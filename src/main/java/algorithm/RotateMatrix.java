package algorithm;

/**
 * 将一个n*n的矩阵顺时针旋转90度
 */
public class RotateMatrix {
    public static void main(String[] args) {

        int[][] matrix = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}
        };
        int n = matrix.length;
        int[][] arr = new int[n][n];
        for (int i = 0;i < n;i++){
            for (int j = 0;j < n; j++){
                arr[j][n-i-1] = matrix[i][j];
            }
        }
        System.out.println(1);
    }
}
