package com.yeepay.sqkkseperator.bean;

/**
 * Created by liangl on 2019/4/8.
 */
public class AuthBindCardResend {

    private String requestno; //原绑卡请求号
    private String advicesmstype; //短信发送类型 MESSAGE： 短验码将以短信的方式发送给用户VOICE： 短验码将以语音的方式发送给用户默认值为上次请求的建议发送类型

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getAdvicesmstype() {
        return advicesmstype;
    }

    public void setAdvicesmstype(String advicesmstype) {
        this.advicesmstype = advicesmstype;
    }
}
