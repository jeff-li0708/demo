package fc;

import java.util.*;

/**
 * Created by liangl on 2019/3/25.
 */
public class Test3 {
    public static void main(String[] args) {
        flag:
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                System.out.println(i+","+j);
                if (j==2) continue flag;

            }
        }
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i< 10; i++) {
//            list.add(i);
//        }
//        for (Integer a:list){
//            System.out.println(a);
//            if (a>7) list.add(3);
//        }
//        Iterator<Integer> iterator = list.iterator();
//        while (iterator.hasNext()){
//            Integer a = iterator.next();
//            iterator.remove();
//            System.out.println(a);
//        }
//        for (int i = 0; i< list.size(); i++) {
//            if (i == 5) {
//                list.remove(i);
//            } else {
//                System.out.println(list.get(i));
//            }
//        }
//        Map<String,Integer> map = new HashMap<>();
//        Iterator<Map.Entry<String, Integer>> iterator1 = map.entrySet().iterator();
//        while (iterator1.hasNext()){
//            if (iterator1.next().getValue()<1){
//                iterator1.remove();
//            }
//
//        }
    }
}
