package com;

import com.yeepay.sqkkseperator.bean.AuthBindCardReq;
import com.yeepay.sqkkseperator.bean.PayToUserReq;
import com.yeepay.sqkkseperator.config.Config;
import com.yeepay.sqkkseperator.service.YibaoPayService;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
/**
 * Created by liangl on 2019/4/15.
 */
public class YibaoPayTest {

    private YibaoPayService yibaoPayService = new YibaoPayService();

    private Config config = Config.getInstance();

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
        assertEquals(1,3-2);


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


    /**
     * 还款对账记录
     */
    @Test
    public void testPayAccountCheck() {
        yibaoPayService.payAccountCheck("2019-04-11","2019-04-15");
    }

    /**
     * 查询商户余额
     */
    @Test
    public void testQueryBalance() {
        Map<String,String> result = yibaoPayService.accountBalanaceQuery();
        System.out.println(result);
    }

    @Test
    public void testPayToUser() {
        PayToUserReq req = new PayToUserReq();
        req.setAccount_Name("李亮");
        req.setAccount_Number("6222022314007119475");
        req.setAmount("0.01");
        req.setBank_Code("ICBC");
        req.setBank_Name("工商银行");
        req.setBatch_No("20190417002");
        req.setOrder_Id("2018041700201");
        Map<String, String> result = yibaoPayService.payToUser(req);
        System.out.println(result);
    }

    @Test
    public void testQueryPayToUser(){
        Map<String, String> result = yibaoPayService.queryTransfer("20190417001","2018041700101");
        System.out.println(result);
    }
}
