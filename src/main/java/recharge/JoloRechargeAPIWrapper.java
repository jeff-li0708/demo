package recharge;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recharge.http.SimpleHttpService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangl on 2019/1/17.
 */
@Service
public class JoloRechargeAPIWrapper {
    Logger logger= LoggerFactory.getLogger(JoloRechargeAPIWrapper.class);
    @Autowired
    MobileRechargeConfig rechargeConfig;

    @Autowired
    SimpleHttpService simpleHttpService;

    /**
     * 查询JOPO账户余额
     * @return
     * @throws CommException
     */
    public BigDecimal queryAccountBalance() throws CommException{
        try {

            Map<String, String> paramsMap = new HashMap();
            paramsMap.put("userid", rechargeConfig.getUserId());
            paramsMap.put("key", rechargeConfig.getKey());
            paramsMap.put("type", "json");
            String result = simpleHttpService.doJsonGet(RechargeConstants.QUERY_BALANCE, paramsMap);
            logger.info("jolo api query balance,response:{}",result);
            /**
             * {
             status: "SUCCESS",
             error: "0",
             balance: "774953.00",
             totalearn: "9840.00",
             time: "March 02 2018 05:50:12 PM"
             }
             */
            JSONObject jsonObject = JSON.parseObject(result);
            if (RechargeConstants.SUCCESS.equals(jsonObject.getString("status")))
                return jsonObject.getBigDecimal("balance");
        } catch (Exception e) {
            throw new CommException(-1,"查询JOPO账户余额错误");
        }
        return BigDecimal.ZERO;
    }

    /**
     * 充值
     * @param phone
     * @param amount
     * @param orderNo
     * @throws CommException
     */
    public JSONObject recharge(String phone,String operator,BigDecimal amount,String orderNo) throws CommException {
        JSONObject resultDate = new JSONObject();
        try {
            Map<String, String> paramsMap = new HashMap();
            paramsMap.put("userid", rechargeConfig.getUserId());
            paramsMap.put("key", rechargeConfig.getKey());
            paramsMap.put("type", "json");
            paramsMap.put("mode","0");//0 for sandox,1 for live
            paramsMap.put("META-INF/services", phone);
            paramsMap.put("amount",String.valueOf(amount));
            paramsMap.put("orderid",orderNo);
            paramsMap.put("operator",operator);
            String result = simpleHttpService.doJsonGet(RechargeConstants.RECHARGE, paramsMap);
            logger.info("jolo api recharge,response:{}",result);
            /**
             * {
             status: "SUCCESS",
             error: "0",
             txid: "Z20180000000000000",
             operator: "AT",
             service: "9999999999",
             amount: "10",
             orderid: "1234567890",
             operatorid: "0",
             balance: "75699.45",
             margin: "0.24",
             time: "March 01 2018 09:48:09 PM"
             }
             */
            resultDate = JSON.parseObject(result);
        } catch (Exception e) {
            throw new CommException(-1,"请求充值接口错误");
        }
        return resultDate;
    }

    /**
     * 根据我们的订单号查询充值状态
     * @param orderNo
     * @throws CommException
     */
    public JSONObject rechargeStatusClient(String orderNo) throws CommException {
        JSONObject resultDate = new JSONObject();
        try {
            Map<String, String> paramsMap = new HashMap();
            paramsMap.put("userid", rechargeConfig.getUserId());
            paramsMap.put("key", rechargeConfig.getKey());
            paramsMap.put("servicetype","1");
            paramsMap.put("txn", orderNo);
            paramsMap.put("type", "json");
            String result = simpleHttpService.doJsonGet(RechargeConstants.RECHARGE_STASUS_CLIENT, paramsMap);
            logger.info("jolo api query recharge status,response:{}",result);
            /**
             * {
             status: "FAILED",
             error: "360",
             txid: "Z2018000000000000",
             clientid: "00973003714",
             service: "9999999999",
             amount: "99",
             time: "January 01 2018 07:04:48 AM",
             margin: "0",
             operatorid: "0"
             }
             */
            resultDate = JSON.parseObject(result);
        } catch (Exception e) {
            throw new CommException(-1,"请求充值状态查询接口错误");
        }
        return resultDate;
    }

    /**
     * 根据外部单号查询状态
     * @param pojoOrderNo
     * @throws CommException
     */
    public void rechargeStatus(String pojoOrderNo) throws CommException {
        try {
            Map<String, String> paramsMap = new HashMap();
            paramsMap.put("userid", rechargeConfig.getUserId());
            paramsMap.put("key", rechargeConfig.getKey());
            paramsMap.put("servicetype","1");
            paramsMap.put("txn", pojoOrderNo);
            paramsMap.put("type", "json");
            String result = simpleHttpService.doJsonGet(RechargeConstants.RECHARGE_STASUS, paramsMap);
            logger.info("jolo api query recharge status,response:{}",result);
        } catch (Exception e) {
            throw new CommException(-1,"请求充值状态查询接口错误");
        }
    }
}
