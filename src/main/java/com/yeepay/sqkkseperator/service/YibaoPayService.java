package com.yeepay.sqkkseperator.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yeepay.common.securityplatform.YBPayUtil;
import com.yeepay.common.utils.CallbackUtils;
import com.yeepay.sqkkseperator.bean.AuthBindCardReq;
import com.yeepay.sqkkseperator.bean.BindCardInfo;
import com.yeepay.sqkkseperator.bean.BindCardPayReq;
import com.yeepay.sqkkseperator.bean.PayToUserReq;
import com.yeepay.sqkkseperator.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.BeanUtil;
import util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liangl on 2019/4/8.
 */
@Service
public class YibaoPayService {

    private Logger logger = LoggerFactory.getLogger(YibaoPayService.class);
    private Config config = Config.getInstance();

    /**
     * 绑卡请求
     * @param req
     * @return
     */
    public Map<String, String> authBindCardReq(AuthBindCardReq req) {
        Map<String, String> param = BeanUtil.beanToMap(req);
        param.put("merchantno", config.getValue("merchantno"));
        return YeepayService.yeepayYOP(param, config.getValue("authbindcardreqUri"));
    }

    /**
     * 绑卡短信重发
     * @param requestno 请求单号
     * @param advicesmstype 短信发送类型 MESSAGE： 短验码将以短信的方式发送给用户，VOICE： 短验码将以语音的方式发送给用户默认值为上次请求的建议发送类型
     * @return
     */
    public Map<String, String> authBindCardResend(String requestno, String advicesmstype) {
        Map<String, String> param = new HashMap<>();
        param.put("requestno", requestno);
        param.put("advicesmstype", advicesmstype);
        return YeepayService.yeepayYOP(param, config.getValue("authbindcardresendUri"));
    }

    /**
     * 绑卡确认
     * @param requestno 请求单号
     * @param validatecode 验证码
     * @return
     */
    public Map<String, String> authbindcardconfirm(String requestno, String validatecode) {
        Map<String, String> param = new HashMap<>();
        param.put("requestno", requestno);
        param.put("validatecode", validatecode);
        return YeepayService.yeepayYOP(param, config.getValue("authbindcardconfirmUri"));
    }

    /**
     * 查询用户绑卡列表
     * @param identityid 商户生成的用户唯一标识
     * @param identitytype MAC： 网卡地址
     * IMEI： 国际移动设备标识
     * ID_CARD： 用户身份证号
     * PHONE： 手机号
     * EMAIL： 邮箱
     * USER_ID： 用户 id
     * AGREEMENT_NO： 用户纸质订单协议号
     * @return
     */
    public List<BindCardInfo> authListqueryServlet(String identityid,String identitytype) {
        Map<String,String> map = new HashMap<>();
        map.put("merchantno", config.getValue("merchantno"));
        map.put("identityid", identityid);
        map.put("identitytype", identitytype);
        Map<String,String> yopresponsemap = YeepayService.yeepayYOP(map,config.getValue("authListqueryUri"));
        List<BindCardInfo> cardList = new ArrayList<>();
        if (yopresponsemap.get("cardlist") != null) {
            JSONArray jsonArray = JSON.parseArray(yopresponsemap.get("cardlist"));
            for (int i = 0,j = jsonArray.size(); i < j; i++) {
                BindCardInfo cardInfo = jsonArray.getObject(i, BindCardInfo.class);
                cardList.add(cardInfo);
            }
        }
        return cardList;
    }

    /**
     * 绑卡支付请求
     * @return
     */
    public Map<String,String> bindCardPayReq(BindCardPayReq req) {
        Map<String, String> param = BeanUtil.beanToMap(req);
        param.put("merchantno", config.getValue("merchantno"));
        return YeepayService.yeepayYOP(param, config.getValue("unibindcardpayUri"));
    }

    /**
     * 绑卡支付短信重发
     * @param requestno 商户生成的唯一充值请求号，请与绑卡支付请求接口中的请求号保持一致
     * @param advicesmstype MESSAGE： 短验码将以短信的方式发送给用户，VOICE： 短验码将以语音的方式发送给用户
     * @return
     */
    public Map<String,String> bindCardPayResend(String requestno,String advicesmstype) {
        Map<String, String> param = new HashMap<>();
        param.put("requestno", requestno);
        param.put("advicesmstype", advicesmstype);
        return YeepayService.yeepayYOP(param, config.getValue("bindcardpayresendUri"));
    }

    /**
     * 绑卡支付确认
     * @param requestno
     * @param validatecode
     * @return
     */
    public Map<String, String> bindCardConfirm(String requestno, String validatecode) {
        Map<String, String> param = new HashMap<>();
        param.put("requestno", requestno);
        param.put("validatecode", validatecode);
        return YeepayService.yeepayYOP(param, config.getValue("bindcardconfirmUri"));
    }

