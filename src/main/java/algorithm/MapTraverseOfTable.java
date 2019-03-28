package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 1------5------4
 *\     |\    /|
 * \    | \  / |
 *  \   |   6  |
 *   \  |  / \ |
 *    \ | /   \|
 *      2 -----3
 * 图的遍历- 邻接表
 *
 * Created by liangl on 2019/3/28.
 */
public class MapTraverseOfTable {
    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {}

        public Node(int _val,List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    };

    public static void main(String[] args) {
        Node clone1 = new Node(1, new ArrayList<Node>());
        Node clone2 = new Node(2, new ArrayList<Node>());
        Node clone3 = new Node(3, new ArrayList<Node>());
        Node clone4 = new Node(4, new ArrayList<Node>());
        Node clone5 = new Node(5, new ArrayList<Node>());
        Node clone6 = new Node(6, new ArrayList<Node>());
        clone1.neighbors.add(clone2);
        clone1.neighbors.add(clone5);

        clone2.neighbors.add(clone1);
        clone2.neighbors.add(clone3);
        clone2.neighbors.add(clone4);
        clone2.neighbors.add(clone5);

        clone3.neighbors.add(clone2);
        clone3.neighbors.add(clone4);
        clone3.neighbors.add(clone6);

        clone4.neighbors.add(clone2);
        clone4.neighbors.add(clone3);
        clone4.neighbors.add(clone5);

        clone5.neighbors.add(clone1);
        clone5.neighbors.add(clone2);
        clone5.neighbors.add(clone4);
        clone5.neighbors.add(clone6);

        clone6.neighbors.add(clone5);
        clone6.neighbors.add(clone3);
        dfs(clone1);
        //bfs(clone1);
    }

    static Map<Integer, Node> map = new HashMap<>();

    /**
     * 深度优先
     * @param node
     * @return
     */
    public static void dfs(Node node) {
        if (node == null) return;
        map.put(node.val, node);
        System.out.println(node.val);
        //邻居节点
        for (Node neighbor :node.neighbors) {
            //如果邻居节点在map中没有
            if (!map.containsKey(neighbor.val)) {
                dfs(neighbor);
            }
        }
    }

    /**
     * 广度优先
     * @param node
     * @return
     */
    public static void bfs(Node node) {
        if (node == null) return;
        map.put(node.val,node);
        System.out.println(node.val);
        //邻居节点
        while (true) {
            Node firstInser = null;
            for (Node neighbor :node.neighbors) {

                //如果邻居节点在map中没有
                if (!map.containsKey(neighbor.val)) {
                    map.put(neighbor.val,neighbor);
                    System.out.println(neighbor.val);
                    firstInser = firstInser == null ? neighbor : firstInser;
                }
            }
            if (firstInser == null) {
                break;
            } else {
                node = firstInser;
            }
        }

    }
}
