package algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Z形变换
 * Created by liangl on 2019/3/21.
 */
public class StringConvert {
    public static void main(String[] args) {
        String s = "LEETCODEISHIRING";
        String s2 = "PAYPALISHIRING";

        System.out.println(convert("ABC",2));
    }

    public static String convert(String s, int numRows) {
        if (numRows == 1) return s;
        Map<Integer,StringBuilder> map = new HashMap<>();
        int len = s.length();
        boolean isZ = false;
        int index = numRows-2;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (i < numRows) {
                StringBuilder sb = new StringBuilder();
                map.put(i, sb.append(c));
            } else if (numRows == 2) {
                StringBuilder old = map.get(i%2);
                map.put(i%2,old.append(c));
            } else {
                if (isZ == false) {
                    if (index > 0) {
                        StringBuilder old = map.get(index);
                        map.put(index,old.append(c));
                        index--;
                        if (index == 0) {
                            isZ = true;
                            continue;
                        }
                    }
                }
                if (isZ) {
                    if (index < numRows) {
                        StringBuilder old = map.get(index);
                        map.put(index,old.append(c));
                        index++;
                        if (index == numRows) {
                            index = numRows-2;
                            isZ = false;
                        }
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            sb = sb.append(map.get(i) == null ? "" : map.get(i));
        }
        return sb.toString();
    }
}
