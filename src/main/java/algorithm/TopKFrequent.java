package algorithm;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 *
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *
 */
public class TopKFrequent {
    public static void main(String[] args) {
        int[] nums = {5,3,1,1,1,3,73,1}; int k = 2;
        int[] proNumKey = new int[k];
        int[] proNumValue = new int[k];
        Map<Integer,Integer> map = new HashMap<>();

        for (int a: nums){
            Integer count = map.get(Integer.valueOf(a)) == null ? 1: map.get(Integer.valueOf(a))+1;
            map.put(Integer.valueOf(a),count);
        }
        for (Integer key : map.keySet()) {
            Integer integer = map.get(key);
            int min = Integer.MAX_VALUE;
            int minNumIdx = 0;
            for (int i = 0;i< k;i++){
                if (proNumValue[i]<min){
                    minNumIdx = i;
                    min = proNumValue[i];
                }
            }
            if (integer > proNumValue[minNumIdx]){
                proNumKey[minNumIdx] = key;
                proNumValue[minNumIdx] = integer;
            }
        }

        System.out.println(JSON.toJSONString(proNumKey));
    }
}
