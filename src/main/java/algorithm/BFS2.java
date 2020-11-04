package algorithm;

import java.util.LinkedList;
import java.util.Queue;

public class BFS2 {
    public static void main(String[] args) {
        TreeNodeSummary.TreeNode root = null;
        //BFS
        Queue<TreeNodeSummary.TreeNode> queue = new LinkedList();
        queue.offer(root);
        while(!queue.isEmpty()) {
            int n = queue.size();
            TreeNodeSummary.TreeNode last = null;
            for (int i = 1; i <= n; ++i) {
                TreeNodeSummary.TreeNode f = queue.poll();////弹出队头的元素
                if (f.left != null) queue.offer(f.left);
                if (f.right != null) queue.offer(f.right);

                last = f;
            }

        }
    }


}
