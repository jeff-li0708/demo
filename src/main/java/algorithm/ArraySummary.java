package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    //小于等于N的最大递增数，如1234
    public int monotoneIncreasingDigits(int N) {
        int i = 1;
        int res = N;
        while(i <= res/10) {
            int n = res / i % 100; // 每次取两个位
            i *= 10;
            if(n/10 > n%10) // 比较的高一位大于底一位
                res = res / i * i - 1; //例如1332 循环第一次变为1330-1=1329 第二次变为1300-1=1299
        }
        return res;
    }
    // 买卖股票的最佳时机II
    public int maxProfit(int[] prices) {
        int res=0;
        int n=prices.length;
        for(int i=1;i<n;i++){
            res+=Math.max(0,prices[i]-prices[i-1]);
        }
        return res;
    }
    public static void main(String[] args) {
        int[] nums = new int[]{6,7};
        int[] nums2=new int[]{6,0,4};
        ArraySummary obj = new ArraySummary();
//        int[] ints = obj.maxMerge(nums, nums2);
//        Arrays.stream(ints).forEach(System.out::println);
        obj.monotoneIncreasingDigits(382);


    }

}
