package algorithm;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * Created by liangl on 2019/3/22.
 */
public class IntegerReverse {
    public static void main(String[] args) {
        int a = -3210;
        System.out.println(reverse(a));
    }

    public static int reverse(int x) {
        String str = String.valueOf(x);
        str = x < 0 ? str.substring(1,str.length()) : str;
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        Long lon = Long.parseLong(sb.toString());
        lon = x < 0 ? -lon:lon;
        if (lon.compareTo(Long.valueOf(Integer.MAX_VALUE)) == 1 || lon.compareTo(Long.valueOf(Integer.MIN_VALUE)) == -1) return 0;
        return x < 0 ? -Integer.parseInt(sb.toString()):Integer.parseInt(sb.toString());
    }
}
