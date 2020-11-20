package algorithm;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * 常见的链表处理
 * Created by liangl on 2019/2/25.
 */
public class LinkedListSummary {

    public static class ListNode{
        int value;
        ListNode next;
        public ListNode(int n){
            this.value=n;
            this.next=null;
        }
    }

    //求单链表中结点的个数: getListLength
    public static int getListLength(ListNode head){
        int len=0;
        while(head!=null){
            len++;
            head=head.next;
        }
        return len;
    }
    public  static ListNode swapListNode(ListNode head){
        if (head == null || head.next == null) return head;
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
        return returnNode;
    }
    /**
     * 将单链表反转,循环
     * 输入：1->2->3->4
     * 输出：4->3->2->1
     */
    public static ListNode reverseList(ListNode head){
        if(head==null||head.next==null)return head;
        ListNode pre=null;
        ListNode nex=null;
        while(head!=null){
            nex=head.next;
            head.next=pre;
            pre=head;
            head=nex;
        }
        return pre;
    }
    //将单链表反转,递归
    public static ListNode reverseListRec(ListNode head){
        if(head==null||head.next==null)return head;
        ListNode reHead=reverseListRec(head.next);
        head.next.next=head;
        head.next=null;
        return reHead;
    }
    //查找单链表中的倒数第K个结点（k > 0）
    public static ListNode reGetKthListNode(ListNode head,int k){
        if(head==null)return head;
        int len=getListLength(head);
        if(k>len)return null;
        ListNode target=head;
        ListNode nexk=head;
        for(int i=0;i<k;i++){
            nexk=nexk.next;
        }
        while(nexk!=null){
            target=target.next;
            nexk=nexk.next;
        }
        return target;
    }
    //查找单链表的中间结点(快慢指针，相当于两个指针,一个前进步长为1，一个为2)
    public static ListNode getMiddleListNode(ListNode head){
        if(head==null||head.next==null)return head;
        ListNode target=head;
        ListNode temp=head;
        while(temp!=null&&temp.next!=null){
            target=target.next;
            temp=temp.next.next;
        }
        return target;
    }
    //从尾到头打印单链表,递归
    public static void reversePrintListRec(ListNode head){
        if(head==null)return;
        else{
            reversePrintListRec(head.next);
            System.out.println(head.value);
        }
    }
    //从尾到头打印单链表,栈
    public static void reversePrintListStack(ListNode head){
        Stack<ListNode> s=new Stack<ListNode>();
        while(head!=null){
            s.push(head);
            head=head.next;
        }
        while(!s.isEmpty()){
            System.out.println(s.pop().value);
        }
    }
    //合并两个有序的单链表head1和head2，循环
    public static ListNode mergeSortedList(ListNode head1,ListNode head2){
        if(head1==null)return head2;
        if(head2==null)return head1;
        ListNode target=null;
        if(head1.value>head2.value){
            target=head2;
            head2=head2.next;
        }
        else{
            target=head1;
            head1=head1.next;
        }
        target.next=null;
        ListNode mergeHead=target;
        while(head1!=null && head2!=null){
            if(head1.value>head2.value){
                target.next=head2;
                head2=head2.next;
            }
            else{
                target.next=head1;
                head1=head1.next;
            }
            target=target.next;
            target.next=null;
        }
        if(head1==null)target.next=head2;
        else target.next=head1;
        return mergeHead;
    }
    //合并两个有序的单链表head1和head2，递归
    public static ListNode mergeSortedListRec(ListNode head1,ListNode head2){
        if(head1==null)return head2;
        if(head2==null)return head1;
        if(head1.value>head2.value){
            head2.next=mergeSortedListRec(head2.next,head1);
            return head2;
        }
        else{
            head1.next=mergeSortedListRec(head1.next,head2);
            return head1;
        }
    }
    //对单链表进行排序,归并排序,在排序里面不建议选用递归的合并有序链表算法，如果链表长度较长，很容易出现栈溢出
    public static ListNode listSort(ListNode head){
        ListNode nex=null;
        if(head==null||head.next==null)return head;
        else if(head.next.next==null){
            nex=head.next;
            head.next=null;
        }
        else{
            ListNode mid=getMiddleListNode(head);
            nex=mid.next;
            mid.next=null;
        }
        return mergeSortedList(listSort(head),listSort(nex));//合并两个有序链表，不建议递归
    }
    //对单链表进行排序,插入排序
    public ListNode insertionSortList(ListNode head) {
        if(head==null||head.next==null)return head;
        ListNode pnex=head.next;
        ListNode pnex_nex=null;
        head.next=null;
        while(pnex!=null){
            pnex_nex=pnex.next;
            ListNode temp=head;
            ListNode temp_pre=null;
            while(temp!=null){
                if(temp.value>pnex.value)break;
                temp_pre=temp;
                temp=temp.next;
            }
            if(temp_pre==null){
                head=pnex;
                pnex.next=temp;
            }
            else{
                temp_pre.next=pnex;
                pnex.next=temp;
            }
            pnex=pnex_nex;
        }
        return head;
    }
    //判断一个单链表中是否有环,快慢指针
    public static boolean hasCycle(ListNode head){
        boolean flag=false;
        ListNode p1=head;
        ListNode p2=head;
        while(p1!=null&&p2!=null){
            p1=p1.next;
            p2=(p2.next != null ? p2.next.next:null);
            if(p2==p1&&p1 != null){
                flag=true;
                break;
            }
        }
        return flag;
    }
    //判断两个单链表是否相交,如果相交返回第一个节点，否则返回null
    //如果单纯的判断是否相交，只需要看最后一个指针是否相等
    public static ListNode isIntersect(ListNode head1,ListNode head2){
        ListNode target=null;
        if(head1==null||head2==null)return target;
        int len1=getListLength(head1);
        int len2=getListLength(head2);
        if(len1>=len2){
            for(int i=0;i<len1-len2;i++){
                head1=head1.next;
            }
        }else{
            for(int i=0;i<len2-len1;i++){
                head2=head2.next;
            }
        }
        while(head1!=null&&head2!=null){
            if(head1==head2){
                target=head1;
                break;
            }
            else{
                head1=head1.next;
                head2=head2.next;
            }
        }
        return target;
    }
    //已知一个单链表中存在环，求进入环中的第一个节点,利用hashmap，不要用ArrayList，因为判断ArrayList是否包含某个元素的效率不高
    public static ListNode getFirstListNodeInCycleHashMap(ListNode head){
        ListNode target=null;
        HashMap<ListNode,Boolean> map=new HashMap<ListNode,Boolean>();
        while(head!=null){
            if(map.containsKey(head))target=head;
            else{
                map.put(head, true);
            }
            head=head.next;
        }
        return target;
    }
    //已知一个单链表中存在环，求进入环中的第一个节点,不用hashmap
    //用快慢指针，与判断一个单链表中是否有环一样，找到快慢指针第一次相交的节点，此时这个节点距离环开始节点的长度和链表头距离环开始的节点的长度相等
    public static ListNode getFirstListNodeInCycle(ListNode head){
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
            if(slow==fast)break;
        }
        if(fast==null||fast.next==null)return null;//判断是否包含环
        //相遇节点距离环开始节点的长度和链表头距离环开始的节点的长度相等
        slow=head;
        while(slow!=fast){
            slow=slow.next;
            fast=fast.next;
        }//同步走
        return slow;

    }
    //给出一单链表头指针head和一节点指针delete，O(1)时间复杂度删除节点delete
    //可以采用将delete节点value值与它下个节点的值互换的方法，但是如果delete是最后一个节点，则不行，但是总得复杂度还是O(1)
    public static void deleteListNode(ListNode head,ListNode delete){
        //首先处理delete节点为最后一个节点的情况
        if(delete==null)return;
        if(delete.next==null){
            if(head==delete)head=null;
            else{
                ListNode temp=head;
                while(temp.next!=delete){
                    temp=temp.next;
                }
                temp.next=null;
            }
        }
        else{
            delete.value=delete.next.value;
            delete.next=delete.next.next;
        }
        return;
    }

    /**
     * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
     * 输入: 1->2->3->4->5->NULL, k = 2
     * 输出: 4->5->1->2->3->NULL
     * @param head
     * @param k
     * @return
     */
    public static ListNode moveListNode(ListNode head,int k){
        if(head==null)return head;
        int len=getListLength(head);
        k = k % len;
        if (k == 0) return head;
        ListNode nexk=head;
        ListNode target=head;
        for(int i=0;i<k;i++){
            nexk=nexk.next;
        }
        ListNode returnNode = head;
        while(nexk != null){
            if (nexk.next == null) {
                returnNode = target.next;
                target.next = null;
                nexk.next = head;
                break;
            } else {
                target=target.next;
                nexk=nexk.next;
            }
        }
        return returnNode;
    }

    /**
     * 给定一个头结点为 root 的链表, 编写一个函数以将链表分隔为 k 个连续的部分。每个字链表的长度相差不能超过1
     * @param root
     * @param k
     * @return
     */
    public static ListNode[] splitListNode(ListNode root,int k){
        ListNode[] returnNode = new ListNode[k];
        if(root == null || k == 0) return returnNode;
        int len = getListLength(root);
        int subLen = len / k,p = len % k;
        int[] subLenArr = new int[k];
        for (int i = 0;i<k;i++) {
            if (i < p){
                subLenArr[i] = subLen+1;
            } else {
                subLenArr[i] = subLen;
            }
        }
        int i=0;
        ListNode head = root;
        while(root != null){
            subLenArr[i]--;
            if (subLenArr[i] == 0) {
                ListNode temp = root.next;
                root.next = null;
                returnNode[i++] = head;
                head = temp;
                root = temp;
            } else {
                root=root.next;
            }
        }
        if (head != null) {
            returnNode[i++] = head;
        }
        return returnNode;
    }

    /**
     * 判断一个链表是否为回文链表。
     * 先声明一个指向头节点的指针，利用递归判断出栈的节点是否与指针指向的节点相等，
     * 相等则指针往后移动一位
     */
    ListNode top;
    public boolean isPalindrome(ListNode head) {
        top = head;
        isPalindromeDFS(head);
        return top == null;
    }
    public void isPalindromeDFS(ListNode head){
        if (head == null) return;
        isPalindromeDFS(head.next);
        if (head.value == top.value) top = top.next;
    }

    /**
     * 给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
     * 示例 1:
     * 输入: 1->2->3->4->5->NULL
     * 输出: 1->3->5->2->4->NULL
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode evenHead = head.next;
        ListNode odd = head, even = evenHead;
        while (even != null && even.next != null) {
            odd.next = even.next;//奇数的下一个节点是偶数节点的下一个
            odd = odd.next;//移动奇数节点指针
            even.next = odd.next;//偶数节点的下一个是奇数节点的下一个
            even = even.next;//一定偶数节点的指针
        }
        odd.next = evenHead;//奇数节点的尾部指向偶数节点的头部
        return head;
    }

    public static void main(String[] args) {

        Scanner in=new Scanner(System.in);
        ListNode head=null;
        if(in.hasNextInt()){
            head=new ListNode(in.nextInt());
        }
        ListNode temp=head;
        while(in.hasNextInt()){
            temp.next=new ListNode(in.nextInt());
            temp=temp.next;
        }
        in.close();
//        int len=getListLength(head);
        //ListNode reHead=reverseList(head);
        //reHead=reverseListRec(reHead);
        //ListNode ListNode_k=reGetKthListNode(head,3);
//        ListNode mid=getMiddleListNode(head);
//        System.out.println(mid.value);
        //reversePrintListRec(head);
        //reversePrintListStack(head);
        //ListNode mergeHead=mergeSortedList(head,null);
        //ListNode sortHead=listSort(head);
//        ListNode[] listNodes = splitListNode(head, 3);
//        System.out.println(JSON.toJSONString(listNodes));
        String s = "3";

        System.out.println(s.compareTo("30"));
    }

}
