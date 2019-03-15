package recharge;

/**
 * 充值相关常量
 * Created by liangl on 2019/1/17.
 */
public class RechargeConstants {

    public static final String FAILED   = "FAILED";
    public static final String SUCCESS  = "SUCCESS";

    public static final String QUERY_BALANCE = "https://joloapi.com/api/balance.php"; //查询账户余额
    public static final String RECHARGE = "https://joloapi.com/api/recharge.php"; //充值
    public static final String RECHARGE_STASUS = "https://joloapi.com/api/rechargestatus.php"; //根据JOPO订单号查询状态
    public static final String RECHARGE_STASUS_CLIENT = "https://joloapi.com/api/rechargestatus_client.php"; //根据订单号查询状态

}
