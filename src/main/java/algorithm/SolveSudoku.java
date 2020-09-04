package algorithm;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 编写一个程序，通过已填充的空格来解决数独问题。
 *
 * 一个数独的解法需遵循如下规则：
 *
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 * 空白格用 '.' 表示。
 *
 *
 *
 * 一个数独。
 * {'.','.','.','.','5','.','.','1','.'},
 * {'.','4','.','3','.','.','.','.','.'},
 * {'.','.','.','.','.','3','.','.','1'},
 * {'8','.','.','.','.','.','.','2','.'},
 * {'.','.','2','.','7','.','.','.','.'},
 * {'.','1','5','.','.','.','.','.','.'},
 * {'.','.','.','.','.','2','.','.','.'},
 * {'.','2','.','9','.','.','.','.','.'},
 * {'.','.','4','.','.','.','.','.','.'}};
 *
 *
 * 答案被标成红色。
 *
 * Note:
 *
 * 给定的数独序列只包含数字 1-9 和字符 '.' 。
 * 你可以假设给定的数独只有唯一解。
 * 给定数独永远是 9x9 形式的。

 */
public class SolveSudoku {
    public static void main(String[] args) {
        char[][] arr3 = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}};
        char[][] arr = {
                {'5','3','4','6','7','8','9','1','2'},
                {'6','7','2','1','9','5','3','4','8'},
                {'1','9','8','3','4','2','5','6','7'},
                {'8','5','9','7','6','1','4','2','3'},
                {'4','2','6','8','5','3','7','9','1'},
                {'7','1','3','9','2','4','8','5','6'},
                {'9','6','1','5','3','7','2','8','.'},
                {'2','8','7','4','1','9','6','3','5'},
                {'3','4','5','2','8','6','1','7','.'}};
        //printlArr(arr3);
        fun(arr);
    }

    public static void fun(char[][] board) {
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                if('.' == board[i][j]) {
                    //存放i行j列的数以及ij所在的子单元格
                    HashSet<String> colSet = new HashSet<>();
                    HashSet<String> rowSet = new HashSet<>();
                    HashSet<String> subSet = new HashSet<>();
                    boolean isGo = false;
                    for (int p = 0;p < 9; p++){
                        if (isGo = '.' != board[p][j] && !colSet.add(String.valueOf(board[p][j]))) break;
                        if (isGo = '.' != board[i][p] && !rowSet.add(String.valueOf(board[i][p]))) break;
                        if (p%3 == 0){
                            if(isGo = '.' != board[p/3+(i/3)*3][p%3+(j/3)*3] && !subSet.add(String.valueOf(board[p/3+(i/3)*3][p%3+(j/3)*3]))) break;
                        } else if (p%3 == 1) {
                            if(isGo = '.' == board[p/3+(i/3)*3][p%3+(j/3)*3] && !subSet.add(String.valueOf(board[p/3+(i/3)*3][p%3+(j/3)*3])))break;
                        } else if (p%3 == 2) {
                            if(isGo = '.' != board[p/3+(i/3)*3][p%3+(j/3)*3] && !subSet.add(String.valueOf(board[p/3+(i/3)*3][p%3+(j/3)*3]))) break;
                        }
                    }

                    for (int k = 1;k < 10;k++) {
                        if (!colSet.contains(String.valueOf(k)) && !rowSet.contains(String.valueOf(k)) && !subSet.contains(String.valueOf(k))) { //都不包含
                            char cNumber= (char) (k+'0');
                            board[i][j] = cNumber;
                            System.out.println("arr["+i+"]["+j+"]="+cNumber);
                            fun(board);
                            if (verify(board)) {
                                printlArr(board);
                            } else {
                                board[i][j] = '.';
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean verify(char[][] board) {
        for (int i = 0;i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ('.' == board[i][j]) return false;
            }
        }
        return true;
    }

    public static void printlArr(char[][] board) {
        for (int i = 0;i<9;i++){
            List<String> rowList = new ArrayList<>();
            for (int j = 0;j<9;j++) {
                rowList.add(String.valueOf(board[i][j]));
            }
            System.out.println(rowList);
        }
        System.out.println("------------------------------------------------------");
    }
}
