package algorithm;

import bean.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liangl on 2019/3/11.
 */
public class DynamicPlan2 {
    public static void main(String[] args) {
        List<Product> list = new ArrayList<>();
        for (int i = 0;i < 20; i++) {
            Product product = new Product();
            product.setWeight((int)(Math.random() * 20)+1);
            product.setValue((int)(Math.random() * 50)+1);
            list.add(product);
        }
        System.out.println(list.stream().map(a->a.getWeight()).collect(Collectors.toList()));
        System.out.println(list.stream().map(a->a.getValue()).collect(Collectors.toList()));

        int limitW = 20; //限定重量
        int num = list.size();//物品个数
        int[][] table = new int[num + 1][limitW+1];     //存放表示到第i个元素为止，在限制总重量为limitW的情况下我们所能选择到的最优解
        String[][] strArr = new String[num + 1][limitW+1]; //表示到第i个元素为止，在限制总重量为limitW的情况下我们所能选择到的最优解选取的物品下标
        boolean Frist_Flag = true;
        for (int i = 1;i <= num;i++) {
            for (int j = 1;j <=limitW;j++) {
                Product product = list.get(i-1);
                if (Frist_Flag == true) {
                    if (product.getWeight() <= j) {
                        table[i][j] = product.getValue();
                        Frist_Flag = false;
                        strArr[i][j] = String.valueOf(i-1);
                    }
                } else {
                    if (product.getWeight() > j) {
                        table[i][j] = table[i-1][j];
                        strArr[i][j] = strArr[i-1][j] == null ? "" : strArr[i-1][j];
                    } else {
                        table[i][j] = Math.max(table[i-1][j-product.getWeight()]+product.getValue(),table[i-1][j]);
                        if (table[i][j] == table[i-1][j]) {
                            strArr[i][j] = strArr[i-1][j] == null ? "" : strArr[i-1][j];
                        } else {
                            strArr[i][j] = (strArr[i-1][j-product.getWeight()] == null ? "" : strArr[i-1][j-product.getWeight()]) + "," + (i-1);
                        }
                    }
                }
            }
        }
        System.out.println(table[num][limitW]);
        System.out.println(strArr[num][limitW]);
//        for(int[] row : table) {
//            for (int a : row) {
//                System.out.print(a + (a > 9 ? a > 99 ? " " : "  ":"   "));
//            }
//            System.out.println("");
//        }

//        for(String[] row : strArr) {
//            for (String a : row) {
//                System.out.print(a);
//            }
//            System.out.println("");
//        }

    }
}
