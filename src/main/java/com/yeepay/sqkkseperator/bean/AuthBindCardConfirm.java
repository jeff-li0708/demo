package com.yeepay.sqkkseperator.bean;

/**
 * Created by liangl on 2019/4/8.
 */
public class AuthBindCardConfirm {
    private String requestno;   //绑卡请求单号
    private String validatecode;//验证码

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getValidatecode() {
        return validatecode;
    }

    public void setValidatecode(String validatecode) {
        this.validatecode = validatecode;
    }
}
