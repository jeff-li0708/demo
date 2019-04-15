package com.yeepay.sqkkseperator.bean;

/**
 * Created by liangl on 2019/4/12.
 */
public class BindCardPayReq {
    private String requestno;    //绑卡支付单号
    private String issms;           //是否发短信
    private String identityid;

    /**MAC： 网卡地址
     * IMEI： 国际移动设备标识
     * ID_CARD： 用户身份证号
     * PHONE： 手机号
     * EMAIL： 邮箱
     * USER_ID： 用户 id
     * AGREEMENT_NO： 用户纸质订单协议号
     */
    private String identitytype; //用户标识类型
    private String cardtop;       //卡号前6位
    private String cardlast;     //卡号后四位
    private String amount;       //金额 0.01
    private String terminalno; //终端号 协议支付： SQKKSCENEKJ010,代扣： SQKKSCENE10
    private String avaliabletime; // 有效时间1min < n < 7day
    private String advicesmstype; //MESSAGE： 短验码将以短信的方式发送给用户,VOICE： 短验码将以语音的方式发送给用户
    private String callbackurl;   //回调地址
    private String remark;         //备注
    private String requesttime;    //请求时间 yyyy-MM-dd HH:mm:ss
    private String productname;

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getIssms() {
        return issms;
    }

    public void setIssms(String issms) {
        this.issms = issms;
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

    public String getCardtop() {
        return cardtop;
    }

    public void setCardtop(String cardtop) {
        this.cardtop = cardtop;
    }

    public String getCardlast() {
        return cardlast;
    }

    public void setCardlast(String cardlast) {
        this.cardlast = cardlast;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTerminalno() {
        return terminalno;
    }

    public void setTerminalno(String terminalno) {
        this.terminalno = terminalno;
    }

    public String getAvaliabletime() {
        return avaliabletime;
    }

    public void setAvaliabletime(String avaliabletime) {
        this.avaliabletime = avaliabletime;
    }

    public String getAdvicesmstype() {
        return advicesmstype;
    }

    public void setAdvicesmstype(String advicesmstype) {
        this.advicesmstype = advicesmstype;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
