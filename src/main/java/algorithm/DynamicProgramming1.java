package algorithm;

import java.util.Stack;

/**
 * 动态规划-背包问题
 *
 * Created by liangl on 2019/3/11.
 */
public class DynamicProgramming1 {

    public static void main(String[] args) {
        int Weight[] = {0,1,2,2,6,5,4,7,2};  //物品的重量
        int Value[] = {0,7,6,3,5,4,6,20,10};  //物品的价值
        int limitW = 10; //限定重量
        int num = 8;//物品个数
        Stack<int[]> stack = new Stack<>();
        int[][] table = new int[num + 1][limitW+1];     //存放表示到第i个元素为止，在限制总重量为limitW的情况下我们所能选择到的最优解
        stack.push(table[0]);
        boolean Frist_Flag = true;
        for (int i = 1;i <= num;i++) {
            for (int j = 1;j <=limitW;j++) {

                if (Frist_Flag == true) {
                    if (Weight[1] <= j) {
                        table[i][j] = Value[1];
                        Frist_Flag = false;
                    }
                } else {
                    if (Weight[i] > j) {
                        table[i][j] = table[i-1][j];
                    } else {
                        table[i][j] = Math.max(table[i-1][j-Weight[i]]+Value[i],table[i-1][j]);
                    }
                }
            }
        }
        for(int[] row:table){
            for (int a : row) {
                System.out.print(a + (a > 9 ? "  ":"   "));
            }
            System.out.println();
        }
    }



}
