package algorithm;

import java.util.*;

/**
 * 四数之和
 */
public class FourSum {
    public static void main(String[] args) {
        int[]  nums = {-3,-2,-1,0,0,1,2,3};
        int target = 1;
        Map<String,Boolean> replaceMap = new HashMap<>();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0;i<nums.length;i++){
            for (int j=i+1;j< nums.length;j++){
                int[] newArr = new int[nums.length-2];
                for (int k = 0,idx = 0;k < nums.length; k++) {
                    if (k != i && k != j){
                        newArr[idx++] = nums[k];
                    }
                }
                int a = nums[i]+nums[j];
                List<List<Integer>> lists = twoSum(newArr, target - a);
                if (lists != null){
                    for (List<Integer> integers:lists) {
                        integers.add(new Integer(nums[i]));
                        integers.add(new Integer(nums[j]));
                        Collections.sort(integers);
                        String key = ""+integers.get(0)+integers.get(1)+integers.get(2)+integers.get(3);
                        if (!replaceMap.containsKey(key)) {
                            result.add(integers);
                            replaceMap.put(key,true);
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    public static List<List<Integer>> twoSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0;i<nums.length;i++){
            for (int j=i+1;j< nums.length;j++){
                if (nums[i]+nums[j]==target){
                    List<Integer> list = new ArrayList<>();
                    list.add(new Integer(nums[i]));
                    list.add(new Integer(nums[j]));
                    result.add(list);
                }
            }
        }
        return result;
    }
}
