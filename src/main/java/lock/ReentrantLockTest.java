package lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liangl on 2019/9/6.
 */
public class ReentrantLockTest {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                out();
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                out();
            }
        },"t2");
        t1.start();
        t2.start();
    }

    public static void out(){
        lock.lock();
        System.out.println(Thread.currentThread().getName()+"-"+System.currentTimeMillis());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
