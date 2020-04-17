package fc;


import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by liangl on 2019/3/25.
 */
public class Test5 {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        Integer[] arr = new Integer[1000000];
        Arrays.parallelSetAll(arr,i->ThreadLocalRandom.current().nextInt(10000));

        Integer[] arr2 = new Integer[1000000];
        Arrays.parallelSetAll(arr2,i->ThreadLocalRandom.current().nextInt(10000));

//        Arrays.stream(arr).limit(10).forEach(i->System.out.print(i+" "));
//        System.out.println();
//
//        Arrays.stream(arr2).limit(10).forEach(i->System.out.print(i+" "));
//        System.out.println();

        long startTime = System.currentTimeMillis();
        Arrays.parallelSort(arr);
        long endTime = System.currentTimeMillis();
        Arrays.sort(arr2);
        long endTime2 = System.currentTimeMillis();
        System.out.println("time1="+String.valueOf(endTime-startTime)+" time2="+ String.valueOf(endTime2-endTime));
    }
}
