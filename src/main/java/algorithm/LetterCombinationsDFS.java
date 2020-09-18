package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 随便输入一串手机键盘的数字，返回可能的字符串组合
 * 解答：回溯dfs
 * Created by liangl on 2019/4/1.
 */
public class LetterCombinationsDFS {
    public static void main(String[] args) {

        List<String> list = new LetterCombinationsDFS().letterCombinations("2375");
        for (String s : list)
            System.out.println(s);
    }
    List<Character> temp = new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        if (digits == null || "".equals(digits)) return new ArrayList<>();
        char[][] arr = {{'a','b','c','0'}, //2
                        {'d','e','f','0'}, //3
                        {'g','h','i','0'}, //4
                        {'j','k','l','0'}, //5
                        {'m','n','o','0'}, //6
                        {'p','q','r','s'}, //7
                        {'t','u','v','0'}, //8
                        {'w','x','y','z'}}; //9
        char[] num = digits.toCharArray();
        char[][] input = new char[digits.length()][4];

        int i = 0;
        for (char n : num) {
            input[i++] = arr[Integer.valueOf(Character.toString(n))-2];

        }
        List<String> ans=new ArrayList<>();
        dfs(input,0,ans);
        return ans;
    }
    private void dfs(char[][] arr,int index,List<String> ans) {
        if (arr.length == index) {
            ans.add(arrToStr(temp));
            return;
        } else {
            for (int i = 0; i < arr[index].length;i++){
                if (arr[index][i] != '0'){
                    temp.add(arr[index][i]);
                    dfs(arr,index+1,ans);
                    temp.remove(temp.size()-1);
                }
            }
        }
    }

    private String arrToStr(List<Character> temp){
        StringBuilder sb = new StringBuilder();
        for (Character c : temp) {
            sb.append(c);
        }
        return sb.toString();
    }
}
