package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 输入：n = 3
 * 输出：[
 *        "((()))",
 *        "(()())",
 *        "(())()",
 *        "()(())",
 *        "()()()"
 *      ]
 *
 */
public class GenerateParenthesis {
    public static void main(String[] args) {
        int n = 4;
        List<String> result = new ArrayList<>();
        fun(result, "(", n);
        System.out.println(result);
    }

    public static void fun(List<String> list, String s,int n) {
        int a = check(s,n);
        if (a != -1) {
            if (s.length() == n*2) {
                if (a == 0) {
                    list.add(s);
                }
            } else {
                fun(list,s+"(",n);
                fun(list,s+")",n);
            }
        }
    }
    public static int check(String s, int a) {
        int left = 0,right = 0;
        for (int i = 0;i<s.length();i++) {
            if (s.charAt(i) == '('){
                left++;
            } else {
                right++;
            }
            if (left-right < 0 || left > a || right > a) {
                return -1;
            }
        }
        return left-right;
    }
}
