package algorithm;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 在数组中找出和为target的两个数
 * Created by liangl on 2019/3/18.
 */
public class ArrayTwoSum {

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5};
        int target = 11;
        System.out.println(JSON.toJSONString(twoSum(a,target)));
    }

    /**
     * 暴力查找-时间复杂度n的平方 空间复杂度1
     * @param nums
     * @param target
     * @return
     */
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

    /**
     * 方法2
     * Hash表一遍遍历 时间复杂度n,空间复杂度1
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
