package algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * Created by liangl on 2019/3/18.
 */
public class LongestSubstring {
    public static void main(String[] args) {
        String str = "abcabcdefg";
        System.out.println(lengthOfLongestSubstring(str));
    }
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals(s)) return 0;
        Map<Character,Integer> map = new HashMap<>();
        int maxLength = 0;
        int temp = 0;
        for (int i = 0;i<s.length(); i++) {
            char c = s.charAt(i);
            if (map.get(c) == null) {
                temp++;
                map.put(c,i);
            } else {
                i = map.get(c);
                maxLength = maxLength >= temp ? maxLength : temp;
                temp = 0;
                map.clear();
            }
        }
        maxLength = maxLength >= temp ? maxLength : temp;
        return maxLength;
    }
}
