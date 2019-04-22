package com.wxpay.sdk;

/**
 * Created by liangl on 2018/11/2.
 */
public class WXPayDetailDTO {
    private String tradeTime;           //交易时间
    private String appId;               //公众账号Id
    private String merchNo;             //商户号
    private String superMerchaNo;       //特约商户号
    private String deviceNo;            //设备号
    private String wxOrderNo;           //微信订单号
    private String merchOrderNo;        //商户订单号
    private String openId;              //用户标识openId
    private String tradeType;           //交易类型
    private String tradeStatus;         //交易状态
    private String payBank;             //付款银行
    private String payCurrency;         //付款币种
    private String needSettleAmt;       //应结算金额
    private String couponAmt;           //代金券金额
    private String wxRefundOrderNo;     //微信退款单号
    private String merchRefundOrderNo;  //商户退款单号
    private String refundAmt;           //退款金额
    private String couponRefundAmt;     //充值券退款金额
    private String refundType;          //退款类型
    private String refundStatus;        //退款状态
    private String goodsName;           //商品名称
    private String merchData;           //商户数据包
    private String feeAmt;              //手续费
    private String feeRate;             //费率
    private String orderAmt;            //订单金额
    private String applyRefundAmt;      //申请退款金额
    private String rateRemark;          //费率备注


    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchNo() {
        return merchNo;
    }

    public void setMerchNo(String merchNo) {
        this.merchNo = merchNo;
    }

    public String getSuperMerchaNo() {
        return superMerchaNo;
    }

    public void setSuperMerchaNo(String superMerchaNo) {
        this.superMerchaNo = superMerchaNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getWxOrderNo() {
        return wxOrderNo;
    }

    public void setWxOrderNo(String wxOrderNo) {
        this.wxOrderNo = wxOrderNo;
    }

    public String getMerchOrderNo() {
        return merchOrderNo;
    }

    public void setMerchOrderNo(String merchOrderNo) {
        this.merchOrderNo = merchOrderNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public String getPayCurrency() {
        return payCurrency;
    }

    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency;
    }

    public String getNeedSettleAmt() {
        return needSettleAmt;
    }

    public void setNeedSettleAmt(String needSettleAmt) {
        this.needSettleAmt = needSettleAmt;
    }

    public String getCouponAmt() {
        return couponAmt;
    }

    public void setCouponAmt(String couponAmt) {
        this.couponAmt = couponAmt;
    }

    public String getWxRefundOrderNo() {
        return wxRefundOrderNo;
    }

    public void setWxRefundOrderNo(String wxRefundOrderNo) {
        this.wxRefundOrderNo = wxRefundOrderNo;
    }

    public String getMerchRefundOrderNo() {
        return merchRefundOrderNo;
    }

    public void setMerchRefundOrderNo(String merchRefundOrderNo) {
        this.merchRefundOrderNo = merchRefundOrderNo;
    }

    public String getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getCouponRefundAmt() {
        return couponRefundAmt;
    }

    public void setCouponRefundAmt(String couponRefundAmt) {
        this.couponRefundAmt = couponRefundAmt;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMerchData() {
        return merchData;
    }

    public void setMerchData(String merchData) {
        this.merchData = merchData;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getApplyRefundAmt() {
        return applyRefundAmt;
    }

    public void setApplyRefundAmt(String applyRefundAmt) {
        this.applyRefundAmt = applyRefundAmt;
    }

    public String getRateRemark() {
        return rateRemark;
    }

    public void setRateRemark(String rateRemark) {
        this.rateRemark = rateRemark;
    }
}
