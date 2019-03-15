package hystrix;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liangl on 2019/3/14.
 */
public class UserCommand extends HystrixCommand<String> {

    private Logger logger = LoggerFactory.getLogger(UserCommand.class);
    private String userName;

    public UserCommand(String userName) {

        super(Setter.withGroupKey(
                //服务分组
                HystrixCommandGroupKey.Factory.asKey("UserGroup"))
                //线程分组
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("UserPool"))

                //线程池配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withKeepAliveTimeMinutes(5)
                        .withMaxQueueSize(10)
                        .withQueueSizeRejectionThreshold(10000))

                //线程池隔离
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                .withExecutionIsolationThreadTimeoutInMilliseconds(500))
        );
        this.userName = userName;
    }

    @Override
    public String run() throws Exception {
        logger.info("run userCommand");
        //TimeUnit.MILLISECONDS.sleep(1000);
        return userName;
    }
}
