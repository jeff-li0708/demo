package fc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 让两个线程达到一个状态后再全部同时执行
 * Created by liangl on 2019/2/14.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread()+"-------------->step1");
                    cyclicBarrier.await();

                    System.out.println(Thread.currentThread()+"-------------->step2");
                    cyclicBarrier.await();

                    System.out.println(Thread.currentThread()+"-------------->step3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        },"threadOne");

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread()+"-------------->step1");
                    cyclicBarrier.await();

                    System.out.println(Thread.currentThread()+"-------------->step2");
                    cyclicBarrier.await();

                    System.out.println(Thread.currentThread()+"-------------->step3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        },"threadTwo");

        threadOne.start();
        threadTwo.start();
    }


}
