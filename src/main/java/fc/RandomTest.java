package fc;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by liangl on 2018/11/30.
 */
public class RandomTest {

    public static void main(String[] args) {
        final Random r = new Random();
        final ThreadLocalRandom r2 = ThreadLocalRandom.current();
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    r.nextInt(1000);
                    r2.nextInt(1000);
                }
            });
            thread.start();
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
