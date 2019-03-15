package fc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by liangl on 2019/3/13.
 */
public class Test2 {

    static Logger logger = (Logger) LoggerFactory.getLogger(Test2.class);

    public static void main(String[] args) {
//        int arr[] = {1,3,8,3,6,7,4,9};
//        int size = 3;
//        int arrMax[] = new int[arr.length-2];
//        int max = arr[0],smax = arr[0];
//        for (int i = 0,len = arr.length; i < len;i++) {
//            if (arr[i] > max) max = arr[i];
//        }
        ExecutorService es = new ThreadPoolExecutor(1,1,0, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
        es.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("--------22222------------");
            }
        });
        es.execute(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                while (true) {
                    a++;
                    if (a == 10){
                        System.out.println(1/0);
                    }
                    if (a==20){
                        break;
                    }
                    logger.info(""+a);
                }
            }
        });
    }
}
