package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于ReentrantLock和Condition和await/signal
 * 两线程交替打印
 * Created by liangl on 2021/1/6
 */
public class TwoThreadAlternatePrint2 {

        private static Lock lock = new ReentrantLock();
        private static Condition c1 = lock.newCondition();
        private static Condition c2 = lock.newCondition();
        private static CountDownLatch count = new CountDownLatch(1);//这个的作用是让线程t2后执行

        public static void main(String[] args) {
            String c = "ABCDEFGHI";
            char[] ca = c.toCharArray();
            String n = "123456789";
            char[] na = n.toCharArray();

            Thread t1 = new Thread(() -> {
                try {
                    lock.lock();
                    count.countDown();
                    for(char caa : ca) {
                        c1.signal();
                        System.out.print(caa);
                        c2.await();
                    }
                    c1.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });

            Thread t2 = new Thread(() -> {
                try {
                    count.await();
                    lock.lock();
                    for(char naa : na) {
                        c2.signal();
                        System.out.print(naa);
                        c1.await();
                    }
                    c2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });

            t1.start();
            t2.start();
        }
    }