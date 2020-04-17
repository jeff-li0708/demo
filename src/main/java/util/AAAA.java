package util;

import java.util.concurrent.ConcurrentHashMap;

public class AAAA {
    private static ConcurrentHashMap<String,String> map = new ConcurrentHashMap();
    public static String getStr(String str) {
        String s = map.get(str);
        if (s == null) {
            map.put(str,new String(str+System.currentTimeMillis()));
            s = map.get(str);
            System.out.println(1111);
        }
        return s;
    }
}
