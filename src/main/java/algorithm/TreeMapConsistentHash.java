package algorithm;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性Hash算法
 * 基于TreMap
 * Created by liangl on 2019/3/27.
 */
public class TreeMapConsistentHash {
    private static TreeMap<Long,String> map = new TreeMap<>();

    private static Integer VIRTUAL_NODE_SIZE = 10; //虚拟节点个数


    /**
     * 增加节点
     * @param key
     * @param value
     */
    public static void add(Long key, String value) {
        for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
            map.put(hash("virtual"+ key + i), value);
        }
        map.put(key,value);
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值
     * @param key
     * @return
     */
    public static long hash(String key) {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for (int i = 0; i < key.length(); i++)
            hash = (hash ^ key.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    /**
     * 获取最近的节点
     * @param key
     * @return
     */
    public static String getFirstNode(String key) {
        Long hash = hash(key);
        SortedMap<Long,String> last = map.tailMap(hash);
        if (last.isEmpty()) {
            return map.firstEntry().getValue();
        } else {
            return last.get(last.firstKey());
        }
    }
    public static void init(String[] ips) {
        for (String ip : ips) {
            add(hash(ip), ip);
        }
    }

    public static void main(String[] args) {
        String[] ips = {"10.16.11.1","10.16.11.2","10.16.11.4","10.16.11.3"};
        init(ips);
        for (int i = 0; i < 10;i++)
            System.out.println(getFirstNode("192.168.16.1"+i));

    }


}
