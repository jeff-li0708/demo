package threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ThreadPoolTest2 {
    static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("consumer-queue-thread-%d").build();
    static ExecutorService es = new ThreadPoolExt(0, 2,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(4),namedThreadFactory,new RejectedPolicyWithReport());

    public static void main(String[] args) {
        for (int i=0;i<8;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            es.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
