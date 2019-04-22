//package com.wxpay.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.jumei.market.commons.RedisKeyConstants;
//import com.jumei.market.commons.utils.*;
//import com.jumei.market.commons.utils.wx.WXInterface;
//import com.jumei.market.dao.entity.*;
//import com.jumei.market.dao.entity.tablefield.CouponEnum;
//import com.jumei.market.dao.entity.tablefield.OrderEnum;
//import com.jumei.market.dao.entity.tablefield.PayOrderEnum;
//import com.jumei.market.dao.mapper.*;
//import com.jumei.market.miniseller.service.SellerSettlementService;
//import com.jumei.market.service.EncryptionDecryptionService;
//import com.jumei.market.service.order.OrderFinishService;
//import com.jumei.market.service.seller.XcSellerInfoService;
//import com.jumei.market.service.settlement.DistributionService;
//import com.jumei.market.service.settlement.SubsidyService;
//import com.jumei.market.sms.SmsService;
//import com.jumei.market.web.controller.dto.WXOrderPay;
//import com.jumei.market.web.domain.activity.ActivityCoupon;
//import com.jumei.market.web.exception.CommException;
//import com.jumei.market.web.exception.OrderException;
//import com.jumei.owl.logger.Owl;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import redis.clients.jedis.JedisCluster;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//
///**
// * 微信支付相关
// * Created by liangl on 2018/7/10
// */
//@Service
//public class WXPayService {
//
//    Logger logger = LoggerFactory.getLogger(WXPayService.class);
//
//    @Autowired
//    XcPayOrderMapper payOrderMapper;
//
//    @Autowired
//    XcOrderInfoMapper orderInfoMapper;
//
//    @Autowired
//    ClassOwnMapper classOwnMapper;
//    @Autowired
//    XcOrderCouponMapper orderCouponMapper;
//    @Autowired
//    XcUserCouponItemMapper userCouponItemMapper;
//
//    @Autowired
//    WeiXinConfig weiXinConfig;
//
//    @Autowired
//    JedisCluster jedisCluster;
//
//    @Autowired
//    SmsService smsService;
//
//    @Autowired
//    XcSellerInfoService xcSellerInfoService;
//
//    @Autowired
//    DistributionService distributionService;
//
//    @Autowired
//    EncryptionDecryptionService decryptionService;
//
//    @Autowired
//    SubsidyService subsidyService;
//
//    @Autowired
//    OrderFinishService orderFinishService;
//    @Autowired
//    SellerSettlementService sellerSettlementService;
//
//    /***支付回调地址 application-dev.yaml*/
//    @Value("${wxPayCallBackUrl}")
//    String payCallBackUrl;
//
//    /**
//     * 到微信发起下单
//     *
//     * @param userinfo 用户相关信息
//     * @param ip       用户当前ip
//     * @param sellerId  商户id
//     * @param orderNo   订单号
//     * @param payAmount 支付金额
//     * @param sellerName 店家名称  xxx店
//     * @throws OrderException
//     */
//    public String addPayOrder(XcUserInfo userinfo, String ip, String sellerId, String orderNo,BigDecimal payAmount,String sellerName) throws OrderException {
//
//        WXInterface.logger.info("创建支付单uid={},sellerId={},orderNo={},payAmount={}",userinfo.getId(), sellerId, orderNo, payAmount);
//
//        StringBuilder key = new StringBuilder("pay_order_").append(userinfo.getId()).append("_").append(orderNo);
//        //微信支付设置的是1分钟的超时 这里锁稍微配置的时间长点
//        RedisDistributedLock lock = new RedisDistributedLock(key.toString(), jedisCluster, 70, TimeUnit.SECONDS);
//        if (!lock.tryLock()) {
//            throw new OrderException(-1, "请稍后再试");
//        }
//        try {
//            if (payAmount.compareTo(BigDecimal.ZERO) == 1) { //支付金额大于0
//                Map<String, Object> param = new HashMap<>();
//                param.put("userId", userinfo.getId());
//                param.put("orderNo", orderNo);
//                List<XcPayOrder> orders = payOrderMapper.selectByMap(param);
//                Optional<XcPayOrder> ordering = orders.stream().filter(order -> order.getStatus() == PayOrderEnum.Status.Ordering.getCode())
//                        .max((o1, o2) -> o1.getCreateTime() > o2.getCreateTime() ? 1 : -1);
//                if (ordering.isPresent()) {
//                    //微信订单支付设置的是10分钟有效，设置9分钟 以免领结点点过去支付不能成功
//                    if (TimeUtils.getUnixTime() - ordering.get().getCreateTime() >= 9 * 60) {
//                        ordering.get().setStatus(PayOrderEnum.Status.FAIL.getCode());
//                        payOrderMapper.updateByPrimaryKeySelective(ordering.get());
//                    } else {
//                        return ordering.get().getWxPrepayId();
//                    }
//                }
//
//                //更新订单状态为成功
//                XcOrderInfo orderInfo = orderInfoMapper.getOrderInfoByUidAndOrderNo(userinfo.getId(), orderNo);
//                //支付单号
//                String payOrderNo = RedisClusterSerialNumber.nextByDay("PPAY", 8);
//                payOrderNo = payOrderNo + RandomUtils.getFixedLengthNumber(4); //支付单号后面增加4位随机数字
//
//                String repayId = paySign(userinfo.getOpenId(), payOrderNo,
//                        String.valueOf(FormatUtils.moneyTocents(payAmount)), ip, sellerName,DoveConfigUtils.getGoodsTagByDeliveryType(orderInfo.getDeliveryType()));
//
//                XcPayOrder order = new XcPayOrder();
//                order.setUserId(userinfo.getId());
//                order.setOpenId(userinfo.getOpenId());
//                order.setSellerId(sellerId);
//                order.setOrderNo(orderNo);
//                order.setPayOrderNo(payOrderNo);
//                order.setPayAmount(payAmount);
//                order.setStatus(PayOrderEnum.Status.Ordering.getCode());
//                order.setCreateTime(DateUtil.unixTime());
//                order.setWxPrepayId(repayId);
//
//                payOrderMapper.insertSelective(order);
//                WXInterface.logger.info("创建支付单成功payOrder={}", JSON.toJSONString(order));
//                return repayId;
//
//            } else { //支付金额为0,则创建一个虚拟支付单,并将订单状态改为支付成功
//                //支付单号
//                String payOrderNo = RedisClusterSerialNumber.nextByDay("PPAY", 8);
//                XcPayOrder order = new XcPayOrder();
//                order.setUserId(userinfo.getId());
//                order.setOpenId(userinfo.getOpenId());
//                order.setSellerId(sellerId);
//                order.setOrderNo(orderNo);
//                order.setPayOrderNo(payOrderNo);
//                order.setPayAmount(payAmount);
//                order.setStatus(PayOrderEnum.Status.SUCCESS.getCode());//成功
//                order.setCreateTime(DateUtil.unixTime());
//                order.setPayTime(DateUtil.unixTime());
//                order.setWxPrepayId("");
//                payOrderMapper.insertSelective(order);
//                WXInterface.logger.info("创建支付单成功payOrder={}", JSON.toJSONString(order));
//
//                //更新订单状态为成功
//                XcOrderInfo orderInfo = orderInfoMapper.getOrderInfoByUidAndOrderNo(userinfo.getId(), orderNo);
//                if (getSuccessOrderCount(orderInfo.getUserId()) == 0) {
//                    orderInfo.setIsFirst(1); //首单
//                }
//                orderInfo.setOrderStatus(OrderEnum.Status.PAY_SUCCESS.getCode());
//                orderInfo.setPayTime(DateUtil.unixTime());
//                orderInfo.setUpdateTime(DateUtil.unixTime());
//
//                logger.info("get subsidy amount");
//                BigDecimal subsidyAmount = subsidyService.getOrderSubsidy(orderInfo); //计算首单补贴  需要配送，完成时间等信息最后来
//                logger.info("order {} subsidy amount is {}",orderInfo.getOrderNo(),subsidyAmount);
//                orderInfo.setSubsidyAmount(subsidyAmount);//平台补贴商户金额
//
//                orderInfoMapper.updateByPrimaryKeySelective(orderInfo);//更新订单状态
//
//                if(orderInfo.getIsFacePay() == 1){//当面付
//                    orderFinishService.confirmOrderFinish(orderInfo.getOrderNo(),orderInfo.getUserId());
//                }
//                distributionService.noticeMessage(orderInfo.getSellerId(),orderInfo.getOrderNo());//订单下单成功通知
//                return "";
//            }
//        } catch (OrderException e) {
//            throw e;
//        } catch (CommException e) {
//            logger.info("confirmOrderFinish orderNo:{},error:{}",orderNo,e.getMessage());
//            OrderException exception = new OrderException(-1, "下单失败");
//            throw exception;
//        } catch (Exception e) {
//            OrderException exception = new OrderException(-1, "下单失败");
//            exception.addSuppressed(e);
//            throw e;
//        } finally {
//            lock.unlock();
//        }
//
//    }
//
//    /**
//     *  微信支付回调处理
//     * @param isSuccess
//     * @param pay
//     * @throws OrderException
//     */
//    public void orderPayResult(boolean isSuccess, WXOrderPay pay) throws OrderException {
//        WXInterface.logger.info("#wei xin#回调信息{}", JSON.toJSONString(pay));
//        String payOrderNo = pay.getOutTradeNo();
//        if (StringUtils.isBlank(payOrderNo)) {
//            WXInterface.logger.info("微信结果没有订单号码");
//            throw new OrderException(-1, "微信返回结果无订单号码");
//        }
//        XcPayOrder payOrder = payOrderMapper.selectByPayOrderNo(payOrderNo);
//        if (payOrder == null) {
//            WXInterface.logger.info("微信支付回调,支付单信息不存在payOrderNo={}",payOrderNo);
//            return;
//        }
//        XcOrderInfo orderInfo = orderInfoMapper.getOrderInfoByUidAndOrderNo(payOrder.getUserId(), payOrder.getOrderNo());
//        if (payOrder.getPayAmount().multiply(new BigDecimal(100)).compareTo(new BigDecimal(pay.getTotalFee())) != 0) {
//            WXInterface.logger.info("回传金额{}与订单金额{}不一致",pay.getCashFee(),payOrder.getPayAmount());
//            throw new OrderException(-1, "金额不一致");
//        }
//        payOrder.setWxOrder(pay.getTransactionId());
//        payOrder.setBankType(pay.getBankType()); //银行编码
//        payOrder.setIsSubscribe((StringUtils.isNotEmpty(pay.getIsSubscribe()) && "Y".equals(pay.getIsSubscribe())) ? 1 : 0); //是否关注公众号
//        payOrder.setWxCouponCount(StringUtils.isNotEmpty(pay.getCouponCount()) ? Integer.parseInt(pay.getCouponCount()) : 0);//使用的优惠券
//        payOrder.setWxCouponFee(StringUtils.isNotEmpty(pay.getCouponFee()) ? new BigDecimal(pay.getCouponFee()).divide(new BigDecimal(100)) : BigDecimal.ZERO);//优惠金额
//        payOrder.setWxCouponId(pay.getCouponId0()); //优惠券ID
//        try {
//            payOrder.setPayTime(Long.valueOf(WXInterface.toUnixTime(pay.getTimeEnd())));
//        } catch (ParseException e) {
//            throw new OrderException(-1, "微信返回支付时间错误");
//        }
//
//        if (PayOrderEnum.Status.Ordering.getCode() == payOrder.getStatus()) {
//            if (isSuccess) {
//
//                this.paySuccessDeal(payOrder, orderInfo);
//
//            } else {
//                payOrder.setStatus(PayOrderEnum.Status.FAIL.getCode());
//                payOrderMapper.updateByPrimaryKeySelective(payOrder);//更新支付单状态
//            }
//        } else if (PayOrderEnum.Status.SUCCESS.getCode() == payOrder.getStatus()&& StringUtils.isBlank(payOrder.getWxOrder())) {
//            WXInterface.logger.info("订单已经成功，直接更新订单号码");
//            payOrderMapper.updateByPrimaryKeySelective(payOrder);
//        }
//
//    }
//
////    public String paySign(String openId, String orderNo, String total_fee, String ip, String sellerName) throws OrderException {
////        return paySign(openId,orderNo,total_fee,ip,sellerName,null);
////    }
//
//
//    /**
//     * 请求生成微信预支付单号
//     *
//     * @return
//     */
//    public String paySign(String openId, String orderNo, String total_fee, String ip, String sellerName,String goodsTag) throws OrderException {
//        Map<String, String> result = null;
//        long start = System.currentTimeMillis();
//        try {
//            double creditCardPayMaxLimit = MapUtils.getDouble(DoveConfigUtils.getSystemCommon(), "credit_card_pay_max_limit", 500.0);
//            Map<String, String> paramsMap = new HashMap<>();
//            paramsMap.put("body", sellerName + "-超市");//商品描述
//            //paramsMap.put("detail","");//非必须
//            //paramsMap.put("attach","");
//            paramsMap.put("out_trade_no", orderNo);
//            paramsMap.put("fee_type", "CNY");
//            paramsMap.put("total_fee", total_fee);
//            paramsMap.put("spbill_create_ip", ip);
//            paramsMap.put("time_start", DateUtil.getDateTime("yyyyMMddHHmmss")); //交易起始时间
//            paramsMap.put("time_expire", DateUtil.getDateTime(DateUtil.addMinute(DateUtil.getNow(), 10),"yyyyMMddHHmmss"));//交易结束时间-开始时间加10分钟
//            paramsMap.put("notify_url", payCallBackUrl);//回调地址
//            paramsMap.put("trade_type", "JSAPI");//小程序写死
//            if(StringUtils.isNotBlank(goodsTag)){
//                paramsMap.put("goods_tag", goodsTag);
//            }
////            Map<String,Object> detailMap = new HashMap<>();
////            detailMap.put("goods_id","goods_id");
////            detailMap.put("goods_name","goods_name");
////            detailMap.put("goods_category",StringUtils.isNotBlank(goodsTag)?"上门订单":"自提订单");
////            detailMap.put("price",new BigDecimal(total_fee));
////            detailMap.put("body","body");
////            detailMap.put("quantity",1);
////
////            List detailList = new ArrayList();
////            detailList.add(detailMap);
////            //detail
////            paramsMap.put("detail", "{ \"goods_detail\":"+ JSON.toJSONString(detailList)+"}");
//
//            if(new Double(total_fee) > creditCardPayMaxLimit*100) { //金额大于配置金额,不让使用信用卡
//                paramsMap.put("limit_pay", "no_credit");
//            }
//            paramsMap.put("openid", openId);
//            WXInterface.logger.info("调用微信下单接口{}",paramsMap);
//            result = weiXinConfig.getPay(false).unifiedOrder(paramsMap);
//            WXInterface.logger.info("微信下单返回{}",result);
//            Owl.getInstance().stats("market,api,weixin.paySign,cost_time",
//                    String.valueOf(1) + "," + String.valueOf(System.currentTimeMillis() - start));
//        } catch (Exception e) {
//            Owl.getInstance().stats("market,api,weixin.paySign.error,cost_time",
//                    String.valueOf(1) + "," + String.valueOf(System.currentTimeMillis() - start));
//            WXInterface.logger.error("微信下单调用失败payOrderNo={}", orderNo, e);
//            throw new OrderException(-1, "微信支付调用失败");
//        }
//        if (WXInterface.WX_SUCCESS.equals(result.get(WXInterface.WX_RETURN_CODE))
//                && WXInterface.WX_SUCCESS.equals(result.get(WXInterface.WX_RESULT_CODE))) {
//            String prepayId = result.get("prepay_id");
//            if (StringUtils.isBlank(prepayId)) {
//                WXInterface.logger.info("微信没有返回支付id");
//                throw new OrderException(-1, "微信支付失败");
//            }
//            return prepayId;
//        } else {
//            throw new OrderException(-1, "微信支付失败");
//        }
//
//    }
//
//
//
//    /**
//     *  专门用于前端支付完成窗口回调 及时拉去支付结果。
//     * @throws Exception
//     */
//    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
//    public void loadOrderResult(Long userId,String orderNo) throws Exception {
//        Map<String, Object> param = new HashMap<>();
//        param.put("orderNo", orderNo);
//        param.put("status", PayOrderEnum.Status.Ordering.getCode());
//        param.put("userId", userId);
//        List<XcPayOrder> list = payOrderMapper.selectByMap(param);
//        WXInterface.logger.info("查询到用户{}的订单{}付款中订单数量{}",userId,orderNo,list==null?0:list.size());
//        if (list!=null&&list.size()==1&&queryWXOrder(list.get(0))) {
//            XcPayOrder order = list.get(0);
//            order.setStatus(PayOrderEnum.Status.SUCCESS.getCode());
//            order.setPayTime(DateUtil.unixTime());
//            payOrderMapper.updateByPrimaryKeySelective(order);
//
//            XcOrderInfo orderInfo = orderInfoMapper.getOrderInfoByUidAndOrderNo(userId,orderNo);
//            if (orderInfo.getOrderStatus() == OrderEnum.Status.NEED_PAY.getCode()) {
//                orderInfo.setOrderStatus(OrderEnum.Status.PAY_SUCCESS.getCode());
//                orderInfo.setPayTime(DateUtil.unixTime());
//                orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
//
//                //merchantWarning(orderInfo.getSellerId());
//            }
//            WXInterface.logger.info("更新订单状态为成功");
//        }
//    }
//
//    /**
//     * 查询微信支付结果
//     * @param order
//     * @return
//     * @throws Exception
//     */
//    public boolean queryWXOrder(XcPayOrder order) throws Exception {
//
//        Map<String, String> param = new HashMap<>();
//        param.put("out_trade_no", order.getPayOrderNo());
//        WXInterface.logger.info("拉取支付单状态{}", JSON.toJSONString(param));
//        Map<String, String> result = weiXinConfig.getPay(false).orderQuery(param);
//        WXInterface.logger.info("微信订单拉取结果{}", JSON.toJSONString(result));
//        if (WXInterface.WX_SUCCESS.equals(result.get("trade_state"))) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 拉取更新微信支付结果
//     * @throws Exception
//     */
//    public void scheduledUpdatePayOrderStatus() throws Exception {
//        Map<String, Object> param = new HashMap<>();
//        param.put("status", PayOrderEnum.Status.Ordering.getCode());
//        List<XcPayOrder> list = payOrderMapper.selectByMap(param);
//        logger.info("支付中的订单量{}", list.size());
//        for (XcPayOrder payOrder : list) {
//            XcOrderInfo orderInfo = orderInfoMapper.getOrderInfoByUidAndOrderNo(payOrder.getUserId(),payOrder.getOrderNo());
//            if (orderInfo == null || orderInfo.getOrderStatus() != OrderEnum.Status.NEED_PAY.getCode()
//                    || (DateUtil.unixTime() - payOrder.getCreateTime()) < 10) { //支付创建成功后延迟10秒再拉取结果,创建支付单到用户完成支付大约需要几秒
//                continue;
//            } else {
//                if (queryWXOrder(payOrder)) {
//                    this.paySuccessDeal(payOrder, orderInfo);
//                }
//            }
//        }
//    }
//
//    /**
//     * 支付成功处理
//     * @param payOrder
//     * @param orderInfo
//     */
//    public void paySuccessDeal(XcPayOrder payOrder,XcOrderInfo orderInfo) {
//
//        //1.更新支付单状态
//        payOrder.setStatus(PayOrderEnum.Status.SUCCESS.getCode());
//        payOrder.setPayTime(DateUtil.unixTime());
//        payOrderMapper.updateByPrimaryKeySelective(payOrder);//更新支付单状态
//
//        //2.更新订单状态
//        if (getSuccessOrderCount(orderInfo.getUserId()) == 0) {
//            orderInfo.setIsFirst(1); //首单
//        }
//        orderInfo.setOrderStatus(OrderEnum.Status.PAY_SUCCESS.getCode());
//        orderInfo.setPayTime(DateUtil.unixTime());
//
//        logger.info("get subsidy amount");
//        BigDecimal subsidyAmount = subsidyService.getOrderSubsidy(orderInfo); //计算首单补贴  需要配送，完成时间等信息最后来
//        logger.info("order {} subsidy amount is {}",orderInfo.getOrderNo(),subsidyAmount);
//        orderInfo.setSubsidyAmount(subsidyAmount);//平台补贴商户金额
//
//        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);//更新订单状态
//
//        //3.当面付直接完成
//        if(orderInfo.getIsFacePay() == 1){//当面付
//            try {
//                orderFinishService.confirmOrderFinish(orderInfo.getOrderNo(),orderInfo.getUserId());
//            } catch (CommException e) {
//                logger.info("confirmOrderFinish orderNo:{},error:{}",orderInfo.getOrderNo(),e.getMessage());
//            }
//        }
//
//        //4.启用优惠券
//        if (orderInfo.getDiscountAmount().compareTo(BigDecimal.ZERO) == 1) { //有减免,可能使用了优惠券
//            List<XcOrderCoupon> orderCouponList = orderCouponMapper.selectByOrderNo(orderInfo.getOrderNo());
//
//            Map config = (Map) DoveConfigUtils.getGuoqingActivityConfig().get("guoqing_seed_coupon"); //国庆活动dove配置
//            JSONObject couponJson = config.get("coupon") == null ? new JSONObject() : new JSONObject((Map) config.get("coupon"));//优惠券
//            Long now = DateUtil.unixTime();
//            for (XcOrderCoupon orderCoupon : orderCouponList) {
//
//                List<XcUserCouponItem> enabledCouponList = userCouponItemMapper.selectByUidBeforeCouponId(orderInfo.getUserId(), orderCoupon.getCouponId()); //待启用
//                for (XcUserCouponItem enabledCoupon : enabledCouponList) {
//                    if(enabledCoupon.getLimitTimeType() == CouponEnum.CouponLimitType.VALID_DAYS.getCode()){
//
//                        if(enabledCoupon.getLimitTimeProlongDaysToStart() == 0
//                                && enabledCoupon.getLimitTimeProlongMinuteToStart() == 0){//立即生效
//                            enabledCoupon.setLimitTimeStart(now);
//                        }else if (enabledCoupon.getLimitTimeProlongDaysToStart() > 0) {//配置了开始时间延迟N天
//                            enabledCoupon.setLimitTimeStart(Long.valueOf(TimeUtils.dayStart(now.intValue()) + enabledCoupon.getLimitTimeProlongDaysToStart() * 24 * 3600 ));
//                        } else if (enabledCoupon.getLimitTimeProlongMinuteToStart() > 0) {
//                            enabledCoupon.setLimitTimeStart(now + enabledCoupon.getLimitTimeProlongMinuteToStart() * 60);//延迟N分钟
//                        }
//                        if(enabledCoupon.getLimitTimeProlongDays() > 0) {//结束时间设置了后推N天
//                            enabledCoupon.setLimitTimeEnd(enabledCoupon.getLimitTimeStart() + enabledCoupon.getLimitTimeProlongDays() * 24 * 3600);
//                        }
//                    } else if (enabledCoupon.getLimitTimeType() == CouponEnum.CouponLimitType.END_FIXED.getCode()){ //结束时间固定
//                        if(enabledCoupon.getLimitTimeProlongDaysToStart() == 0
//                                && enabledCoupon.getLimitTimeProlongMinuteToStart() == 0){//立即生效
//                            enabledCoupon.setLimitTimeStart(now);
//                        }else if (enabledCoupon.getLimitTimeProlongDaysToStart() > 0) {//配置了开始时间延迟N天
//                            enabledCoupon.setLimitTimeStart(Long.valueOf(TimeUtils.dayStart(now.intValue()) + enabledCoupon.getLimitTimeProlongDaysToStart() * 24 * 3600 ));
//                        } else if (enabledCoupon.getLimitTimeProlongMinuteToStart() > 0) {
//                            enabledCoupon.setLimitTimeStart(now + enabledCoupon.getLimitTimeProlongMinuteToStart() * 60);//延迟N分钟
//                        }
//                    } else {
//                        enabledCoupon.setLimitTimeStart(now);
//                    }
//                    enabledCoupon.setCouponStatus(CouponEnum.CouponStatus.NOT_USED.getCode());
//                    userCouponItemMapper.updateByPrimaryKeySelective(enabledCoupon);
//                }
//
//                //下面的逻辑暂留,兼容重阳节活动,活动结束后可删除
//                for (String id : couponJson.keySet()) {
//                    ActivityCoupon coupon = JSON.toJavaObject(couponJson.getJSONObject(id), ActivityCoupon.class);
//                    if (orderCoupon.getCouponId().equals(coupon.getCouponId())
//                            && Integer.parseInt(id) != couponJson.keySet().size()) { //使用的券为指定的活动券,且不是最后一张,启用下一张
//                        ActivityCoupon nextCoupon = JSON.toJavaObject(couponJson.getJSONObject((Integer.parseInt(id) + 1) + ""), ActivityCoupon.class); //下一张券
//                        List<XcUserCouponItem> userCouponItemList = userCouponItemMapper.selectByUserIdCouponId(orderInfo.getUserId(), nextCoupon.getCouponId());
//                        if (userCouponItemList != null && userCouponItemList.size() > 0) {
//                            XcUserCouponItem userCouponItem = userCouponItemList.get(0);
//                            int start = TimeUtils.dayEnd(DateUtil.unixTime().intValue()) + 1;
//                            if (start < userCouponItem.getLimitTimeEnd()) {
//                                userCouponItem.setLimitTimeStart(Long.parseLong(start + ""));//设置开始时间为明天
//                                userCouponItemMapper.updateByPrimaryKeySelective(userCouponItem);
//                                logger.info("启用优惠券uid:{},orderNo={},coupon:{}",orderInfo.getUserId(),orderInfo.getOrderNo(), JSON.toJSONString(userCouponItem));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        //更新缓存信息
//        if (orderInfo.getDeliveryType() == OrderEnum.DeliveryType.DELIVERY_GOODS.getCode()) {
//            String key = RedisKeyConstants.XC_USER_HAS_DELIVERY_GOODS + orderInfo.getUserId();//是否有上门订单标志
//            jedisCluster.set(key,"1");
//        }
//
//        //5.通知商家
//        if (orderInfo.getOrderType() == OrderEnum.OrderType.OFFLINE_TRADE.getCode()) { //线下收单成功直接打款给商户
//            sellerSettlementService.systemAutoSettlementPay(orderInfo.getOrderNo());
//        } else if(orderInfo.getIsFacePay() == 1){ //普通订单当面付只发短信2018-08-22
//            BigDecimal inAmount = orderInfo.getTotalSellAmount().add(orderInfo.getSubsidyAmount()).setScale(2, BigDecimal.ROUND_DOWN); //收款金额=订单金额+补贴
//            distributionService.sendMessage(orderInfo.getSellerId(),orderInfo.getOrderNo(),inAmount,orderInfo.getSubsidyAmount().setScale(2, BigDecimal.ROUND_DOWN));
//        } else {
//            distributionService.noticeMessage(orderInfo.getSellerId(),orderInfo.getOrderNo());//订单下单成功通知
//        }
//
//    }
//    /**
//     * 获取用户支付成功和完成的订单数量
//     * @param uid
//     * @return
//     */
//    private Long getSuccessOrderCount(Long uid) {
//        Long success = orderInfoMapper.getUserOrderTotal(uid,OrderEnum.Status.PAY_SUCCESS.getCode());
//        Long finash = orderInfoMapper.getUserOrderTotal(uid,OrderEnum.Status.FINISH.getCode());
//        return success + finash;
//    }
//
//
//}
