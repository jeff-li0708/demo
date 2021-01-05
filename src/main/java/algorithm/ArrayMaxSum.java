package algorithm;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 给定一个数组，可以对其中K个数取反，求数组的最大和
 * 如[-3,1,4] k=2 最大和为6
 * Created by liangl on 2019/3/15.
 */
public class ArrayMaxSum {

    public static void main(String[] args) {


        int A[] = {-8,3,-5,-3,-5,-2};
        int K = 6;
        System.out.println(largestSumAfterKNegations(A,K));
    }

    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b) -> { return b-a;});
        for (int a:stones) queue.add(a);
        while(queue.size()>1){
            int max = queue.remove();
            int max2 = queue.remove();
            if (max>max2) queue.add(max-max2);
        }
        return queue.size()==1?queue.remove():0;
    }
    public static int largestSumAfterKNegations(int[] A, int K) {
        Arrays.sort(A);
        int total = 0;
        for(int i = 0,len=A.length; i<len; i++) {
            if(A[i] < 0 && (--K) >= 0) total += -A[i];
            else if (K%2 == 1) {
                if (i>0 && A[i] > -A[i-1]) {
                    total +=  A[i]+ 2*A[i-1];
                } else {
                    total += -A[i];
                }
                K = 0;
            } else {
                total += A[i];
            }
        }
        return total;
    }
}
