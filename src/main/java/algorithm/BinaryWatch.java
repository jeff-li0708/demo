package algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 二进制手表顶部有 4 个 LED 代表小时（0-11），底部的 6 个 LED 代表分钟（0-59）。
 * 回溯算法找到所有的子集
 * Created by liangl on 2019/3/29.
 */
public class BinaryWatch {
    public static void main(String[] args) {
        List<String> result1 = new BinaryWatch().readBinaryWatch(3);
        for(String s : result1)
            System.out.println(s);
    }

    int[] nums = {480,240,120,60,32,16,8,4,2,1};
    List<Integer> temp = new ArrayList<>();
    List<String> ans = new ArrayList<>();
    HashSet<List<Integer>> set = new HashSet<>();

    public List<String> readBinaryWatch(int num) {

        dfs(nums,0,num);

        for(List list : set){
            int sum = 0;
            int flag = 0;
            for(int i = list.size()-1 ; i >= 0 ; i--){
                sum += Integer.valueOf(String.valueOf(list.get(i)));
                if(sum>=60 && Integer.valueOf(String.valueOf(list.get(i)))<=32)
                    flag = 1;

            }
            if(flag==1)
                continue;
            int hours = (sum / 60);
            if(hours>=12){
                continue;
            }
            int minute = sum - hours*60;
            String one = null;
            String two = null;
            if(minute<10){
                two = "0" + String.valueOf(minute);
            }else{
                two = String.valueOf(minute);
            }
            one = String.valueOf(hours);
            ans.add(one+":"+two);
        }
        return ans;
    }

    public void dfs(int[] nums , int index, int num){
        if(temp.size()==num){
            set.add(new ArrayList(temp));
        }else{
            for(int i = index ; i < nums.length ; i++){
                temp.add(nums[i]);
                dfs(nums,i+1,num);
                temp.remove(temp.size()-1);
            }
        }
    }

}
