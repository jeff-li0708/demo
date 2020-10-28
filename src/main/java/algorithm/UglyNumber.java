package algorithm;

public class UglyNumber {
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        int i = new UglyNumber().nthUglyNumber(9);
        System.out.println(i);
    }
    public int nthUglyNumber(int n) {
        int[] dp = new int[n];
        dp[0]=1;
        int i=0,j=0,k=0;
        for (int p = 1;p<n;p++){
            int temp = Math.min(dp[i]*2,Math.min(dp[j]*3,dp[k]*5));
            dp[p]=temp;
            if (temp==dp[i]*2)i++;
            if (temp==dp[j]*3)j++;
            if (temp==dp[k]*5)k++;
        }
        return dp[n-1];
    }
}
