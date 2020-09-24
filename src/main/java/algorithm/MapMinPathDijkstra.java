package algorithm;

import java.util.Arrays;

/**
 * 求最短路径之迪杰斯特拉算法
 */
public class MapMinPathDijkstra {
    public static void main(String[] args) {
        //领接列表表示有向图最后一个数表示权值
        int[][] edges = {{0,1,2},{0,3,5},{3,1,2},{3,2,6},{1,2,2},{2,4,12},{3,4,2},{2,5,2},{5,6,10},{4,6,1},{4,2,1},{6,4,1}};
        int n = 7,start = 0,target = 6; //依次是节点个数、起点、终点
        int dijkstra = new MapMinPathDijkstra().dijkstra(edges, n, start, target);
        System.out.println(dijkstra);
    }

    public int dijkstra(int[][] edges,int n, int start, int target){
        int INF = Integer.MAX_VALUE/2;

        int[] dest = new int[n]; //dest[i]表示从起点到i的距离(路径的权值和)
        Arrays.fill(dest, INF); //默认一个很大的值
        dest[start] = 0;//起点默认为0

        boolean[] flag = new boolean[n]; //记录节点i是否已被确认

        int minNode = start; //当前权值最小的节点
        int confirmNum = 0; //已被确认的节点数
        while(confirmNum < n) {

            //从未确认的节点中找出权值最小的节点
            int minWeight = INF;
            for (int i = 0; i < n; i++) {
                if (!flag[i] && dest[i] < minWeight) {
                    minWeight = dest[i];
                    minNode = i;
                }
            }

            //计算与节点minNode相连的节点的权值
            for(int i = 0;i<edges.length;i++){
                if(edges[i][0] == minNode) {
                    //原来起点到节点edges[i][1]的权值与start到i的权值加上i到节点edges[i][1]的权值取最小值;
                    dest[edges[i][1]] = Math.min(dest[edges[i][1]],dest[edges[i][0]] + edges[i][2]);
                }
            }

            flag[minNode] = true; //将权值最小的节点记录为已被确认
            confirmNum++;
        }
        return dest[target];
    }
}
