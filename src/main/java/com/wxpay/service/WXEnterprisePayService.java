//package com.wxpay.service;
//
//import com.alibaba.fastjson.JSON;
//import com.github.wxpay.sdk.WXPayUtil;
//import com.jumei.market.commons.utils.*;
//import com.jumei.market.commons.utils.wx.WXInterface;
//import com.jumei.market.dao.entity.XcSettlementPay;
//import com.jumei.market.dao.entity.tablefield.SettlementEnum;
//import com.jumei.market.dao.mapper.XcOrderInfoMapper;
//import com.jumei.market.dao.mapper.XcPayOrderMapper;
//import com.jumei.market.service.EncryptionDecryptionService;
//import com.jumei.market.sms.SmsService;
//import com.jumei.market.web.exception.CommException;
//import com.jumei.owl.logger.Owl;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import redis.clients.jedis.JedisCluster;
//
//import java.math.BigDecimal;
//import java.security.PublicKey;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 微信企业支付支付相关
// * Created by liangl on 2018/7/10
// */
//@Service
//public class WXEnterprisePayService {
//
//    Logger logger = LoggerFactory.getLogger(WXEnterprisePayService.class);
//
//    @Autowired
//    XcPayOrderMapper payOrderMapper;
//    @Autowired
//    XcOrderInfoMapper orderInfoMapper;
//
//    @Autowired
//    WeiXinConfig weiXinConfig;
//    @Autowired
//    SmsService smsService;
//    @Autowired
//    JedisCluster jedisCluster;
//    @Autowired
//    EncryptionDecryptionService decryptionService;
//
//
//    /**
//     * 付款到用户微信零钱钱包
//     */
//    public XcSettlementPay payToUserWXWellet(XcSettlementPay settlementPay,String ip,String desc) throws CommException{
//        Map<String, String> wxResult;
//        long start = System.currentTimeMillis();
//        try{
//            Map<String, String> param = new HashMap<>();
//            if (settlementPay.getSourceType() == SettlementEnum.SettlementPaySourceType.YHTX.getCode()) {
//                param.put("mch_appid", weiXinConfig.getAppID()); //C端提现用C端的AppId
//            } else {
//                param.put("mch_appid", weiXinConfig.getManagerAppId());
//            }
//            param.put("mchid",weiXinConfig.getMchID());
//            param.put("nonce_str", WeiXinParamUtils.getNonceStr());
//            param.put("partner_trade_no", settlementPay.getPayOrderNo()); //商户订单号
//            param.put("openid",settlementPay.getOpenId()); //用户openid
//            param.put("check_name","FORCE_CHECK");                           //NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
//            param.put("re_user_name",settlementPay.getUserName());
//            param.put("amount", String.valueOf(FormatUtils.moneyTocents(settlementPay.getAmount()))); //单位分
//            param.put("desc",desc);                                                 //企业付款描述信息
//            param.put("spbill_create_ip",ip);                                       //该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。
//            param.put("sign", WXPayUtil.generateSignature(param, weiXinConfig.getKey()));
//            WXInterface.logger.info("调用微信企业付款到零钱接口:{}", JSON.toJSONString(param));
//            wxResult = weiXinConfig.getPay(false).payToUser(param);
//            WXInterface.logger.info("调用微信企业付款到零钱接口结果:{}", JSON.toJSONString(wxResult));
//            Owl.getInstance().stats("market,api,weixin.payToUserWXWellet,cost_time",
//                    String.valueOf(1) + "," + String.valueOf(System.currentTimeMillis() - start));
//        }catch(Exception e){
//            Owl.getInstance().stats("market,api,weixin.payToUserWXWellet.error,cost_time",
//                    String.valueOf(1) + "," + String.valueOf(System.currentTimeMillis() - start));
//            WXInterface.logger.error("调用微信企业付款到零钱接口异常",e);
//            throw new CommException(-1,"请求微信付款异常");
//        }
//
//        if (WXInterface.WX_SUCCESS.equals(wxResult.get(WXInterface.WX_RETURN_CODE))) { //请求成功
//            if (WXInterface.WX_SUCCESS.equals(wxResult.get(WXInterface.WX_RESULT_CODE))) { //业务成功
//                String payment_no = wxResult.get("payment_no");
//                String payment_time = wxResult.get("payment_time"); //企业付款成功时间 2015-05-19 15：26：59
//                settlementPay.setWxOrder(payment_no);
//                settlementPay.setPayTime(DateUtil.unixTime());//与微信返回的时间相差不大,就取当前的系统时间
//                settlementPay.setStatus(SettlementEnum.SettlementPayStatus.SUCCESS.getCode());//成功
//            } else if (WXInterface.WX_FAIL.equals(wxResult.get(WXInterface.WX_RESULT_CODE))
//                    && WXInterface.NEED_QUERY_RESULT.contains(wxResult.get("err_code"))) { //需要查询状态
//
//                WXInterface.logger.info("调用微信企业付款到零钱接口异常payOrderNo:{},err_code_des:{}",settlementPay.getPayOrderNo(),wxResult.get("err_code_des"));
//
//            } else { //失败
//                WXInterface.logger.info("调用微信企业付款到零钱接口失败payOrderNo:{},err_code_des:{}",settlementPay.getPayOrderNo(),wxResult.get("err_code_des"));
//                settlementPay.setStatus(SettlementEnum.SettlementPayStatus.FAIL.getCode());
//                settlementPay.setReason(wxResult.get("err_code_des"));
//
//                //打款失败--我们的商户账户余额不足,发短信提醒
//                if (WXInterface.ERR_CODE.NOTENOUGH.getCode().equals(wxResult.get("err_code"))){
//                    String sendMsg = "微信商户余额不足,联系财务充值";
//                    this.sendWarnMsg(sendMsg);
//                } else if (WXInterface.ERR_CODE.NAME_MISMATCH.getCode().equals(wxResult.get("err_code"))) { //真实姓名不一致
//                    settlementPay.setWxErrCode(WXInterface.ERR_CODE.NAME_MISMATCH.getCode());
//                }
//            }
//        } else { //请求失败
//            WXInterface.logger.error("调用微信企业付款到零钱接请求失败payOrderNo:{},return_msg:{}",settlementPay.getPayOrderNo(),wxResult.get(WXInterface.WX_RETURN_MSG));
//            settlementPay.setStatus(SettlementEnum.SettlementPayStatus.FAIL.getCode());
//            settlementPay.setReason(wxResult.get(WXInterface.WX_RETURN_MSG));
//        }
//        return settlementPay;
//    }
//
//    /**
//     * 查询结算状态
//     * @param settlementPay
//     * @throws Exception
//     */
//    public XcSettlementPay querySettlementOrder(XcSettlementPay settlementPay){
//        Map<String, String> result;
//        try {
//
//            Map<String, String> param = new HashMap<>();
//            if (settlementPay.getSourceType() == SettlementEnum.SettlementPaySourceType.YHTX.getCode()) {
//                param.put("appid", weiXinConfig.getAppID()); //C端提现用C端的AppId
//            } else {
//                param.put("appid", weiXinConfig.getManagerAppId());
//            }
//            param.put("mch_id",weiXinConfig.getMchID());
//            param.put("partner_trade_no",settlementPay.getPayOrderNo());
//            param.put("nonce_str", WeiXinParamUtils.getNonceStr());
//            param.put("sign", WXPayUtil.generateSignature(param, weiXinConfig.getKey()));
//            WXInterface.logger.info("调用微信企业付款查询接口:{}", JSON.toJSONString(param));
//            result = weiXinConfig.getPay(false).payToUserQuery(param);
//            WXInterface.logger.info("调用微信企业付款查询接口结果:{}", JSON.toJSONString(result));
//            if (WXInterface.WX_SUCCESS.equals(result.get(WXInterface.WX_RETURN_CODE))) { //请求成功
//                if (WXInterface.WX_SUCCESS.equals(result.get(WXInterface.WX_RESULT_CODE))) { //SUCCESS/FAIL ，非付款标识，付款是否成功需要查看status字段来判断
//                    if (WXInterface.WX_SUCCESS.equals(result.get(WXInterface.WX_STATUS))){//转账成功
//
//                        settlementPay.setWxOrder(result.get("detail_id"));
//                        settlementPay.setPayTime(DateUtil.unixTime());//与微信返回的时间相差不大,就取当前的系统时间
//                        settlementPay.setStatus(SettlementEnum.SettlementPayStatus.SUCCESS.getCode());//成功
//
//                    } else if ("FAILED".equals(result.get(WXInterface.WX_STATUS))){ //转账失败
//                        WXInterface.logger.info("微信企业付款查询转账失败,失败原因:{}", result.get("reason"));
//                        settlementPay.setStatus(SettlementEnum.SettlementPayStatus.FAIL.getCode());
//                        settlementPay.setReason(result.get("reason"));
//                    }
//                } else if (WXInterface.WX_FAIL.equals(result.get(WXInterface.WX_RESULT_CODE))
//                        && WXInterface.ERR_CODE.NOT_FOUND.getCode().equals(result.get("err_code"))
//                        && DateUtil.unixTime() - settlementPay.getCreateTime() > 120) { //结算支付单创建两分钟后微信返回订单不存在,则当失败处理,请求的超时时间设置的是1一分钟,这里稍微延迟一分钟
//                    WXInterface.logger.info("微信企业付款查询转账失败,失败原因:{}", result.get("err_code_des"));
//                    settlementPay.setStatus(SettlementEnum.SettlementPayStatus.FAIL.getCode());
//                    settlementPay.setReason(result.get("err_code_des"));
//                } else {
//                   WXInterface.logger.info("调用微信企业付款查询接口err_code={},err_code_des={}", result.get(WXInterface.WX_ERR_CODE), result.get(WXInterface.WX_ERR_CODE_DES));
//                }
//
//            }
//        }catch (Exception e) {
//            WXInterface.logger.error("调用微信企业付款查询接口错误payOderNo:{}", settlementPay.getPayOrderNo(),e);
//        }
//        return settlementPay;
//    }
//
//    /**
//     * 付款到用户银行卡
//     * @param settlementPay
//     * @param desc
//     * @return
//     * @throws CommException
//     */
//    public XcSettlementPay payToUserBankCard(XcSettlementPay settlementPay, String desc) throws CommException {
//        Map<String, String> wxResult;
//        PublicKey publicKey = WXPayUtil.getPublicKey(weiXinConfig.getRSAPublicKeyStream(), "RSA");
//        long start = System.currentTimeMillis();
//        try {
//            String decryptBankCard = decryptionService.decryptData(settlementPay.getBankCard());//解密银行卡
//
//            Map<String, String> param = new HashMap<>();
//            param.put("mch_id", weiXinConfig.getMchID());
//            param.put("partner_trade_no", settlementPay.getPayOrderNo()); //商户订单号
//            param.put("nonce_str", WeiXinParamUtils.getNonceStr());
//            param.put("enc_bank_no",WXPayUtil.encodeByRSA(publicKey, decryptBankCard)); //银行卡号-RSA加密
//            param.put("enc_true_name",WXPayUtil.encodeByRSA(publicKey, settlementPay.getUserName()));//收款人-RSA加密
//            param.put("bank_code",settlementPay.getBankCode());
//            param.put("amount", String.valueOf(FormatUtils.moneyTocents(settlementPay.getAmount()))); //单位分
//            param.put("desc", desc);                                                 //企业付款描述信息
//            param.put("sign", WXPayUtil.generateSignature(param, weiXinConfig.getKey()));
//            WXInterface.logger.info("调用微信企业付款到银行卡接口:{}", JSON.toJSONString(param));
//            wxResult = weiXinConfig.getPay(false).payToBankCard(param);
//            WXInterface.logger.info("调用微信企业付款到银行卡接口结果:{}", JSON.toJSONString(wxResult));
//            Owl.getInstance().stats("market,api,weixin.payToUserBankCard,cost_time",
//                    String.valueOf(1) + "," + String.valueOf(System.currentTimeMillis() - start));
//        } catch (Exception e) {
//            Owl.getInstance().stats("market,api,weixin.payToUserBankCard.error,cost_time",
//                    String.valueOf(1) + "," + String.valueOf(System.currentTimeMillis() - start));
//            WXInterface.logger.error("调用微信企业付款到银行卡接口异常", e);
//            throw new CommException(-1, "请求微信付款异常");
//        }
//
//        if (WXInterface.WX_SUCCESS.equals(wxResult.get(WXInterface.WX_RETURN_CODE))) { //请求成功
//            if (WXInterface.WX_SUCCESS.equals(wxResult.get(WXInterface.WX_RESULT_CODE))) { //业务成功
//                String payment_no = wxResult.get("payment_no");
//                String feeAmt = wxResult.get("cmms_amt");
//                settlementPay.setWxOrder(payment_no);
//                settlementPay.setPayTime(DateUtil.unixTime());//与微信返回的时间相差不大,就取当前的系统时间
//                settlementPay.setStatus(SettlementEnum.SettlementPayStatus.SUCCESS.getCode());//成功
//                settlementPay.setFeeAmount(new BigDecimal(feeAmt).divide(new BigDecimal(100))); //手续费
//            } else if (WXInterface.WX_FAIL.equals(wxResult.get(WXInterface.WX_RESULT_CODE))
//                    && WXInterface.NEED_QUERY_RESULT.contains(wxResult.get("err_code"))) { //需要查询状态
//
//                WXInterface.logger.info("调用微信企业付款到银行卡接口异常payOrderNo:{},err_code_des:{}",settlementPay.getPayOrderNo(),wxResult.get("err_code_des"));
//
//            } else { //失败
//                WXInterface.logger.info("调用微信企业付款到银行卡接口失败payOrderNo:{},err_code_des:{}",settlementPay.getPayOrderNo(),wxResult.get("err_code_des"));
//                settlementPay.setStatus(SettlementEnum.SettlementPayStatus.FAIL.getCode());
//                settlementPay.setReason(wxResult.get("err_code_des"));
//
//                //打款失败--我们的商户账户余额不足,发短信提醒
//                if (WXInterface.ERR_CODE.NOTENOUGH.getCode().equals(wxResult.get("err_code"))){
//                    String sendMsg = "微信商户余额不足,联系财务充值";
//                    this.sendWarnMsg(sendMsg);
//                } else if (WXInterface.ERR_CODE.NAME_MISMATCH.getCode().equals(wxResult.get("err_code"))) { //真实姓名不一致
//                    settlementPay.setWxErrCode(WXInterface.ERR_CODE.NAME_MISMATCH.getCode());
//                }
//            }
//        } else { //请求失败
//            WXInterface.logger.error("调用微信企业付款到银行卡请求失败payOrderNo:{},return_msg:{}",settlementPay.getPayOrderNo(),wxResult.get(WXInterface.WX_RETURN_MSG));
//            settlementPay.setStatus(SettlementEnum.SettlementPayStatus.FAIL.getCode());
//            settlementPay.setReason(wxResult.get(WXInterface.WX_RETURN_MSG));
//        }
//        return settlementPay;
//    }
//    /**
//     * 查询企业付款到银行卡状态
//     * @param settlementPay
//     * @throws Exception
//     */
//    public XcSettlementPay querySettlementToBankCard(XcSettlementPay settlementPay){
//        Map<String, String> result;
//        try {
//
//            Map<String, String> param = new HashMap<>();
//            param.put("mch_id",weiXinConfig.getMchID());
//            param.put("partner_trade_no",settlementPay.getPayOrderNo());
//            param.put("nonce_str", WeiXinParamUtils.getNonceStr());
//            param.put("sign", WXPayUtil.generateSignature(param, weiXinConfig.getKey()));
//            WXInterface.logger.info("调用微信企业付款到银行卡查询接口:{}", JSON.toJSONString(param));
//            result = weiXinConfig.getPay(false).payToUserQuery(param);
//            WXInterface.logger.info("调用微信企业付款到银行卡查询接口结果:{}", JSON.toJSONString(result));
//
//            if (WXInterface.WX_SUCCESS.equals(result.get(WXInterface.WX_RETURN_CODE))) { //请求成功
//                if (WXInterface.WX_SUCCESS.equals(result.get(WXInterface.WX_RESULT_CODE))) { //SUCCESS/FAIL ，非付款标识，付款是否成功需要查看status字段来判断
//                    if (WXInterface.WX_SUCCESS.equals(result.get(WXInterface.WX_STATUS))){//转账成功
//                        String feeAmt = result.get("cmms_amt");
//
//                        settlementPay.setWxOrder(result.get("payment_no"));
//                        settlementPay.setPayTime(DateUtil.unixTime());//与微信返回的时间相差不大,就取当前的系统时间
//                        settlementPay.setStatus(SettlementEnum.SettlementPayStatus.SUCCESS.getCode());//成功
//                        settlementPay.setFeeAmount(new BigDecimal(feeAmt).divide(new BigDecimal(100))); //手续费
//
//                    } else if ("FAILED".equals(result.get(WXInterface.WX_STATUS))){ //转账失败
//                        WXInterface.logger.info("微信企业付款到银行卡查询转账失败,失败原因:{}", result.get("reason"));
//                        settlementPay.setStatus(SettlementEnum.SettlementPayStatus.FAIL.getCode());
//                        settlementPay.setReason(result.get("reason"));
//
//                    } else if ("BANK_FAIL".equals(result.get(WXInterface.WX_STATUS))) { //银行退票，订单状态由付款成功流转至退票
//                        WXInterface.logger.info("微信企业付款到银行卡查询,银行退票,失败原因:{}", result.get("reason"));
//                        settlementPay.setStatus(SettlementEnum.SettlementPayStatus.BANK_FAIL.getCode());
//                        settlementPay.setReason(result.get("reason"));
//                    }
//                } else if (WXInterface.WX_FAIL.equals(result.get(WXInterface.WX_RESULT_CODE))
//                        && WXInterface.ERR_CODE.ORDERNOTEXIST.getCode().equals(result.get("err_code"))
//                        && DateUtil.unixTime() - settlementPay.getCreateTime() > 600) { //结算支付单创建10分钟后微信返回订单不存在,则当失败处理,
//                    WXInterface.logger.info("微信企业付款查询转账失败,失败原因:{}", result.get("err_code_des"));
//                    settlementPay.setStatus(SettlementEnum.SettlementPayStatus.FAIL.getCode());
//                    settlementPay.setReason(result.get("err_code_des"));
//                } else {
//                    WXInterface.logger.info("调用微信企业付款查询接口err_code={},err_code_des={}", result.get(WXInterface.WX_ERR_CODE), result.get(WXInterface.WX_ERR_CODE_DES));
//                }
//
//            }
//        }catch (Exception e) {
//            WXInterface.logger.error("调用微信企业付款查询接口错误payOderNo:{}", settlementPay.getPayOrderNo(),e);
//        }
//        return settlementPay;
//    }
//    /**
//     * 发预警短信
//     * @param msg
//     */
//    @Async
//    public void sendWarnMsg(String msg){
//        try {
//            String key = MD5Util.encrypt(msg);
//            List<String> phones = (List<String>) DoveConfigUtils.getSystemCommon().get("balance_not_enough_notify_phone");
//            for (String phone : phones){
//                key = key +"_"+ phone;
//                if (StringUtils.isEmpty(jedisCluster.get(key))) {
//                    smsService.sendByMobile(phone, msg, false);
//                    jedisCluster.setex(key,12 * 3600,"1");
//                }
//            }
//        }catch (Exception e){
//            logger.error("发送预警短信错误mgs={}", msg, e);
//        }
//    }
//
//}
