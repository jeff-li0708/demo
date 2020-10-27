package algorithm;

import java.util.*;

/**
 * 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一个字母只会出现在其中的一个片段。返回一个表示每个字符串片段的长度的列表。
 * 示例 1：
 *
 * 输入：S = "ababcbacadefegdehijhklij"
 * 输出：[9,7,8]
 * 解释：
 * 划分结果为 "ababcbaca", "defegde", "hijhklij"。
 * 每个字母最多出现在一个片段中。
 * 像 "ababcbacadefegde", "hijhklij" 的划分是错误的，因为划分的片段数较少。
 *
 */
public class DynamicProgramming8 {

    public static void main(String[] args) {
//        String s = "ababcbacadefegdehijhklij";
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        queue.offer(2);
        System.out.println(queue.poll());
        String s = "abcdefgch";
        List<Integer> list = new DynamicProgramming8().partitionLabels(s);
        list.stream().forEach(System.out::println);
        list.add(null);
        list.add(null);
        String b = list.toString();
        System.out.println(b);
        b=b.substring(1, b.length()-1);
        String[] split = b.split(",");
        for (String s1 : split) {
            System.out.println(s1);
        }
    }


    public List<Integer> partitionLabels(String S) {
        List<Integer> res = new ArrayList<>();
        int[] ext = new int[26];
        for (int i=0;i<S.length();i++) ext[S.charAt(i)-'a'] = i; //记录字符出现的最后位置
        for (int i= 0;i<S.length();i++){
            int end = ext[S.charAt(i)-'a'];
            int j = i+1;
            while(j<end) {
                end = Math.max(end,ext[S.charAt(j++)-'a']);
            }
            int len = end - i + 1;
            res.add(len);
            i=end;
        }
        return res;
    }

}
