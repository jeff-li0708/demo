package thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest {
    public static List<String> list = new ArrayList<>();

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<String> list2 = new ArrayList<>(50000);
                    long l = System.currentTimeMillis();
                    for(int i=0;i<50000;i++) {
                        list2.add(l+"-"+i);
                    }
                    list = list2;
                    System.out.println("--------------------------------------------------------------------------------------------------");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    for (String s:list) {
                        System.out.println(s);
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread2.start();
    }
}
