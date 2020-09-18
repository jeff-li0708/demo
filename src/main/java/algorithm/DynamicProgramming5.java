package algorithm;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 在本问题中，有根树指满足以下条件的有向图。该树只有一个根节点，所有其他节点都是该根节点的后继。每一个节点只有一个父节点，除了根节点没有父节点。
 * 输入一个有向图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
 *
 * 结果图是一个以边组成的二维数组。 每一个边 的元素是一对 [u, v]，用以表示有向图中连接顶点 u 和顶点 v 的边，其中 u 是 v 的一个父节点。
 *
 * 返回一条能删除的边，使得剩下的图是有N个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。
 *
 * 示例 1:
 *
 * 输入: [[1,2], [1,3], [2,3]]
 * 输出: [2,3]
 * 解释: 给定的有向图如下:
 *   1
 *  / \
 * v   v
 * 2-->3
 *
 */
public class DynamicProgramming5 {
    public static void main(String[] args) {
//        int[][] deges = {{1,2}, {1,3}, {2,3}};
        int[][] deges = {{1,2}, {2,3}, {3,4}, {4,1}, {1,5}};
//        int[][] deges = {{2,1},{3,1},{4,2},{1,4}};
//        int[][] deges = {{4,2},{1,5},{5,2},{5,3},{2,4}};
//        int[][] deges = {{2,1},{3,1},{4,2},{1,4}};
        int[] res = new DynamicProgramming5().findRedundantDirectedConnection(deges);
        System.out.println(JSON.toJSONString(res));
    }
    /**
     *在一棵树中，边的数量比节点的数量少 1。如果一棵树有 N个节点，则这棵树有 N-1条边。这道题中的图在树的基础上多了一条附加的边，因此边的数量也是 N。
     *树中的每个节点都有一个父节点，除了根节点没有父节点。在多了一条附加的边之后，可能有以下两种情况：
     *
     *      附加的边指向根节点，则包括根节点在内的每个节点都有一个父节点，此时图中一定有环路；
     *      附加的边指向非根节点，则恰好有一个节点（即被附加的边指向的节点）有两个父节点，此时图中可能有环路也可能没有环路。
     */
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int len = edges.length;
        Node nodes[] = new Node[len+1];//储存节点
        int a[] = new int[len+1];//储存入度(前驱个数)
        List<Integer> list[] = new ArrayList[len+1];//临时空间，为了存储指向的集合
        for (int i = 0;i < len+1; i++) {
            nodes[i] = new Node(i);
            list[i]=new ArrayList<Integer>();
        }
        //初始数据
        for (int[] arr : edges) {
            list[arr[0]].add(arr[1]);
            nodes[arr[0]].setnext(list[arr[0]]);
            a[arr[1]]++; //入度加1
        }

        Stack<Node> s1 = new Stack<Node>();
        int res = -1;
        for(int i = 1;i <= len;i++){
            if(a[i]==0) {s1.add(nodes[i]);} //将入度为0的节点入栈
            if (a[i] == 2) {res = i;System.out.println(i);}
        }

        List<Integer> result = new ArrayList<>();
        while(!s1.isEmpty() && result.size() < len){
            Node n1 = s1.pop();//抛出输出
            result.add(n1.value);
            List<Integer> next =n1.next;
            for(int i=0;i<next.size();i++){
                a[next.get(i)]--; //入度减一
                if(a[next.get(i)]==0) {//如果入度为0,入栈
                    s1.add(nodes[next.get(i)]);
                }
            }
        }
        if (result.size() != len) {
            System.out.println("有环");
            //查找环返回最后一条边
//            for (int i = 1;i< len+1;i++){
//                int[] cycleLastNode = findCycleLastNode(i, i, nodes);
//                if(cycleLastNode != null) return cycleLastNode;
//            }

            List<Integer> cycleList = findCycleLastNodeByWhile(nodes);
            if (cycleList != null) {
                if (cycleList.contains(res)){
                    for(int i = len-1;i>=0;i--) {
                        if (cycleList.contains(edges[i][0]) && edges[i][1] == res) return edges[i];
                    }
                } else {
                    for(int i = len-1;i>=0;i--) {
                        if (cycleList.contains(edges[i][0]) && cycleList.contains(edges[i][1])) return edges[i];
                    }
                }
            }
//            int[] cycleLastNode = (int[]) cycleLastNodeByWhile;
//            if(cycleLastNode != null) return cycleLastNode;

        }
        for (int i = len -1;i>= 0; i--) {
            if(res == edges[i][1]) return edges[i];
        }
        return null;
    }

    /**
     * 查找环，并返回最后一条边
     * @return
     */
    public int[] findCycleLastNode(int start,int current, Node[] nodes) {
        List<Integer> nextList = nodes[current].next;
        if (nextList == null) return null;
        for (Integer a : nextList) {
            if (a == start) return new int[]{current,start};
            else{
                int[] cycleLastNode = findCycleLastNode(start, a, nodes);
                if(cycleLastNode != null) return cycleLastNode;
            }
        }
        return null;
    }

    public List<Integer> findCycleLastNodeByWhile(Node[] nodes) {
        for (int i = 1;i< nodes.length; i++) {
            int start = i;
            List<Integer> nextList = nodes[i].next;

            Stack<Integer> stack = new Stack<>();
            for (int j = nextList.size() -1;j>= 0; j--){ //入栈
                stack.push(nextList.get(j));
            }
            List<Integer> hasDealList = new ArrayList<>();
            hasDealList.add(i);
            while (!stack.empty()){
                Integer node = stack.pop();
                if (hasDealList.contains(node)) {
                    stack.clear();
                    break;
                }
                hasDealList.add(node);
                if (node == start) return hasDealList;
                else{
                    List<Integer> next = nodes[node].next;
                    for (int k = next.size() -1;k>= 0; k--){ //入栈
                        stack.push(next.get(k));
                    }
                }
            }


        }
        return null;
    }
    static class Node {
        int value;
        List<Integer> next;

        public Node(int value) {
            this.value=value;
            next = new ArrayList<Integer>();
        }
        public void setnext(List<Integer>list) {
            this.next=list;
        }
    }
}
