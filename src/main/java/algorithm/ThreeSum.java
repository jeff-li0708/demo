package algorithm;

import java.util.*;

/**
 * 求三个数之和为0
 * 例如{-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0}
 * 输出[[-5, 1, 4], [-4, 0, 4], [-4, 1, 3], [-2, -2, 4], [-2, 1, 1], [0, 0, 0]]
 */
public class ThreeSum {

    public static void main(String[] args) {
        List<List<Integer>> result = new ArrayList<>();
        //int[] nums = {-4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6};
        int[] nums = {-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0};
        Map<String,Boolean> replaseMap = new HashMap<>();
        Arrays.sort(nums);
        for (int i = 0,length=nums.length;i<length-2;i++){
            for (int j=length-1;j>i;j--) {
                int a = nums[i]+nums[j];
                if ( nums[j]<0){
                    break;
                }
                if (nums[i]>0) {
                    i = length;
                    break;
                }
                if (a < 0) {
                    for (int k = j-1; k>i;k--) {
                        if (a+nums[k]==0) {
                            String key = ""+nums[i]+nums[k]+nums[j];
                            if (!replaseMap.containsKey(key)) {
                                result.add(Arrays.asList(new Integer(nums[i]),new Integer(nums[k]),new Integer(+nums[j])));
                                replaseMap.put(key,true);
                            }
                            break;
                        } else if (a+nums[k] < 0) {
                            break;
                        }
                    }
                } else {
                    for (int k = i+1; k<j;k++) {
                        if (a+nums[k]==0) {
                            String key = ""+nums[i]+nums[k]+nums[j];
                            if (!replaseMap.containsKey(key)) {
                                result.add(Arrays.asList(new Integer(nums[i]),new Integer(nums[k]),new Integer(+nums[j])));
                                replaseMap.put(key,true);
                            }
                            break;
                        } else if (a+nums[k] > 0) {
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }
}
