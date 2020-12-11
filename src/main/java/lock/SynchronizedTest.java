package lock;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedTest {
    static Object object = null;

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("�߳�0");
        Thread.sleep(6000);
        object = new Object();
        System.out.print("����->" + "�߳����ƣ�" + Thread.currentThread().getName() + "\n" + ClassLayout.parseInstance(object).toPrintable());
        sysn("�߳�1");
        Thread.sleep(1000);
        System.out.print("�߳�1ռ��,δ�������̳߳��Ի�ȡ��->" + "�߳����ƣ�" + Thread.currentThread().getName() + "\n" + ClassLayout.parseInstance(object).toPrintable());
        sysn("�߳�2");
        Thread.sleep(1000);
        System.out.print("�߳�1ռ�ã��߳�2���Ի�ȡ����->" + "�߳����ƣ�" + Thread.currentThread().getName() + "\n" + ClassLayout.parseInstance(object).toPrintable());
        Thread.sleep(10000);
        System.out.println("�ͷ���->" + "�߳����ƣ�" + Thread.currentThread().getName() + "\n" + ClassLayout.parseInstance(object).toPrintable());
        //sysn("�߳�3");
    }

    private static void sysn(String threadName) {
        new Thread(() -> {
            Thread.currentThread().setName(threadName);
            synchronized (object) {
                System.out.print(threadName + "ռ����->" + "�߳����ƣ�" + Thread.currentThread().getName() + "\n" + ClassLayout.parseInstance(object).toPrintable());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(threadName + "�ͷź�->" + "�߳����ƣ�" + Thread.currentThread().getName() + "\n" + ClassLayout.parseInstance(object).toPrintable());
        }).start();
    }

}
