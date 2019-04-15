package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换字符串的字符大小写,得到所有的子集
 * 回溯算法
 * Created by liangl on 2019/3/29.
 */
public class LetterCasePermutation {
    public static void main(String[] args) {
        List<String> result = new LetterCasePermutation().letterCasePermutation("abc");
        for (String s : result) {
            System.out.println(s);
        }
    }
    List<String> list = new ArrayList<>();

    public List<String> letterCasePermutation(String S) {
        List<String> ans=new ArrayList<>();
        dfs(S.toCharArray(),0,ans);
        return ans;
    }
    private void dfs(char[] S,int i,List<String> ans) {
        if(i==S.length){
            ans.add(new String(S));
            return;
        }
        dfs(S,i+1,ans);
        if(!Character.isLetter(S[i])) return; //不是字母直接返回
        S[i]^=1<<5;
        dfs(S,i+1,ans);
        S[i]^=1<<5;
    }





    private void dfs(String s,int index,String temp) {
        if (temp.length() == s.length()) {
            list.add(temp);
        } else {
            for (int i = index,len = s.length(); i < len; i++) {
                char c = s.charAt(i);
                if (c >= 'a' && c <= 'z') {
                    temp = temp + Character.toUpperCase(c);
                } else if (c >= 'A' && c <= 'Z') {
                    temp = temp + Character.toLowerCase(c);
                } else {
                    temp = temp + c;
                }
                dfs(s,index+1,temp);
                temp = temp.substring(0,temp.length()-1);

//                if (c >= 'a' && c <= 'z') {
//                    String S2 = s.substring(0,index) + Character.toUpperCase(c) + s.substring(index+1);
//                    dfs(S2,index+1,temp+Character.toUpperCase(c));
//                } else if (c >= 'A' && c <= 'Z'){
//                    String S2 = s.substring(0,index) + Character.toLowerCase(c) + s.substring(index+1);
//                    dfs(S2,index+1,temp+Character.toLowerCase(c));
//                } else {
//                    dfs(s,index+1,temp);
//                }
            }
        }
    }


}
