package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 */
public class ArrayPermute {
    public static void main(String[] args) {

        int[] nums = {1,2,3};
        List<List<Integer>> resultList = new ArrayList<>();
        for (int i = 0;i<nums.length;i++){
            List<Integer> list = new ArrayList<>();
            list.add(nums[i]);
            fun(resultList,list,nums);
        }

        System.out.println(resultList);
    }

    public static void fun(List<List<Integer>> resultList, List<Integer> list,int[] nums) {
        if (list.size() == nums.length){
            resultList.add(list);
            return;
        }
        for (int i = 0;i<nums.length;i++){
            if(!list.contains(nums[i])) {
                List<Integer> newList = new ArrayList<>();
                newList.addAll(list);
                newList.add(nums[i]);
                fun(resultList,newList,nums);
            }
        }
    }
}
