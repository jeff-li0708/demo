package recharge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by liangl on 2019/1/17.
 */
@Component
public class MobileRechargeConfig {

    @Value("${recharge.jolo.userid}")
    private String userId;
    @Value("${recharge.jolo.key}")
    private String key;


    public String getUserId() {
        return userId;
    }

    public String getKey() {
        return key;
    }

}
