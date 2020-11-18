package fc;

import java.util.PriorityQueue;

public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueueTest obj = new PriorityQueueTest();
        int[] nums = {5,5,8,1,4,7,1,3,8,4};
        int k=1;
        for (int i=0;i<k;i++){
            obj.addNum(nums[i]);
        }
        for (int i=0;i<nums.length-k+1;i++){
            System.out.println(obj.findMedian());
            if(i<nums.length-k){
                obj.removeNum(nums[i]);
                obj.addNum(nums[i+k]);
            }
        }

    }

    static PriorityQueue<Integer> min = new PriorityQueue<>();//优先队列，存的权值大的，个数少的
    static PriorityQueue<Integer> max = new PriorityQueue<>((o1,o2)->Integer.compare(o2,o1));//优先队列，存的权值小的，个数多的
    public void addNum(int num) {
        max.add(num);
        min.add(max.remove());
        if (min.size() > max.size()) max.add(min.remove());
    }

    public double findMedian() {
        if (max.size()==min.size()) return (max.peek()+min.peek()) / 2.0;
        else return max.peek();
    }
    public void removeNum(int num) {
        if (min.isEmpty()) {
            max.remove(num);
            return;
        }
        int temp = min.peek();
        if (num<temp){
            max.remove(num);
            if (min.size() > max.size()) max.add(min.remove());
        } else {
            if (min.size()<max.size()) min.add(max.remove());
            min.remove(num);
        }
    }
}
