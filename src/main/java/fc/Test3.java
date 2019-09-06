package fc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liangl on 2019/3/25.
 */
public class Test3 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            list.add(i);
        }
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            Integer a = iterator.next();
            iterator.remove();
            System.out.println(a);
        }
        for (int i = 0; i< list.size(); i++) {
            if (i == 5) {
                list.remove(i);
            } else {
                System.out.println(list.get(i));
            }
        }
    }
}
