package util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by linli on 2016/3/11.
 */
public class ExecutorUtils {

    static ExecutorService es = new ExecutorServiceAdapter(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*3));

    public static void execute(Runnable cmd) {
        es.execute(cmd);
    }

    public static void close() {
        es.shutdown();
    }

    public static ExecutorService getES(boolean isNew) {
        if (isNew) return new ExecutorServiceAdapter(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*3));
        return es;
    }


    /**
     * 用于包装eecutorservice
     */
    public static class ExecutorServiceAdapter implements ExecutorService {

        ExecutorService es;

        public ExecutorServiceAdapter(ExecutorService es) {
            this.es = es;
        }

        @Override
        public void shutdown() {
            es.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            return es.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return es.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return es.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return es.awaitTermination(timeout, unit);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return es.submit(new CallableAdapter(task));
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return es.submit(new RunnableAdapter(task), result);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return es.submit(new RunnableAdapter(task));
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return es.invokeAll(CallableAdapter.assemble(tasks));
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return es.invokeAll(CallableAdapter.assemble(tasks), timeout, unit);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return es.invokeAny(CallableAdapter.assemble(tasks));
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return es.invokeAny(CallableAdapter.assemble(tasks), timeout, unit);
        }

        @Override
        public void execute(Runnable command) {
            RunnableAdapter adapter = new RunnableAdapter(command);
            es.execute(adapter);
        }

        private static class RunnableAdapter implements Runnable {

            Runnable r;
            Object object;

            RunnableAdapter(Runnable r) {
                object = ContextUtils.get();
                this.r = r;
            }

            @Override
            public void run() {
                //真正执行时，set一个local
                if (object != null) {
                    ContextUtils.set(object);
                }
                r.run();
            }

        }

        private static class CallableAdapter implements Callable {

            Callable r;
            Object object;

            CallableAdapter(Callable r) {
                object = ContextUtils.get();
                this.r = r;
            }

            @Override
            public Object call() throws Exception {
                //真正执行时，set一个local
                if (object != null) {
                    ContextUtils.set(object);
                }
                return r.call();
            }

            public static <T> Collection<? extends Callable<T>> assemble(Collection<? extends Callable<T>> tasks) {
                List<Callable<T>> adapters = new LinkedList();
                if (tasks != null) {
                    for (Callable callable : tasks) {
                        adapters.add(new CallableAdapter(callable));
                    }
                }
                return adapters;
            }
        }
    }
}
