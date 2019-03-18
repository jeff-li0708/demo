package algorithm;

/**
 * 求两个有序数组的中位数
 *
 * Created by liangl on 2019/3/18.
 */
public class TwoArrayMedian {
    public static void main(String[] args) {
        int[] arr1 = {1,2,3,4,5};
        int[] arr2 = {3,4,5,6,8,9,12};
        System.out.println(findMedianSortedArrays(arr1,arr2));
    }
    //合并两数组后求中位数
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length,len2=nums2.length;
        if (len1 == 0 && len2 == 0) return 0.0;
        int[] arr3 = new int[len1+len2];
        int i = 0,j = 0;
        while (i < len1 && j < len2) {
            if (nums1[i] <= nums2[j]) {
                arr3[i+j] = nums1[i];
                i++;
            } else {
                arr3[i+j] = nums2[j];
                j++;
            }
        }
        while (i < len1){
            arr3[i+j] = nums1[i];
            i++;
        }
        while (j < len2) {
            arr3[i+j] = nums2[j];
            j++;
        }
        if ((len1+len2)%2 == 1) {
            return arr3[(len1+len2)/2];
        } else {
            return (arr3[(len1+len2)/2-1] + arr3[(len1+len2)/2])/2.0;
        }

    }
}
