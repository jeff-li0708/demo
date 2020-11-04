package algorithm;

import java.util.ArrayList;
import java.util.List;

public class DP8 {
    public static void main(String[] args) {
        int n = 5;
//        int[][] requests = {{0,1},{1,0},{0,1},{1,2},{2,0},{3,4}}; //5
//        int[][] requests = {{0,0},{1,2},{2,1}}; //3
//        int[][] requests = {{0,3},{3,1},{1,2},{2,0}};//4
//        int[][] requests = {{1,2},{1,2},{2,2},{0,2},{2,1},{1,1},{1,2}};//3
        int[][] requests = {{0,1},{1,2},{2,3},{3,4},{4,2}};//5
        int i = new DP8().maximumRequests(n, requests);
        System.out.println(i);
    }
    int res = 0;
    public int maximumRequests(int n, int[][] requests) {
        List<Integer>[] list = new ArrayList[n];//临时空间，为了存储指向的集合
        int[] in = new int[n];//节点i的入度
        int[] out = new int[n];//节点i的初度
        for (int i = 0;i < n; i++) {
            list[i]=new ArrayList<>();
        }

        //初始数据
        for (int[] arr : requests) {
            if (arr[1] == arr[0]) {
                res++;//指向自己的环
            }
            else {
                list[arr[0]].add(arr[1]);
                in[arr[1]]++;
                out[arr[0]]++;
            }
        }

        boolean[] flag = new boolean[n];
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0;i< n;i++){
           if (out[i]>0 && in[i] >0) {
               list2.clear();
               list2.add(i);
               flag[i] = true;
               boolean re = true;

           }
        }

        return res;
    }

    public int dfs(List<Integer>[] listArr, List<Integer> list,int[] in,int[] out,boolean[] flag) {
        int key = list.get(list.size()-1);

        for (Integer a : listArr[key]) {
            if (flag[a]){ //a已在list中，从a开始存在环
                listArr[key].remove(a);
                in[a]--;
                res++;
                return a;
            }
            list.add(a);
            flag[a] = true;
            int dfs = dfs(listArr, list, in, out, flag);
            if (a != dfs) { //递归删除到形成环的节点
                list.remove(a);
                flag[a] = false;
                listArr[key].remove(a);
                out[key]--;
                in[a]--;
                res++;
                return dfs;
            } else {
                list.remove(a);
                flag[a] = false;
                listArr[key].remove(a);
                out[key]--;
                in[a]--;
                res++;
                continue;
            }
        }
        return 0;
    }
}
