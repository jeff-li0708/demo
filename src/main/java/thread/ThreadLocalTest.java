package thread;

import bean.User;
import org.springframework.core.NamedThreadLocal;

/**
 * 每个线程都有一个ThreadLocal.ThreadLocalMap类型的属性threadLocals，其中key就是当前的ThreadLocal,比如下面的resources和resources2，value是调用resources.set(user)时传入的参数
 */
public class ThreadLocalTest {
    private static final ThreadLocal<User> resources = new NamedThreadLocal<>("Transactional resources");

    private static final ThreadLocal<User> resources2 = new NamedThreadLocal<>("Transactional resources");

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                User user = new User();
                user.setUserName(name);
                resources.set(user);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":"+resources.get().getUserName());
            }
        },"thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                User user = new User();
                user.setUserName(name);
                resources.set(user);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":"+resources.get().getUserName());
            }
        },"thread2");

        thread1.start();
        thread2.start();
        User user = resources.get();
        System.out.println(user);
    }
}
