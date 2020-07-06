package fc;

import bean.User;
import org.openjdk.jol.info.ClassLayout;

/**
 * 对象头分析
 */
public class ObjectHeadTest {

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println("new Object:" + ClassLayout.parseInstance(o).toPrintable());
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o2 = new Object();
        System.out.println("new Object2:" + ClassLayout.parseInstance(o2).toPrintable());

        System.out.println(o.hashCode());
        System.out.println("call hashCode Object:" + ClassLayout.parseInstance(o).toPrintable()); //00000101 偏向锁
        synchronized (o){
            System.out.println("locked Object:" + ClassLayout.parseInstance(o).toPrintable());
            System.out.println(o.hashCode());
        }

        synchronized (o2){
            System.out.println(Thread.currentThread().getId());
            System.out.println("locked Object2:" + ClassLayout.parseInstance(o2).toPrintable());
        }

        User a = new User();
        System.out.println("new User:" + ClassLayout.parseInstance(a).toPrintable());
        a.setUserName("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("赋值 User:" + ClassLayout.parseInstance(a).toPrintable());

    }
}
