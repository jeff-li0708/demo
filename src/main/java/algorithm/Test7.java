package algorithm;

import util.DateUtil;

import java.util.concurrent.locks.LockSupport;

public class Test7 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1-"+System.currentTimeMillis());
                Thread.yield();
                Thread.yield();
                LockSupport.park(this);
                System.out.println("1-"+System.currentTimeMillis());
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2-"+System.currentTimeMillis());
                for (int i =0;i<100000;i++);
                System.out.println("2-"+System.currentTimeMillis());
            }
        });
        t2.start();

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("3-"+System.currentTimeMillis());
                for (int i =0;i<500000;i++);
                System.out.println("3-"+System.currentTimeMillis());
            }
        });
        t3.start();

        System.out.println(333);
        Thread.sleep(1000);
//        LockSupport.unpark(t1);

    }
}
