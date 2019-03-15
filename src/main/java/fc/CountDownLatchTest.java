package fc;

import java.util.concurrent.CountDownLatch;

/**
 * 并发执行并最终汇总结果
 * 计数器的值为0时,调countDownLatch.await()方法立刻返回
 * Created by liangl on 2019/2/13.
 */
public class CountDownLatchTest {

    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
            }
        });

        threadOne.start();
        threadTwo.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----end-----------");
    }

}
