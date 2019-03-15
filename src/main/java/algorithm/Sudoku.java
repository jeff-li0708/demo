package algorithm;

import java.util.HashSet;

/**
 * 数独
 * 判断一个9×9的数独是否有效
 * 1.同一行不能有相同的数字
 * 2.同一列不能有相同数字
 * 3.每一个小的3×3的格子不能有相同数字
 * Created by liangl on 2019/3/15.
 */
public class Sudoku {

    public static void main(String[] args) {
        String[][] arr = {
                { "5","3",".",".","7",".",".",".","."},
                { "6",".",".","1","9","5",".",".","."},
                { ".","9","5",".",".",".",".","6","."},
                { "8",".",".",".","6",".",".",".","3"},
                { "4",".",".","8",".","3",".",".","1"},
                { "7",".",".",".","2",".",".",".","6"},
                { ".","6",".",".",".",".","2","8","."},
                { ".",".",".","4","1","9",".",".","5"},
                { ".",".",".",".","8",".",".","7","9"}};

        String[][] arr2 = {
                {"5", "3", ".", ".", "7", ".", ".", ".", "."},
                {"6", ".", ".", "1", "9", "5", ".", ".", "."},
                {".", "9", "8", ".", ".", ".", ".", "6", "."},
                {"8", ".", ".", ".", "6", ".", ".", ".", "3"},
                {"4", ".", ".", "8", ".", "3", ".", ".", "1"},
                {"7", ".", ".", ".", "2", ".", ".", ".", "6"},
                {".", "6", ".", ".", ".", ".", "2", "8", "."},
                {".", ".", ".", "4", "1", "9", ".", ".", "5"},
                {".", ".", ".", ".", "8", ".", ".", "7", "9"}};
        //arr-false arr2-true
        System.out.println(verify(arr));
    }


    public static boolean verify(String[][] arr) {
        HashSet<String> colSet = new HashSet<>();
        HashSet<String> rowSet = new HashSet<>();
        HashSet<String> subSet = new HashSet<>();
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++){
                if (!".".equals(arr[i][j]) && !colSet.add(arr[i][j])) return false;
                if (!".".equals(arr[j][i]) && !rowSet.add(arr[j][i])) return false;
                if (i%3 == 0)
                    if (!".".equals(arr[j/3+(i/3)*3][j%3]) && !subSet.add(arr[j/3+(i/3)*3][j%3])) return false;
                else if (i%3 == 1)
                    if (!".".equals(arr[j/3+(i/3)*3][j%3+3]) && !subSet.add(arr[j/3+(i/3)*3][j%3+3])) return false;
                else if (i%3 == 2)
                    if (!".".equals(arr[j/3+(i/3)*3][j%3+6]) && !subSet.add(arr[j/3+(i/3)*3][j%3+6])) return false;
            }
            colSet.clear();
            rowSet.clear();
            subSet.clear();
        }
        return true;
    }

}
