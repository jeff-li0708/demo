package algorithm;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * Created by liangl on 2019/3/18.
 */
public class LinkedListAddTwoNum {
    public static void main(String[] args) {
        ListNode l11 = new ListNode(1);
        ListNode l12 = new ListNode(2);
        l11.next = l12;
        ListNode l21 = new ListNode(8);
        ListNode l22 = new ListNode(9);
        l21.next = l22;
        ListNode listNode = addTwoNumbers(l11,l21);
        System.out.println();
    }

     public static class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
     }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        int sum = l1.val + l2.val;
        ListNode resultNode = new ListNode(sum%10);
        int carryOver = sum/10;
        l1 = l1.next;
        l2 = l2.next;

        ListNode temp = resultNode;
        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null) {
                sum = l1.val + l2.val + carryOver;
                l1 = l1.next;
                l2 = l2.next;
            } else if (l1 != null){
                sum = l1.val + carryOver;
                l1 = l1.next;
            } else {
                sum = l2.val + carryOver;
                l2 = l2.next;
            }
            temp.next = new ListNode(sum%10);
            carryOver = sum/10;
            temp = temp.next;
        }
        if (carryOver > 0) {
            temp.next = new ListNode(carryOver);
        }
        return resultNode;
    }
}
