package fc;

import java.util.*;

/**
 * Created by liangl on 2019/2/15.
 */
public class CodeSEO {
    public static void main(String[] args) {

//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime waterStart = LocalDateTime.of(now.getYear(), now.getMonth(),now.getDayOfMonth(),3,0);
//        LocalDateTime waterEndTime =LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 4, 0);
//
//        System.out.println(now);
//        System.out.println(waterStart);
//        System.out.println(waterEndTime);
        Map map = new HashMap();
        List status = null;
        map.put("status", Optional.ofNullable(status).orElse(new ArrayList<String>()));
        System.out.println(map);
    }
}
