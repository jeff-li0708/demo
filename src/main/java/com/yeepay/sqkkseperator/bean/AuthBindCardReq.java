package com.yeepay.sqkkseperator.bean;

import util.DateUtil;

/**
 * Created by liangl on 2019/4/8.
 */

public class AuthBindCardReq {

    private String requestno;      //业务请求号
    private String identityid;     //用户标识 商户生成的用户唯一标识。每个用户唯一，绑卡最终会绑在这个用户标志下
    private String identitytype;   //用户标识类型  MAC：网卡地址,IMEI：国际移动设备标识,ID_CARD：用户身份证号，PHONE：手机号，EMAIL：邮箱，USER_ID：用户 id，AGREEMENT_NO：用户纸质订单协议号
    private String cardno;         //卡号
    private String idcardno;       //身份证号
    private String idcardtype = "ID";     //证件类型 固定值：ID
    private String username;       //持卡人姓名
    private String phone;          //持卡人手机号（银行预留手机号）
    private String issms = "true";          //是否发送短验 枚举：true:有短验false:无短验
    private String advicesmstype = "MESSAGE";  //短信发送类型  MESSAGE:短验码将以短信的方式发送给用户 VOICE:短验码将以语音的方式发送给用户，默认值为MESSAGE
    private String avaliabletime;  //订单有效时间 单位： 分钟 若不传则默认有效期 24h 传的值要大于 1min， 小于 48h 传的值若小于 1min 系统置为 1min，传的值若大于 48h 系统置为 48h
    private String callbackurl;    //回调地址
    private String requesttime = DateUtil.getDateTime();    //请求时间 格式： yyyy-MM-dd HH:mm:ss
    private String authtype = "COMMON_FOUR"; //鉴权类型 固定值：COMMON_FOUR(验四)
    private String remark;
    private String extinfos;


    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getIdentityid() {
        return identityid;
    }

    public void setIdentityid(String identityid) {
        this.identityid = identityid;
    }

    public String getIdentitytype() {
        return identitytype;
    }

    public void setIdentitytype(String identitytype) {
        this.identitytype = identitytype;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getIdcardtype() {
        return idcardtype;
    }

    public void setIdcardtype(String idcardtype) {
        this.idcardtype = idcardtype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIssms() {
        return issms;
    }

    public void setIssms(String issms) {
        this.issms = issms;
    }

    public String getAdvicesmstype() {
        return advicesmstype;
    }

    public void setAdvicesmstype(String advicesmstype) {
        this.advicesmstype = advicesmstype;
    }

    public String getAvaliabletime() {
        return avaliabletime;
    }

    public void setAvaliabletime(String avaliabletime) {
        this.avaliabletime = avaliabletime;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    public String getAuthtype() {
        return authtype;
    }

    public void setAuthtype(String authtype) {
        this.authtype = authtype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtinfos() {
        return extinfos;
    }

    public void setExtinfos(String extinfos) {
        this.extinfos = extinfos;
    }

    
}
