package com.wxpay.sdk;

/**
 * 常量
 */
public class WXPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String MICROPAY_URL     = "https://api.mch.weixin.qq.com/pay/micropay";
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String ORDERQUERY_URL   = "https://api.mch.weixin.qq.com/pay/orderquery";
    public static final String REVERSE_URL      = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
    public static final String CLOSEORDER_URL   = "https://api.mch.weixin.qq.com/pay/closeorder";
    public static final String REFUND_URL       = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    public static final String REFUNDQUERY_URL  = "https://api.mch.weixin.qq.com/pay/refundquery";
    public static final String DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    public static final String REPORT_URL       = "https://api.mch.weixin.qq.com/payitil/report";
    public static final String SHORTURL_URL     = "https://api.mch.weixin.qq.com/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/tools/authcodetoopenid";
    public static final String PAYTOUSER_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers"; //企业打款给个人
    public static final String PAYTOUSER_QUERY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo"; //查询企业付款
    public static final String SEND_COUPON_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/send_coupon"; //发放代金券
    public static final String QUERY_COUPON_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/query_coupon_stock"; //根据批次ID查询代金券
    public static final String QUERY_SINGLE_COUPON_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/querycouponsinfo";//查询单个代金券
    public static final String PAY_BANK_CARD_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";//微信付款到银行卡
    public static final String QUERY_PAY_BANK_CARD_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/query_bank";//查询企业付款到银行卡
    public static final String GET_RSA_PUBLIC_KEY = "https://fraud.mch.weixin.qq.com/risk/getpublickey";//获取RSA公钥

    // sandbox
    public static final String SANDBOX_GET_SIGN_KEY = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey"; //获取沙箱环境的key
    public static final String SANDBOX_MICROPAY_URL     = "https://api.mch.weixin.qq.com/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL   = "https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL      = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL   = "https://api.mch.weixin.qq.com/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL       = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL  = "https://api.mch.weixin.qq.com/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL       = "https://api.mch.weixin.qq.com/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL     = "https://api.mch.weixin.qq.com/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/sandboxnew/tools/authcodetoopenid";
    public static final String SANDBOX_PAYTOUSER_URL    = "https://api.mch.weixin.qq.com/sandboxnew/mmpaymkttransfers/promotion/transfers"; //企业打款给个人
    public static final String SANDBOX_PAYTOUSER_QUERY_URL    = "https://api.mch.weixin.qq.com/sandboxnew/mmpaymkttransfers/gettransferinfo"; //查询企业付款
    public static final String SANDBOX_SEND_COUPON_URL = "https://api.mch.weixin.qq.com/sandboxnew/mmpaymkttransfers/send_coupon"; //发放代金券
    public static final String SANDBOX_QUERY_COUPON_URL = "https://api.mch.weixin.qq.com/sandboxnew/mmpaymkttransfers/query_coupon_stock"; //根据批次ID查询代金券
    public static final String SANDBOX_QUERY_SINGLE_COUPON_URL = "https://api.mch.weixin.qq.com/sandboxnew/mmpaymkttransfers/querycouponsinfo";//查询单个代金券
    public static final String SANDBOX_PAY_BANK_CARD_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";//微信付款到银行卡
    public static final String SANDBOX_QUERY_PAY_BANK_CARD_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/query_bank";//查询企业付款到银行卡
    public static final String SANDBOX_GET_RSA_PUBLIC_KEY = "https://fraud.mch.weixin.qq.com/risk/getpublickey";//获取RSA公钥
}