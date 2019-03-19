package algorithm;

/**
 * 最长回文子串
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * Created by liangl on 2019/3/19.
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        String s = "cbbd";
        System.out.println(longestPalindrome(s));
    }

    /**
     * 动态规划 （三个重要的元素最优子结构，边界，状态转移公式）
     * 我们创建一个二维数组，boolean[][]table,table[i][j]表示字符串第i到j是否为回文。那么边界值其实很清楚了，j-i=1的都为true。
     * 状态转换如何设定呢？当字符串i所在的字符等于字符串j所在的字符，并且它的内部(dp[i+1][j-1])为回文那么dp[i][j]为true。
     * 因为这样的规律，我们要保证判断dp[i][j]的时候dp[i+1][j-1]已经判断，所以我们遍历采用i降序j升序的嵌套遍历的方式
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) return s;
        int len = s.length();
        int left = 0,right=0;
        boolean[][] table = new boolean[len][len];
        for (int i = len - 2; i >= 0; i--) {
            for (int j = i+1; j < len; j++) {
                table[i][j] = s.charAt(i) == s.charAt(j) && (j-i<3||table[i+1][j-1]);//小于3是因为aba一定是回文
                if (table[i][j] && j-i > right-left) {
                    left = i;
                    right = j;
                }
            }
        }
        return s.substring(left,right+1);
    }
}
