package algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 在一个n*n的矩阵里走，从原点（0,0）开始走到终点（n-1,n-1），只能上下左右4个方向走，只能在给定的矩阵里走，求最短步数。n*n是01矩阵，0代表该格子没有障碍，为1表示有障碍物。
 * int mazeArr[maxn][maxn]; //表示的是01矩阵
 * int stepArr[4][2] = {{-1,0},{1,0},{0,-1},{0,1}}; //表示上下左右4个方向
 * int visit[maxn][maxn]; //表示该点是否被访问过，防止回溯，回溯很耗时。
 */
public class BFS {
    public static void main(String[] args) {
        System.out.println();
    }
    private static int bfs(){
        int n = 4;
        int mazeArr[][]={{0,0,0,0},{1,1,0,1},{0,0,0,1},{1,1,0,0}}; //表示的是01矩阵
        int[][] stepArr = {{-1,0},{1,0},{0,-1},{0,1}}; //表示上下左右4个方向
        int visit[][] = new int[n][n]; //表示该点是否被访问过，防止回溯，回溯很耗时。

        Node node = new Node(0,0,0);
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(node);
        while(!queue.isEmpty()){
            Node newNode = queue.poll();
            visit[newNode.x][newNode.y]=1;
            for(int i=0;i<4;i++){
                int x=newNode.x+stepArr[i][0];
                int y=newNode.y+stepArr[i][1];
                if(x==n-1&&y==n-1){
                    return newNode.step+1;
                }
                if(x>=0&&y>=0&&x<n&&y<n
                        &&visit[x][y]==0&&mazeArr[x][y]==0){
                    Node next = new Node(x,y,newNode.step+1);
                    queue.add(next);
                }
            }
        }
        return-1;
    }

    private static class Node{
        private int x;
        private int y;
        private int step;
        public Node(int x,int y,int step){
            super();
            this.x=x;
            this.y=y;
            this.step=step;
        }
    }
}
