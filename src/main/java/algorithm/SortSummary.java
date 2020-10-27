package algorithm;

/**
 * 数组排序
 * Created by liangl on 2019/2/19.
 */
public class SortSummary {

    /****冒泡**********/
    public static int[] bubbling(int[] arr) {
        int len = arr.length;
        if (len <= 1) {return arr;}
        for (int i = 0; i < len -1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }

    /**快排***/
    public void quickSort(int[] nums,int start,int end){
        if (start>=end)return;
        int key = nums[start];
        int i = start,j = end;
        while(i<j){
            while (nums[j] >= key && i<j) j--;
            if(i<j){ //i、j元素交换
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
            }
            while (nums[i] <= key && i<j) i++;
            if(i<j){ //i、j元素交换
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
            }
        }
        quickSort(nums,start,i-1);
        quickSort(nums,i+1,end);
    }

    public static void main(String[] args) {
        int[] arr = {12,3,56,44,76,32,4,8,88,66};
        for (int a:arr) System.out.print(a+",");
        System.out.println();
//        arr = bubbling(arr);
        new SortSummary().quickSort(arr,0,arr.length -1);
        for (int a:arr) System.out.print(a + ",");
        String s = "30";

    }

}
