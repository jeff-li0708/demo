package algorithm;

import java.util.Arrays;

public class ArraySummary {

    //最大的由k个元素组成的子序列，且子序列中的元素顺序与原数组保持不变
    public int[] maxSubSeq(int[] nums,int k){
        int length = nums.length;
        int[] stack = new int[k];//用数组代替栈
        int top = -1;//栈顶下标
        int remain = length - k;//可移除的元素一个数
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            while (top >= 0 && stack[top] < num && remain > 0) {
                top--;
                remain--;
            }
            if (top < k - 1) {
                stack[++top] = num;
            } else {
                remain--;
            }
        }
        return stack;
    }

    //合并两数组，合并后的数组组成的数值最大
    public int[] maxMerge(int[] nums1,int[] nums2) {
        int n = nums1.length,m=nums2.length;
        int[] res = new int[m+n];
        int idx1=0,idx2=0;
        for (int i=0;i<n+m;i++){
            if (compare(nums1,idx1,nums2,idx2)>0){
                res[i]=nums1[idx1++];
            } else {
                res[i]=nums2[idx2++];
            }
        }
        return res;
    }

    //比较两数组
    public int compare(int[] nums1, int index1, int[] nums2, int index2) {
        int x = nums1.length, y = nums2.length;
        while (index1 < x && index2 < y) {
            int difference = nums1[index1] - nums2[index2];
            if (difference != 0) {
                return difference;
            }
            index1++;
            index2++;
        }
        return (x - index1) - (y - index2);
    }

    public static void main(String[] args) {
        int[] nums = new int[]{6,7};
        int[] nums2=new int[]{6,0,4};
        ArraySummary obj = new ArraySummary();
        int[] ints = obj.maxMerge(nums, nums2);
        Arrays.stream(ints).forEach(System.out::println);


    }
}
