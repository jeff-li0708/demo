package com.wxpay.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2018/2/3
 *
 * @author zsl
 */
public class WXInterface {

    public static final Logger logger = LoggerFactory.getLogger("wexin");

    public final static String WX_SUCCESS = "SUCCESS";
    public final static String WX_FAIL = "FAIL";

    public final static String WX_RETURN_CODE = "return_code";

    public final static String WX_RETURN_MSG = "return_msg";

    public final static String WX_RESULT_CODE = "result_code";

    public final static String WX_ERR_CODE = "err_code";

    public final static String WX_ERR_CODE_DES = "err_code_des";

    public final static String WX_STATUS = "status";

    public static int toUnixTime(String wxTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return (int) (formatter.parse(wxTime).getTime() / 1000);
    }


    public enum ERR_CODE{
        NO_AUTH("NO_AUTH","没有该接口权限"),
        AMOUNT_LIMIT("AMOUNT_LIMIT","金额超限"),
        PARAM_ERROR("PARAM_ERROR", "参数错误"),
        OPENID_ERROR("OPENID_ERROR","Openid错误"),
        SEND_FAILED("SEND_FAILED","付款错误"),
        NOTENOUGH("NOTENOUGH","余额不足"),
        SYSTEMERROR("SYSTEMERROR","系统繁忙，请稍后再试"),
        NAME_MISMATCH("NAME_MISMATCH","姓名校验出错"),
        SIGN_ERROR("SIGN_ERROR","签名错误"),
        XML_ERROR("XML_ERROR","Post内容出错"),
        FATAL_ERROR("FATAL_ERROR","两次请求参数不一致"),
        FREQ_LIMIT("FREQ_LIMIT","超过频率限制，请稍后再试"),
        MONEY_LIMIT("MONEY_LIMIT","已经达到今日付款总额上限/已达到付款给此用户额度上限"),
        CA_ERROR("CA_ERROR","商户API证书校验出错"),
        V2_ACCOUNT_SIMPLE_BAN("V2_ACCOUNT_SIMPLE_BAN","无法给非实名用户付款"),
        PARAM_IS_NOT_UTF8("PARAM_IS_NOT_UTF8","请求参数中包含非utf8编码字符"),
        SENDNUM_LIMIT("SENDNUM_LIMIT","该用户今日付款次数超过限制,如有需要请登录微信支付商户平台更改API安全配置"),
        NOT_FOUND("NOT_FOUND","指定单号数据不存在"),
        ORDERNOTEXIST("ORDERNOTEXIST","订单不存在");

        private String code;
        private String des;
        ERR_CODE(String code,String des){
            this.code = code;
            this.des = des;
        }
        public String getCode() {
            return this.code;
        }
    }
    //需要查询确认状态的错误码
    public static final List<String> NEED_QUERY_RESULT = Arrays.asList("SEND_FAILED","SYSTEMERROR");

}
