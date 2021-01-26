package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 最小生成树（MST）之Kruskal克鲁斯卡尔算法
 */
public class MapKruskal {
    public static void main(String[] args) {
        MapKruskal obj = new MapKruskal();
        int[][] points = {{0,0},{2,2},{3,10},{5,2},{7,0}};//地图上的点
        int res = obj.minCostConnectPoints(points);
        System.out.println(res);
    }

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        List<Edge> edges = initEdge(points);//构造边
        Collections.sort(edges,(o1, o2)->o1.dis-o2.dis);//根据距离升序
        int ans = 0, num = 1;
        UnionFind uf = new UnionFind(n);

        for (Edge edge : edges) {//对所有的边，加入一条边，同时连接2个点，然后加上距离
            int len = edge.dis, x = edge.x, y = edge.y;
            if (uf.union(x, y)) {
                ans += len;
                num++;
                if (num == n) {
                    break;
                }
            }
        }
        return ans;
    }
    class UnionFind {
        int[] parents;//节点i对应的集合
        int[] size;//节点i对应的集合的元素个数
        int n;//节点个数

        public UnionFind(int n) {
            this.n = n;
            this.size = new int[n];
            Arrays.fill(this.size, 1);//每个集合的元素个数初始都是一个
            this.parents = new int[n];
            for (int i = 0; i < n; i++) {//初始时每个节点单独一个集合
                this.parents[i] = i;
            }
        }
        public int find(int x) {
            if(x!=parents[x]) parents[x] = find(parents[x]);
            return parents[x];
        }

        public boolean union(int x, int y) {
            int fx = find(x), fy = find(y);
            if (fx == fy) { //已经在一个集合了直接返回
                return false;
            }
            //将小的集合合并到大的集合中
            if (size[fx] > size[fy]) {
                size[fx] += size[fy];
                parents[fy] = fx;
            }else{
                size[fy] += size[fx];
                parents[fx] = fy;
            }
            return true;
        }
    }
    /**
     * 根据顶点构造边
     * @param points
     * @return
     */
    public List<Edge> initEdge(int[][] points){
        List<Edge> edges = new ArrayList<>();
        int n = points.length;
        for (int i=0;i<n;i++){
            for (int j=i+1;j<n;j++){
                edges.add(new Edge(points,i,j));
            }
        }
        return edges;
    }
    /**
     * 表示x与y之间的边
     */
    class Edge {
        int x;
        int y;
        int dis;

        Edge(int[][] points, int i, int j) {
            x = i;
            y = j;
            dis = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
        }
    }
}
