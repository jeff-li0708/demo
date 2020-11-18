package algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于双向链表+HashMap
 * 1.初始队列，头尾都指向值为空的节点
 * 2.向队列添加节点，两种情况1-key存在，2-key不存在
 * case1 替换value,将节点移到队尾（最后一个有值的节点）
 * case2 创建个新的Node放入map,超过了初始的容量，删除队头（第一个有值的节点）以及删除对应的map元素，将新节点放入队尾
 * 3.获取节点元素，每次获得后都将节点放队尾
 *
 * 第二种方法就是继承LinkedHashMap,设置LinkedHashMap的属性accessOrder为true并重写removeEldestEntry方法的逻辑
 * Created by liangl on 2019/3/15.
 */
public class LRUCache {

    class Node {
        Node pre;
        Node next;
        Integer key;
        Integer val;
        Node(Integer k, Integer v) {
            key = k;
            val = v;
        }
    }

    Map<Integer, Node> map = new HashMap();

    // The head (eldest) of the doubly linked list.
    Node head;
    // The tail (youngest) of the doubly linked list.
    Node tail;
    int cap;

    //初始队列，头尾都指向一个
    public LRUCache(int capacity) {
        cap = capacity;
        head = new Node(null, null);
        tail = new Node(null, null);
        head.next = tail;
        tail.pre = head;
    }
    public int get(int key) {
        Node n = map.get(key);
        if(n!=null) {
            n.pre.next = n.next;
            n.next.pre = n.pre;
            appendTail(n);
            return n.val;
        }
        return -1;
    }

    public void set(int key, int value) {
        Node n = map.get(key);
        // existed
        if(n!=null) {
            n.val = value;
            map.put(key, n);
            n.pre.next = n.next;//将节点删除并放到队尾
            n.next.pre = n.pre;
            appendTail(n);
            return;
        }
        // else {
        if(map.size() == cap) { //
            Node tmp = head.next;
            head.next = head.next.next;
            head.next.pre = head;
            map.remove(tmp.key);
        }
        n = new Node(key, value);
        // youngest node append taill
        appendTail(n);
        map.put(key, n);
    }
    private void appendTail(Node n) {
        n.next = tail;
        n.pre = tail.pre;
        tail.pre.next = n;
        tail.pre = n;
    }

}
