package hystrix;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * Created by liangl on 2019/3/14.
 */
public class OrderCommand extends HystrixCommand<String> {

    Logger logger =  LoggerFactory.getLogger(OrderCommand.class);

    private final String name;
    public OrderCommand(String name) {
        //最少配置:指定命令组名(CommandGroup)
        super(Setter.withGroupKey(
                //服务分组
                HystrixCommandGroupKey.Factory.asKey("OrderGroup"))
                //线程分组
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("OrderPool"))
                //线程池配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withKeepAliveTimeMinutes(5)
                        .withMaxQueueSize(10)
                        .withQueueSizeRejectionThreshold(10000))

                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                .withExecutionIsolationThreadTimeoutInMilliseconds(3000))//设置超时时间
        );
        this.name = name;
    }
    @Override
    protected String getFallback() {//重写getFallback 当run运行错误时返回exeucute Falled
         return "exeucute Falled";
    }

    @Override
    public String run() throws Exception{
        logger.info("run orderCommand"+name);
        if ("20190314001".equals(name)) {
            Long now = System.currentTimeMillis();
            Long end = now + 3000;
            while (now < end) {
                now = System.currentTimeMillis();
            }
        }
        else
            TimeUnit.MILLISECONDS.sleep(999);
        return name;
    }
}
