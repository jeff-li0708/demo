package com.yeepay.sqkkseperator.bean;

/**
 * Created by liangl on 2019/4/9.
 */
public class BindCardInfo {
    private String bindid; // 绑卡 ID
    private String cardtop; // 银行卡前 6 位
    private String cardlast; // 银行卡后 4 位
    private String cardname; //银行卡名称
    private String phone; // 手机号
    private String bankcode; //银行编码
    private String verifystatus; // 是否签约， true 为已签约， false 为未签约， 返回空可重试查询

    public String getBindid() {
        return bindid;
    }

    public void setBindid(String bindid) {
        this.bindid = bindid;
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

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getVerifystatus() {
        return verifystatus;
    }

    public void setVerifystatus(String verifystatus) {
        this.verifystatus = verifystatus;
    }
}
