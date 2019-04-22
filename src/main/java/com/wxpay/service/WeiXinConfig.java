package com.wxpay.service;

import com.wxpay.sdk.WXPay;
import com.wxpay.sdk.WXPayConfig;
import com.wxpay.sdk.WXPayConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class WeiXinConfig implements WXPayConfig {
    @Value("${weixin.appId}")
    private String appId;

    @Value("${weixin.secret}")
    private String secret;


    @Value("${weixin.mchId}")
    private String mchId;

    @Value("${weixin.key}")
    private String key;

    @Value("${weixin.testKey}")
    private String testKey;

    @Value("${weixin.manager.appId}")
    private String managerAppId;

    @Value("${weixin.manager.secret}")
    private String managerSecret;



    @Value("${weixin.gongzhonghao.appId}")
    private String gongzhonghaoAppId;

    @Value("${weixin.gongzhonghao.secret}")
    private String gongzhonghaoSecret;

    @Override
    public String getAppID() {
        return appId;
    }
    public String getManagerAppId() {
        return managerAppId;
    }
    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTestKey() {
        return testKey;
    }
    @Override
    public InputStream getCertStream() {
        return WeiXinConfig.class.getClassLoader().getResourceAsStream("cert/apiclient_cert.p12");
    }

    @Override
    public InputStream getRSAPublicKeyStream() {
        return WeiXinConfig.class.getClassLoader().getResourceAsStream("cert/public.pem");
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 1000 * 60;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 1000 * 60;
    }

    public WXPay getPay(boolean sandBox){
        if (sandBox) {
            this.key = this.testKey;
        }
        return new WXPay(this, WXPayConstants.SignType.MD5,sandBox);//沙盒
    }

    public String getGongzhonghaoAppId() {
        return gongzhonghaoAppId;
    }

    public void setGongzhonghaoAppId(String gongzhonghaoAppId) {
        this.gongzhonghaoAppId = gongzhonghaoAppId;
    }

    public String getGongzhonghaoSecret() {
        return gongzhonghaoSecret;
    }

    public void setGongzhonghaoSecret(String gongzhonghaoSecret) {
        this.gongzhonghaoSecret = gongzhonghaoSecret;
    }
}
