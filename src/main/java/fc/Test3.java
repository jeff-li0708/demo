package fc;

import java.util.LinkedHashMap;

/**
 * Created by liangl on 2019/3/25.
 */
public class Test3 {
    public static void main(String[] args) {

        LinkedHashMap<Integer,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1,"张三");
        linkedHashMap.put(2,"李四");
        linkedHashMap.get(1);
        Integer cpuNum = Runtime.getRuntime().availableProcessors();
        System.out.println(cpuNum);
    }
}
