package algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 有效括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * Created by liangl on 2019/4/2.
 */
public class StackTest {
    public static void main(String[] args) {
        System.out.println(new StackTest().isValid("([)]"));
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character,Character> map = new HashMap<>();
        map.put('(',')');
        map.put('{','}');
        map.put('[',']');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.size() == 0) {
                stack.push(c);
                continue;
            }
            char top = map.get(stack.peek()) == null ? '0':map.get(stack.peek());
            if (top == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.size() == 0;
    }
}
