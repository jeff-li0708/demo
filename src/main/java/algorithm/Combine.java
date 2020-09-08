package algorithm;

import java.util.ArrayList;
import java.util.List;

public class Combine {
    public static void main(String[] args) {
        int n = 2,k=1;
//        int countUp = 1;
//        int countFloor = 1;
//        for (int i = n,j = k;j>0;j--,i--){
//            countUp = countUp*i;
//            countFloor = countFloor*j;
//        }
//        int count = countUp/countFloor;
        List<List<Integer>> resultList = new ArrayList<>();
        if (k>=n){
            List<Integer> newList = new ArrayList<>();
            for (int i = 1;i<=n;i++){
                newList.add(i);
            }
            resultList.add(newList);
        }
        for (int i =1;i<=(n-k+1);i++){
            List<Integer> list = new ArrayList<>();
            list.add(i);
            List<Integer> fun = fun(resultList, list, n, k);
            if (fun != null) {
                resultList.add(fun);
            }
        }
        System.out.println(resultList);
    }
    public static List<Integer> fun(List<List<Integer>> resultList,List<Integer> list,int n,int k) {
        if (list.size() == k) return list;
        if (list.size() > k) return null;
        int lastIndex = list.size()-1;
        for (int i = list.get(lastIndex)+1; i <= n; i++){
            List<Integer> newList = new ArrayList<>();
            newList.addAll(list);
            newList.add(i);
            List<Integer> fun = fun(resultList, newList, n, k);
            if (fun != null) {
                resultList.add(fun);
            }
        }
        return null;
    }
}
