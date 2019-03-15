package fc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by liangl on 2018/12/5.
 */
public class DateFormatTest {

    static ThreadLocal<DateFormat> safeSdf = new ThreadLocal<DateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static void main(String[] args) throws InterruptedException {
        for (int i= 1;i < 50000;i++){
            final int j = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Thread.sleep(500L);
                        System.out.println(safeSdf.get().parse("2018-12-05 12:00:00") + ","+j);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"test"+j);
            thread.start();
        }
        Thread.sleep(1000L);
        System.out.println(11);
    }

}
