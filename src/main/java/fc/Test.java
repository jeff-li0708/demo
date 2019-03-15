package fc;

import bean.Node;

import java.util.concurrent.atomic.LongAdder;

/**
 * Created by liangl on 2019/2/28.
 */
public class Test {
    //private static final sun.misc.Unsafe UNSAFE = sun.misc.Unsafe.getUnsafe();

    public static void main(String[] args) throws NoSuchFieldException {
//        Class<?> tk = Thread.class;
//        Long probe = UNSAFE.objectFieldOffset
//                (tk.getDeclaredField("threadLocalRandomProbe"));
//        System.out.println(probe);
        Node list1 = new Node(0);
        Node list2 = new Node(-1);
        Node next1 = list1;
        Node next2 = list2;
        for (int i = 1; i <= 6; i++){
            next1.next = new Node(i*4);
            next1 = next1.next;

            next2.next = new Node(i*3);
            next2 = next2.next;
        }
        //Node node = merge(list1,list2);
        Node node = reversal(list1);
        while (node!= null){
            System.out.println(node.value);
            node = node.next;
        }
    }

    public static Node mergeRec(Node list1,Node list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        if (list1.value > list2.value) {
            list2.next = mergeRec(list2.next,list1);
            return list2;
        } else {
            list1.next = mergeRec(list1.next,list2);
            return list1;
        }
    }

    public static Node merge(Node list1,Node list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        Node target = null;
        if (list1.value < list2.value) {
            target = list1;
            list1 = list1.next;
        } else {
            target = list2;
            list2 = list2.next;
        }
        target.next = null;
        Node mergeNode = target;
        while (list1 != null || list2 != null) {
            if (list2 == null || list1.value < list2.value) {
                target.next = list1;
                list1 = list1.next;
            } else {
                target.next = list2;
                list2 = list2.next;
            }
            target = target.next;
            target.next = null;
        }
        return mergeNode;
    }

    public static Node reversal(Node head) {
        if(head == null || head.next == null) return head;
        Node reHead = reversal(head.next);
        head.next.next = head;
        head.next = null;
        return reHead;
    }

    public static void verifySingleton() {
        try {
            SingletonTest singletonTest1 = SingletonTest.getSingletonInstance();
            new SerializableUtil<SingletonTest>().serializableObj(singletonTest1);

            SingletonTest singletonTest2 = new SerializableUtil<SingletonTest>().deserializeObj();
            System.out.println(singletonTest1 == singletonTest2);
        }catch (Exception e) {
        }
    }

    public static void longAdder(){
        LongAdder longAdder = new LongAdder();
        longAdder.add(1);
        System.out.println(longAdder.longValue());
    }
}
