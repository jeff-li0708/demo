package algorithm;

/**
 * 给定一个链表，将链表的没两个数进行旋转
 * 例如 1->2->-3>-4->5
 * 输入2->1->4->3->5
 */
public class SwapListNode {
    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        ListNode head = node;
        for(int i = 2;i <6;i++) {
            node.next = new ListNode(i);
            node = node.next;
        }
        if (head == null || head.next == null) return;
        int i = 1;
        ListNode returnNode = head.next;
        ListNode pro = null;
        while(head!=null) {
            if (i%2==1 && head.next != null){
                ListNode temp = head.next;
                head.next = temp.next;
                temp.next = head;
                if (pro != null){
                    pro.next = temp;
                }
                pro = head;
            } else {
                head = head.next;
            }
            i++;
        }
        System.out.println(returnNode);
    }
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
