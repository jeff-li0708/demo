package algorithm;

public class SlidingWindow {
    public static void main(String[] args) {
        int[] nums = new int[]{2,3,1,2,4,3};

        SlidingWindow obj = new SlidingWindow();
        System.out.println(obj.minSubArrayLen(7, nums));
    }
    //209. 长度最小的子数组
    //找出该数组中满足其和 ≥ target 的长度最小的 连续 子数组，并返回其长度
    public int minSubArrayLen(int target, int[] nums) {
        int n=nums.length;
        int left=0,right=0,sum=0;
        int res=Integer.MAX_VALUE;
        while(right<n) {
            sum += nums[right];
            while (sum >= target) {
                res = Math.min(res, right - left + 1);
                sum -= nums[left++];
            }
            right++;
        }
        return res==Integer.MAX_VALUE?0:res;
    }
}
