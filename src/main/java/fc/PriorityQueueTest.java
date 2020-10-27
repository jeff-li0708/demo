package fc;

import java.util.PriorityQueue;

public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueue<Integer> min;
        PriorityQueue<Integer> max;
        min = new PriorityQueue<>();
        max = new PriorityQueue<>((a,b) -> {return  b - a ;});
        for (int i = 1;i<=10;i++){
            max.add(i);
            min.add(max.remove());
            if (min.size() > max.size()) max.add(min.remove());
        }
        System.out.println(max.peek());
        System.out.println(min.peek());
    }
}
