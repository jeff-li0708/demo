package com.yeepay.sqkkseperator.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yeepay.sqkkseperator.bean.*;
import com.yeepay.sqkkseperator.config.Config;
import org.springframework.stereotype.Service;
import util.BeanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liangl on 2019/4/8.
 */
@Service
public class YibaoPayService {

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

}
