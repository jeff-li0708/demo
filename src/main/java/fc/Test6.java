package fc;


import java.util.*;

public class Test6 {
    public static void main(String[] args) {
        String S = "lee";
        int i = new Test6().distinctSubseqII(S);
        System.out.println(i);
    }

    public int distinctSubseqII(String S) {
        int MOD = 1_000_000_007;
        int n = S.length();
        int[] dp = new int[n+1];
        int[] arr = new int[26];
        Arrays.fill(arr,-1);
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            int idx = S.charAt(i) - 'a';
            dp[i+1] = 2 * dp[i];
            if(arr[idx] >= 0) dp[i+1] -= dp[arr[idx]];
            dp[i+1] %= MOD;
            arr[idx] = i;
        }

        for (int a : dp) {
            System.out.print(a + (a > 9 ? "  ":"   "));
        }
        dp[n]--;
        if (dp[n] < 0) dp[n] += MOD;
        return dp[n];
    }



}
