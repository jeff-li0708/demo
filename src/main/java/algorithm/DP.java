package algorithm;


import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

public class DP {
    public static void main(String[] args) {

        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-1<<29));
        System.out.println(Integer.toBinaryString(1<<29));
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i=0;i<10;i++){
            queue.addFirst(i);
        }
        int[] nums1 = {3, 4, 6, 5};
        int[] nums2 = {8,1,4,7,1,3,8,4};
        double[] i = new DP().medianSlidingWindow(nums2, 8);
        int k = 1;
        int a = new DP().maxNumber(nums1,nums2,k);
        System.out.println(a);
    }
    public int maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length,n = nums2.length;
        int[][][] dp = new int[k+1][m+1][n+1];
        for (int p = 1;p<=k;p++) {
            for (int i = 1;i <= m;i++){
                for (int j = 1;j<=n;j++){
                    if (p == 1) {
                        dp[p][i][i] = Math.max(Math.max(dp[p][i][j],dp[p][i][j-1]),dp[p][i-1][j]);
                    }
                }
            }
        }
        return dp[k][m][n];
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length-k+1];
        for(int i=0;i<res.length;i++){
            int[] temp = Arrays.copyOfRange(nums,i,i+k);
            int idx = quickSelect(temp,0,k-1);
            if ((k%2)==1){
                res[i]=temp[idx];
            } else {
                res[i]=(Long.valueOf(temp[idx])+Long.valueOf(temp[idx-1]))/2.0;
            }
        }
        return res;
    }
    public int quickSelect(int[] nums,int start,int end){
        if (start>=end) return end;
        int key = nums[start];
        int i = start,j=end;
        while(i<j){
            while(i<j&& key<=nums[j])j--;
            if (i<j) swap(nums,i,j);
            while(i<j&&key>=nums[i])i++;
            if (i<j) swap(nums,i,j);
        }
        if (i==nums.length/2) return i;//i刚好等于一半，则下标i是中位数
        else if (i<nums.length/2) return quickSelect(nums,i+1,end);//i<一半，中位数则在另外一半中
        else return quickSelect(nums,start,i-1);
    }
    public void swap(int[] nums,int i,int j){
        int temp = nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
}
