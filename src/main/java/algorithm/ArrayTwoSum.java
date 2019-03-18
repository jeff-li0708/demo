package algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 在数组中找出和为target的两个数
 * Created by liangl on 2019/3/18.
 */
public class ArrayTwoSum {

    public static void main(String[] args) {
        int[] arr = {3,2,10,8};
        int target = 11;
        System.out.println(JSON.toJSONString(twoSum(arr,target)));
    }

    public static int[] twoSum(int[] nums, int target) {
        int len = nums.length;
        int result[] = new int[2];
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] =  i;
                    result[1] =  j;
                    return result;
                }
            }
        }
        return null;
    }
}
