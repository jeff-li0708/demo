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

    /**
     * 堆排序
     * 利用大顶堆（堆顶元素最大），每次将堆顶元素取出
     * @param nums
     */
    public void heapSort(int[] nums){
        int n = nums.length;
        //构建大顶堆
        for (int i=0;i<n;i++){
            int k = i;
            int key = nums[i];
            while (k > 0) {
                int parent = (k - 1) >>> 1;
                int e = nums[parent];
                if (key<=e)
                    break;
                nums[k] = e;
                k = parent;
            }
            nums[k] = key;
        }
        int end = n-1;
        //循环删除堆顶元素冒泡到数组后面，并调整堆
        while (end>0){
            int key = nums[end];
            nums[end]=nums[0];//将堆顶元素放数组最后
            int k=0;
            int size = end;
            int half = size >>> 1;        // loop while a non-leaf
            while (k < half) {
                int child = (k << 1) + 1; // assume left child is least
                int c = nums[child];
                int right = child + 1;
                if (right < size && c < nums[right])
                    c = nums[child = right];
                if (key >= c)
                    break;
                nums[k] = c;
                k = child;
            }
            nums[k] = key;
            end--;
        }

    }



    public static void main(String[] args) {
        int[] arr = {12,3,56,44,76,32,4,8,88,66};
        for (int a:arr) System.out.print(a+",");
        System.out.println();
//        arr = bubbling(arr);
//        new SortSummary().quickSort(arr,0,arr.length -1);
        new SortSummary().heapSort(arr);
        for (int a:arr) System.out.print(a + ",");
        String s = "30";

    }

}
