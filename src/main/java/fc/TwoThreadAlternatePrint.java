package fc;

/**
 * 基于synchronized和wait/notify
 * 两线程交替打印1-100
 * Created by liangl on 2019/2/12.
 */
public class TwoThreadAlternatePrint {
    static Object lock = new Object();
    static Boolean isRun = false;

    public static void main(String[] args) throws InterruptedException {



        class ThreadOne extends Thread {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i =1;i < 100; i+=2) {
                        System.out.println("one---"+i);
                        lock.notify();
                        if (!isRun) {
                            isRun = true;
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
            }
        }

        class ThreadTwo extends Thread {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i =2;i <= 100; i+=2) {

                        System.out.println("two---"+i);
                        lock.notify();
                        if (isRun) {
                            isRun = false;
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
            }
        }


        new ThreadOne().start();
        Thread.sleep(1000L);
        new ThreadTwo().start();

    }

}
