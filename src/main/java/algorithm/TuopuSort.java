package algorithm;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 拓扑排序
 */
public class TuopuSort {
    public static void main(String[] args) {
        int numCourses = 6;
        int[][] prerequisites = {{1,0},{3,1},{3,2}};
        findOrderFromClassCchedule(numCourses,prerequisites);
    }


    /**
     * 课程表问题：
     * 现在你总共有 n 门课需要选，记为 0 到 n-1。
     * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
     * 给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。
     * 可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组
     *
     * 输入: 4, [[1,0],[2,0],[3,1],[3,2]]
     * 输出: [0,1,2,3] or [0,2,1,3]
     * 解释: 总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
     *      因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
     * @return
     */
    public static int[] findOrderFromClassCchedule(int numCourses,int[][] prerequisites) {
        Node nodes[] = new Node[numCourses];//储存节点
        int a[] = new int[numCourses];//储存入度(前驱个数)
        List<Integer> list[] = new ArrayList[numCourses];//临时空间，为了存储指向的集合
        for (int i = 0;i < numCourses; i++) {
            nodes[i] = new Node(i);
            list[i]=new ArrayList<Integer>();
        }
        //初始数据
        for (int[] arr : prerequisites) {
            list[arr[1]].add(arr[0]);
            nodes[arr[1]].setnext(list[arr[1]]);
            a[arr[0]]++;
        }

        Stack<Node> s1 = new Stack<Node>();
        for(int i=0;i < numCourses;i++){
            if(a[i]==0) {s1.add(nodes[i]);} //将入度为0的节点入栈
        }
        List<Integer> result = new ArrayList<>();
        while(!s1.isEmpty() && result.size() < numCourses){
            Node n1 = s1.pop();//抛出输出

            result.add(n1.value);
            List<Integer> next =n1.next;
            for(int i=0;i<next.size();i++){
                a[next.get(i)]--;//入度减一
                if(a[next.get(i)]==0)//如果入度为0
                {
                    s1.add(nodes[next.get(i)]);
                }
            }
        }
        if (result.size() == numCourses) {
            //找到拓扑排序序列
            int[] aaa = result.stream().mapToInt(Integer::valueOf).toArray();
            System.out.print(JSON.toJSONString(aaa));
            return aaa;
        }
        else {
            //图中有环
            System.out.print("图中有环");
            return new int[]{};
        }
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
