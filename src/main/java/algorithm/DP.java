package algorithm;


public class DP {
    public static void main(String[] args) {
        int[] nums1 = {3, 4, 6, 5};
        int[] nums2 = {9, 1, 2, 5, 8, 3};
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
}
