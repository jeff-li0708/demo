package algorithm;

/**
 * 字符串转整数
 * Created by liangl on 2019/3/28.
 */
public class StringToInteger {
    public static void main(String[] args) {
        System.out.println(-Integer.MIN_VALUE);
        System.out.println(myAtoi("2147483648"));
    }
    public static Integer myAtoi(String str) {
        int radix = 10;
        if (str == null || str.length() == 0) {
            return 0;
        }
        str = str.trim();
        int result = 0;
        boolean negative = false;
        int i = 0, len = str.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        if (len > 0) {
            char firstChar = str.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+') return 0;

                if (len == 1) return 0;
                i++;
            } else if (firstChar > '9') {
                return 0;
            }
            multmin = limit / radix;
            while (i < len) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    break;
                }
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(str.charAt(i++),radix);
                if (result < multmin) {
                    return negative ? limit : Integer.MAX_VALUE;
                }
                result *= radix;
                if (result < limit + digit) {//-2147483640
                    result = Integer.MIN_VALUE;
                } else {
                    result -= digit;
                }
            }
        }
        return negative ? result : -(result == Integer.MIN_VALUE ? -Integer.MAX_VALUE : result);
    }
}
