package schema.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import util.SpringBeanFactory;

/**
 * 策略模式
 * Created by liangl on 2019/8/21.
 */
@Component
public class PayBeanFactory {

    private final static Logger logger = LoggerFactory.getLogger(PayBeanFactory.class);

    public PayService getInstance(String channel) {
        String clazz = PayChannelEnum.getClazzByChannel(channel);
        PayService payService = null;
        try {
            payService = (PayService) SpringBeanFactory.getBean(Class.forName(clazz));
        } catch (Exception e) {
            logger.error("get pay service instance error");
        }
        return payService;
    }
}
