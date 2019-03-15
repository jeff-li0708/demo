package hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Hystrix的简单使用
 * 对不同的业务做隔离
 * Created by liangl on 2019/3/14.
 */
public class HystrixTest {
    static Logger logger = LoggerFactory.getLogger(HystrixTest.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        OrderCommand orderCommand = new OrderCommand("20190314001");
        OrderCommand orderCommand2 = new OrderCommand("20190314002");
        String result = orderCommand.execute();
        logger.info("result=[{}]", result);

        Future<String> queue = orderCommand2.queue();
        String value = queue.get(999, TimeUnit.MILLISECONDS);
        logger.info("value=[{}]", value);

        UserCommand userCommand = new UserCommand("张三");
        userCommand.execute();
    }
}
