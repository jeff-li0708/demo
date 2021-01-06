package thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间通信-基于lock的condition
 *
 * @date 2017年12月21日
 */

public class ThreadCommunicationA {

    private Lock lock = new ReentrantLock();

    private Condition c0 = lock.newCondition();

    private Condition c1 = lock.newCondition();

    private Condition c2 = lock.newCondition();

    private int status = 0;

    private static final int LIMIT = 10000;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = new ThreadPoolExecutor(3, 30, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(300),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        final ThreadCommunicationA o = new ThreadCommunicationA();
        exec.submit(o.createTask(0));
        exec.submit(o.createTask(1));
        exec.submit(o.createTask(2));
        exec.shutdown();
        exec.awaitTermination(5, TimeUnit.MINUTES);
    }

    /**
     * @param
     * @param i
     */

    private Runnable createTask(final int i) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    m(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void m(final int n) throws InterruptedException {
        for (int i = n; i < LIMIT; i += 3) {
            lock.lock();
            try {
                switch (n) {
                    case 0:
                        if (status != 0) {
                            c0.await();
                        }
                        System.out.println(Thread.currentThread().getName() + "___" + i);
                        status = 1;
                        c1.signal();
                        break;
                    case 1:
                        if (status != 1) {
                            c1.await();
                        }
                        System.out.println(Thread.currentThread().getName() + "___" + i);
                        status = 2;
                        c2.signal();
                        break;
                    case 2:
                        if (status != 2) {
                            c2.await();
                        }
                        System.out.println(Thread.currentThread().getName() + "___" + i);
                        status = 0;
                        c0.signal();
                        break;
                    default:
                        break;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
