package fc;

import util.ExecutorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by liangl on 2018/11/27.
 */
public  class CallerTaskTest {
    public static class CallerTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("111");
            Thread.sleep(2000L);
            return "aaa";
        }
    }

    static class CallerTaskChild extends CallerTask {
        @Override
        public String call() throws Exception {
            Thread.sleep(1000L);
            return "bbb";
        }

    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                List<CallerTask> tasks = new ArrayList<CallerTask>();
                for (int i = 0; i< 10; i++) {
                    tasks.add(new CallerTask());
                }
                Long start = System.currentTimeMillis();
                List<Future<String>> results = null;
                try {
                    results = ExecutorUtils.getES(false).invokeAll(tasks, 10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    ExecutorUtils.close();
                }
                Long end = System.currentTimeMillis();
                System.out.println("One:"+String.valueOf(end - start));
            }
        },"threadOne");

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                List<CallerTask> tasks = new ArrayList<CallerTask>();
                for (int i = 0; i< 16; i++) {
                    tasks.add(new CallerTaskChild());
                }
                Long start = System.currentTimeMillis();
                List<Future<String>> results = null;
                ExecutorService es = ExecutorUtils.getES(true);
                try {
                    results = es.invokeAll(tasks, 10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    es.shutdown();
                }
                Long end = System.currentTimeMillis();
                System.out.println("Two:"+String.valueOf(end - start));
            }
        },"threadTwo");
        threadOne.start();
        threadTwo.start();

    }
}
