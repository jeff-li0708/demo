package algorithm;

/**
 * 判断整数是否为回文
 * Created by liangl on 2019/3/28.
 */
public class CheckPlalindrome {
    public static void main(String[] args) {

    }

    public static boolean isPalindrome(int x) {
        String s = Integer.toString(x);
        int i = 0,j = s.length()-1;
        while (i<j){
            char a = s.charAt(i),b = s.charAt(j);
            if (a == b) {
                i++;j--;
            } else {
                return false;
            }
        }
        return true;
    }
}
