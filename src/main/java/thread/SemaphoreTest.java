package thread;

import java.util.concurrent.Semaphore;

/**
 * Semaphore信号量通常做为控制线程并发个数的工具来使用，它可以用来限制同时并发访问资源的线程个数。
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);//同时只能有两个线程运行

        // 启动计数线程
        for (int i = 1; i <= 10; i++) {
            new SemaphoreThread(semaphore).start();
        }
    }

    static class SemaphoreThread extends Thread {

        private Semaphore semaphore;

        public SemaphoreThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }
         public void run() {
            try {
                semaphore.acquire();//获取执行许可
                Thread.sleep(1000);
                System.out.println(this.getName() + "线程,开始计数");
                // 模拟计数时长
                Thread.sleep(1000);
                // 一个线程完成，允许下一个线程开始计数
                System.out.println(this.getName() + "线程,计数完毕");
                semaphore.release();//归还许可

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
}
