package fc;

import bean.User;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者消费者
 * Created by liangl on 2019/2/14.
 */
public class ProductConsumeTest {

    public static void main(String[] args) {
        final Queue<User> queue = new LinkedBlockingQueue<User>();
        for (int i = 1; i <= 10; i++) {
            queue.add(new User("name-->"+i));
        }

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (queue){
                    while (true) {
                        while (queue.size() >= 10){
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        User user = new User("name-->" + Math.random());
                        queue.add(user);
                        System.out.println("product-->"+user.getUserName()+",size-->"+queue.size());
                        //生产者后通知
                        queue.notifyAll();
                    }
                }

            }
        },"producer");

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (queue) {
                    while (true) {
                        while (queue.size() == 0) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        User user = queue.poll();
                        deal(user);
                        queue.notifyAll();
                    }
                }
            }
        },"consumer");

        threadOne.start();
        threadTwo.start();

    }

    //启动线程池处理
    public static ExecutorService executorService = Executors.newScheduledThreadPool(10);

    public static void deal(final User user) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread()+"consume--->"+user.getUserName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
