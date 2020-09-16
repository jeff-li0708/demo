package algorithm;

/**
 * 474
 * 在计算机界中，我们总是追求用有限的资源获取最大的收益。
 *
 * 现在，假设你分别支配着 m 个 0 和 n 个 1。另外，还有一个仅包含 0 和 1 字符串的数组。
 *
 * 你的任务是使用给定的 m 个 0 和 n 个 1 ，找到能拼出存在于数组中的字符串的最大数量。每个 0 和 1 至多被使用一次。
 *
 * 注意:
 *
 * 给定 0 和 1 的数量都不会超过 100。
 * 给定字符串数组的长度不会超过 600。
 * 示例 1:
 *
 * 输入: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
 * 输出: 4
 *
 * 解释: 总共 4 个字符串可以通过 5 个 0 和 3 个 1 拼出，即 "10","0001","1","0" 。
 *
 */
public class DynamicPlan4 {
    public static void main(String[] args) {
        String[] strs = {"10","0001","111001","1","0"};
        int m = 4,n=3;
        findMaxForm(strs,m,n);
    }
    public static int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n+1];
        for (String s : strs) {
            int count0 = 0, count1 = 0;
            for (char ch : s.toCharArray()) {
                if (ch == '0') count0++;
                if (ch == '1') count1++;
            }
            for (int i = m; i >=count0; i--) {
                for (int j = n; j >= count1; j--) {
                    dp[i][j] = Math.max(dp[i][j], 1+dp[i-count0][j-count1]);
                }
            }
//            for(int[] row:dp){
//                for (int a : row) {
//                    System.out.print(a + (a > 9 ? "  ":"   "));
//                }
//                System.out.println();
//            }
//            System.out.println("------------------------------");
        }
        return dp[m][n];
    }
}
