package fc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 当前线程抛出异常时,锁不会自动释放
 * Created by liangl on 2019/8/7.
 */
public class TestLock {
    public static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println(11111);
                    new RuntimeException();
                } catch (Exception e) {

                }
                System.out.println("end thread one");
            }
        },"thread-one");
        threadOne.start();
        Thread.sleep(1000);

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start thread two");
                lock.lock();
                try {
                    System.out.println(22222);
                } catch (Exception e) {

                }
                System.out.println("end thread two");
            }
        },"thread-two");
        threadTwo.start();

        System.out.println("end main");
    }

}
