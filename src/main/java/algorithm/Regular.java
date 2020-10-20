package algorithm;


/**
 * 正则匹配
 * 给定一个字符串 (s) 和一个字符模式 (p)。实现支持 '.' 和 '*' 的正则表达式匹配。
 * '.' 匹配任意单个字符。
 * '*' 匹配零个或多个前面的元素。
 * 说明:
 *
 * s 可能为空，且只包含从 a-z 的小写字母。
 * p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
 * Created by liangl on 2019/3/28.
 */
public class Regular {

    public static void main(String[] args) {

    }

    public static boolean isMatch(String s, String p) {
        if(p.isEmpty()) return s.isEmpty();
        boolean firstMatch =  !s.isEmpty() && (s.charAt(0) == p.charAt(0)|| p.charAt(0) == '.');
        if (p.length() >= 2 && p.charAt(1) == '*') {
            return isMatch(s,p.substring(2)) || (firstMatch && isMatch(s.substring(1),p));
        } else {
            return isMatch(s.substring(1),p.substring(1));
        }
    }
}
