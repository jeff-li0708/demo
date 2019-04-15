package com;

import com.yeepay.sqkkseperator.bean.AuthBindCardReq;
import com.yeepay.sqkkseperator.service.YibaoPayService;
import org.junit.Test;

/**
 * Created by liangl on 2019/4/15.
 */
public class YibaoPayTest {

    private YibaoPayService yibaoPayService = new YibaoPayService();

    /**
     *
     */
    @Test
    public void testBindCard() {
        AuthBindCardReq req = new AuthBindCardReq();
        req.setRequestno("20190415001"); //单号
        req.setIdentityid("J11899");
        req.setIdentitytype("USER_ID");
        req.setCardno("6222022314007119475"); //银行卡号
        req.setIdcardno("511502199012168436");
        req.setUsername("李亮");
        req.setPhone("18728344025");
        yibaoPayService.authBindCardReq(req);
    }

    /**
     * 短信重发
     */
    @Test
    public void testBindCardResend() {
        yibaoPayService.authBindCardResend("20190415001","MESSAGE");
    }

    /**
     * 查询绑卡列表
     */
    @Test
    public void testQueryCardList() {
        yibaoPayService.authListqueryServlet("J11899","USER_ID");
    }

    @Test
    public void testPayAccountCheck() {
        yibaoPayService.payAccountCheck("2019-04-11","2019-04-15");
    }
}
