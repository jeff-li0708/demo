package algorithm;

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * Created by liangl on 2019/3/19.
 */
public class MaxSubArray {
    public static void main(String[] args) {
        int[] arr = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray(arr));
    }

    /**
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {
        int len = nums.length;
        int max = nums[0],currSum = 0;
        for (int i = 0; i < len; i++) {
            currSum = nums[i];
            max = max > currSum ? max : currSum;
            for (int j = i + 1; j < len; j++) {
                currSum += nums[j];
                max = max > currSum ? max : currSum;
            }
            currSum = 0;
        }
        return max;
    }
}
