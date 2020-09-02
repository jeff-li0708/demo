package algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 你有 4 张写有 1 到 9 数字的牌。你需要判断是否能通过 *，/，+，-，(，) 的运算得到 24。
 * 例如：
 * 输入: [4, 1, 8, 7]
 * 输出: True
 * 解释: (8-4) * (7-1) = 24
 *
 * 例子8, 1, 6, 6|
 *
 * 解题思路
 * 取两个数运算后的结果与剩余的数继续运算
 */
public class JudgePoint24 {
    public static void main(String[] args) {
        int[] arr = {3, 3, 8, 8};
        double[] arr2 = new double[arr.length];
        for (int i = 0;i< arr.length;i++){
            arr2[i]=arr[i];
        }
        System.out.println(fun(arr2));
    }


    public static boolean fun(double[] nums){
        if (nums.length == 2) {
            if (nums[0]+nums[1] == 24 || nums[0]*nums[1] == 24 || nums[0]-nums[1] == 24 || nums[0]-nums[1] == -24){
                return true;
            } else if (nums[0]*nums[1] != 0 && (nums[0]/nums[1] == 24 || nums[1]/nums[0] == 24 || nums[1]/nums[0] == 23.99999999999999 || nums[0]/nums[1] == 23.99999999999999)) {
                return true;
            } else {
                return false;
            }
        } else {
            for (int i = 0;i<nums.length;i++){
                for (int j = i+1; j< nums.length; j++) {
                    double[] newArr = new double[nums.length-1];
                    int idx = 0;
                    for (int k = 0;k<nums.length; k++) {
                        if (k != i && k != j){
                            newArr[idx++] = nums[k];
                        }
                    }

                    newArr[idx] = nums[i]+nums[j];
                    System.out.println(JSON.toJSONString(newArr));
                    if(fun(newArr)) return true;
                    newArr[idx] = nums[i]*nums[j];
                    System.out.println(JSON.toJSONString(newArr));
                    if(fun(newArr)) return true;
                    newArr[idx] = nums[i]-nums[j];
                    System.out.println(JSON.toJSONString(newArr));
                    if(fun(newArr)) return true;
                    newArr[idx] = nums[j]-nums[i];
                    System.out.println(JSON.toJSONString(newArr));
                    if(fun(newArr)) return true;
                    if (nums[i] == 0 || nums[j] == 0){
                        newArr[idx] = 0;
                        System.out.println(JSON.toJSONString(newArr));
                        if(fun(newArr)) return true;
                    } else {
                        newArr[idx] = nums[i]/nums[j];
                        System.out.println(JSON.toJSONString(newArr));
                        if(fun(newArr)) return true;
                        newArr[idx] = nums[j]/nums[i];
                        System.out.println(JSON.toJSONString(newArr));
                        if(fun(newArr)) return true;
                    }
                }
            }
            return false;
        }
    }
}
