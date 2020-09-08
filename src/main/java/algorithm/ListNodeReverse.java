package algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。
 *
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 *  
 *
 * 示例：
 *
 * 给你这个链表：1->2->3->4->5
 *
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 *
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 *
 * 解题思路：分别取子链表翻转后加到返回节点的尾部
 *
 */
public class ListNodeReverse {
    public static void main(String[] args) {

        ListNode node = new ListNode(1);
        ListNode head = node;
        for(int i = 2;i <3;i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        head = reverseKGroup(head,2);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1 || head == null) return head;
        ListNode subNodeTail = null;
        ListNode subNodeHead = null;
        ListNode returnNodeHead = null;
        ListNode returnNodeTail = null;
        int i = 0;
        while (head != null) {
            if (subNodeTail == null){
                subNodeTail = head;
                subNodeHead = subNodeTail;
            } else {
                subNodeTail.next = head;
                subNodeTail = subNodeTail.next;
            }

            i++;
            if (i == k) {
                ListNode temp = subNodeTail.next;
                subNodeTail.next = null;
                if (returnNodeTail == null) {
                    returnNodeHead = reverseListRec(subNodeHead);
                    returnNodeTail = subNodeHead;
                } else {
                    returnNodeTail.next = reverseListRec(subNodeHead);
                    returnNodeTail = subNodeHead;

                }
                subNodeTail = null;
                i = 0;
                head = temp;
            } else {
                head = head.next;
            }
        }
        if (i>0) {
            returnNodeTail.next = subNodeHead;
        }
        return returnNodeHead;

    }
    public static ListNode reverseListRec(ListNode head){
        if(head==null||head.next==null)return head;
        ListNode reHead=reverseListRec(head.next);
        head.next.next=head;
        head.next=null;
        return reHead;
    }
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
