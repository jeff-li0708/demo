package algorithm;

import java.util.*;

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
     * 累加二叉树
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

    /**
     * 根据一棵树的中序遍历与后序遍历构造二叉树。
     *
     * 注意:
     * 你可以假设树中没有重复的元素。
     *
     * 例如，给出
     *
     * 中序遍历 inorder = [9,3,15,20,7]
     * 后序遍历 postorder = [9,15,7,20,3]
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTreeDFS(inorder,postorder,0,inorder.length-1,postorder.length-1);
    }

    /**
     * 在中序的某一段数据中找出根
     * @param inorder
     * @param postorder
     * @param start
     * @param end
     * @return
     */
    public TreeNode buildTreeDFS(int[] inorder, int[] postorder,int start,int end,int postEnd){
        for (int i = postEnd;i >= 0;i--) {
            for (int j = start;j<= end;j++) {
                if (postorder[i] == inorder[j]) {
                    TreeNode root = new TreeNode(inorder[j]);
                    root.left = buildTreeDFS(inorder,postorder, start,j-1,i-1);
                    root.right = buildTreeDFS(inorder,postorder, j+1,end,i-1);
                    return  root;
                }
            }
        }
        return null;
    }

    /**
     * 根据一棵树的中序遍历与先序遍历构造二叉树。（剑指offer 07）
     * @param preorder
     * @param inorder
     * @param inStart
     * @param inEnd
     * @param preStart
     * @return
     */
    public TreeNode recursive(int[] preorder,int[] inorder,int inStart,int inEnd, int preStart){
        if(preStart > preorder.length - 1 || inStart > inEnd) return null;

        TreeNode root = new TreeNode(preorder[preStart]);
        int inIdx = inEnd;
        for (int i = inStart;i<= inEnd;i++) {
            if (inorder[i] == preorder[preStart]) {
                inIdx = i;
                break;
            }
        }
        root.left=recursive(preorder, inorder, inStart,inIdx-1,preStart+1);
        root.right=recursive(preorder, inorder, inIdx+1, inEnd,preStart+(inIdx-inStart)+1);
        return root;
    }

    /***先序 42135*/
    public void preorderFind(TreeNode root){
        if (root == null) return;
        intList.add(root.val);
        preorderFind(root.left);
        preorderFind((root.right));
    }
    /***先序(迭代) 42135*/
    public void preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            intList.add(node.val);
            if (node.right != null){
                stack.push(node.right);
            }
            if (node.left != null){
                stack.push(node.left);
            }
        }
    }

    /***中序12345**/
    public void inorderFind(TreeNode root){
        if (root == null) return;
        inorderFind(root.left);
        intList.add(root.val);
        inorderFind(root.right);
    }
    /***中序（迭代）13254**/
    public void iterationInOrderFind(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                TreeNode node = stack.pop();
                intList.add(node.val);
                root = node.right;
            }
        }
    }

    /***后序13254**/
    public void postorderFind(TreeNode root){
        if (root == null) return;
        postorderFind(root.left);
        postorderFind(root.right);
        intList.add(root.val);
    }

    /***后序（迭代）13254**/
    public void iterationFind(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            if(node.left != null) stack.push(node.left);//和传统先序遍历不一样，先将左结点入栈
            if(node.right != null) stack.push(node.right);//后将右结点入栈
            intList.add(0,node.val);  //逆序添加结点值
        }
    }
    /***反过来的后序（右->左->根）53124**/
    public void repostorderFind(TreeNode root){
        if (root == null) return;
        repostorderFind(root.right);
        repostorderFind(root.left);
        intList.add(root.val);
    }

    /**
     * 查找二叉搜索树第 k 个最小的元素
     * 思路：递归中序遍历，找到第k个最小的数直接返回
     */
    int idx=0;
    public int kthSmallest(TreeNode root, int k) {
        if (root==null) return 0;
        int res = kthSmallest(root.left,k);
        if (++idx==k) return root.val;
        return res>0?res:kthSmallest(root.right,k);
    }
    /**
     * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     * @param root
     * @return [[1,3],[2,5],[4]]
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null) return result;
        //BFS
        Queue<TreeNode> queue = new LinkedList();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int n = queue.size();
            for (int i = 0;i < n;i++){
                TreeNode f = queue.poll();//弹出队头元素
                list.add(f.val);
                if(f.left != null) queue.offer(f.left);
                if(f.right != null) queue.offer(f.right);
            }
            result.addFirst(list);
        }
        return result;
    }

    static Map<TreeNode,Integer> map = new HashMap<>();
    static List<TreeNode> list = new ArrayList<>();
    static List<Integer> intList = new ArrayList<>();

    /**
     *       4
     *      *  *
     *     2    5
     *    * *
     *   1   3
     * @param args
     */
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        TreeNode l1 = new TreeNode(2);
        root.left = l1;
        l1.left = new TreeNode(1);
        l1.right = new TreeNode(3);
        root.right = new TreeNode(5);
        TreeNodeSummary obj = new TreeNodeSummary();
        obj.iterationInOrderFind(root);
        for (Integer a:intList)
        System.out.println(a);

    }
}
