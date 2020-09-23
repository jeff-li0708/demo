package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNodeSummary {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
     }
    public List<TreeNode> searchTreeNode(TreeNode root){
         if (root == null) return null;
         if (root != null) {
             list.add(root);
         }
         searchTreeNode(root.left);
            searchTreeNode(root.right);
         return list;
    }


    /**
     * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
     *
     * 例如：
     *
     * 输入: 原始二叉搜索树:
     *               5
     *             /   \
     *            2     13
     *
     * 输出: 转换为累加树:
     *              18
     *             /   \
     *           20     13
     * 解题思路：中序遍历左右反过来，右->中->左
     */
    int num = 0;
    public void convertBST(TreeNode root){
        if (root == null) return;
        if (root.right != null) { //右
            convertBST(root.right);
        }
        root.val += num;//中
        num = root.val;
        if (root.left != null) { //左
            convertBST(root.left);
        }
    }

    /**
     * 二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。实现一个方法，把二叉搜索树转换为单向链表，要求依然符合二叉搜索树的性质，转换操作应是原址的，也就是在原始的二叉搜索树上直接修改。
     * 返回转换后的单向链表的头节点。
     * 注意：本题相对原题稍作改动
     *
     * 示例：
     *
     * 输入： [4,2,5,1,3,null,6,0]
     * 输出： [0,null,1,null,2,null,3,null,4,null,5,null,6]
     */
    static TreeNode last = null,first = null;
    public TreeNode convertBiNode(TreeNode root) {
        if (root == null) return root;
        convertBiNode(root.left);//左

        if (root != null) {//中
            if (last != null) {
                last.right = root;
                root.left = null;
            } else {
                first = root;
            }
            last = root;
        }
        convertBiNode(root.right);//右
        return first;
    }
    int max = 0;
    public int maxSumBST(TreeNode root) {
        if (root == null) return 0;
        if (root.left != null ) {
            if (root.left.val >= root.val) {
                num = 0;
                return max;
            }
            maxSumBST(root.left);//左
        }

        if (root.right != null) {
            if (root.right.val <= root.val){
                num = 0;
                return maxSumBST(root.right);
            }
            maxSumBST(root.right);//右
        }

        num += root.val;
        max = Math.max(max,num);

        return max;
    }


    static Map<TreeNode,Integer> map = new HashMap<>();
    static List<TreeNode> list = new ArrayList<>();
    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        TreeNode l1 = new TreeNode(2);
        root.left = l1;
        l1.left = new TreeNode(1);
        l1.right = new TreeNode(3);
        root.right = new TreeNode(4);
//         List<TreeNode> treeNodes = new TreeNodeSummary().searchTreeNode(root);
//         System.out.println(treeNodes);

        int treeNode = new TreeNodeSummary().maxSumBST(root);
        System.out.println(treeNode);

    }
}
