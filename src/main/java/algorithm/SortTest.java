package algorithm;

/**
 * 数组排序
 * Created by liangl on 2019/2/19.
 */
public class SortTest {

    public static void main(String[] args) {
        int[] arr = {12,3,56,44,76,32,4,8,88,66};
        for (int a:arr) System.out.print(a+",");
        System.out.println();
        arr = bubbling(arr);
        for (int a:arr) System.out.print(a + ",");
    }

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

}