    /**
     * 支付查询
     * @param requestno
     * @return
     */
    public Map<String,String> payQuery(String requestno) {
        Map<String, String> param = new HashMap<>();
        param.put("requestno", requestno);
        return YeepayService.yeepayYOP(param, config.getValue("bindcardpayqueryUri"));
    }

    /**
     * 支付对账
     * @param startdate 开始日期 yyyy-MM-dd
     * @param enddate 结束日期 yyyy-MM-dd
     * @return
     */
    public Map<String,String> payAccountCheck(String startdate,String enddate) {

        Map<String, String> param = new HashMap<>();
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        return YeepayService.yeepayYOP(param, config.getValue("payfileUri"));
    }

    /**
     * 查询商户余额
     * @return
     */
    public Map<String,String> accountBalanaceQuery() {
        Map<String,String> param = new HashMap<>();
        param.put("cmd","AccountBalanaceQuery");
        param.put("version","1.0");
        param.put("mer_Id",config.getValue("p1_MerId"));
        param.put("date", DateUtil.getDate2());
        String[] needSiginFeild = {"cmd","mer_Id","date"};
        try {
            String response = CallbackUtils.httpRequest(config.getValue("yeepayCommonReqURL"),
                    YBPayUtil.generateSignedXml(param,needSiginFeild),
                    "POST", "gbk","text/xml ;charset=gbk", false);
//            String response = HttpHelper.requestPost(config.getValue("yeepayCommonReqURL"),
//                    YBPayUtil.generateSignedXml(param,needSiginFeild), HttpHelper.MEDIA_TYPE_TEXT);
            logger.info("易宝余额查询接口返回："+response);
            return YBPayUtil.xmlToMap(response);
        } catch (IOException e) {
            logger.info("易宝接口请求异常",e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * 给用户打款
     * @return
     */
    public Map<String, String> payToUser(PayToUserReq req) {
        Map<String,String> param = new HashMap<>();
        param.put("cmd","TransferSingle");
        param.put("version","1.1");
        param.put("group_Id",config.getValue("p1_MerId"));
        param.put("mer_Id",config.getValue("p1_MerId"));
        param.put("batchNo", req.getBatch_No());
        param.put("bank_Code", req.getBank_Code()); //bank_Code和bank_Name二选一
        //param.put("bank_Name", req.getBank_Name());
        param.put("order_Id", req.getOrder_Id());
        param.put("amount", req.getAmount());
        param.put("account_Name", req.getAccount_Name());
        param.put("account_Number", req.getAccount_Number());
        param.put("fee_Type", "SOURCE");
        param.put("urgency", "1"); //1-实时出款 0-非实时出款
        String[] needSiginFeild = {"cmd","mer_Id","batchNo","order_Id","amount","account_Number"};
        try {
            String response = CallbackUtils.httpRequest(config.getValue("onlinePaymentReqURL"),
                    YBPayUtil.generateSignedXml(param,needSiginFeild),
                    "POST", "gbk","text/xml ;charset=gbk", false);
//            String response = HttpHelper.requestPost(config.getValue("onlinePaymentReqURL"),
//                    YBPayUtil.generateSignedXml(param,needSiginFeild), HttpHelper.MEDIA_TYPE_TEXT);
            logger.info("易宝单笔打款接口返回："+response);
            return YBPayUtil.xmlToMap(response);
        } catch (IOException e) {
            logger.info("易宝接口请求异常",e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * 查询打款结果
     * @param batchNo
     * @param orderId
     * @return
     */
    public Map<String, String> queryTransfer(String batchNo, String orderId) {
        Map<String,String> param = new HashMap<>();
        param.put("cmd","BatchDetailQuery");
        param.put("version","1.0");
        param.put("group_Id",config.getValue("p1_MerId"));
        param.put("mer_Id",config.getValue("p1_MerId"));
        param.put("query_Mode","3");
        param.put("batchNo",batchNo);
        param.put("order_Id",orderId);
        param.put("page_No","1");
        String[] needSiginFeild = {"cmd","mer_Id","batchNo","order_Id","page_No"};
        try {
            String response = CallbackUtils.httpRequest(config.getValue("onlinePaymentReqURL"),
                    YBPayUtil.generateSignedXml(param,needSiginFeild),
                    "POST", "gbk","text/xml ;charset=gbk", false);
//            String response = HttpHelper.requestPost(config.getValue("onlinePaymentReqURL"),
//                    YBPayUtil.generateSignedXml(param,needSiginFeild), HttpHelper.MEDIA_TYPE_TEXT);
            logger.info("易宝打款查询接口返回："+response);
            return YBPayUtil.xmlToMap(response);
        } catch (IOException e) {
            logger.info("易宝接口请求异常",e);
        } catch (Exception e) {

        }
        return new HashMap<>();
    }

}
