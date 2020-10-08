package 链表;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle/
 */
public class _141_判断是否是环形链表 {

    public static void main(String[] args){
        ListNode fir = new ListNode(1);
        ListNode sec = new ListNode(2);
        ListNode th = new ListNode(3);
        fir.next = sec;
        sec.next = th;
        th.next = fir;
        _141_判断是否是环形链表 s = new _141_判断是否是环形链表();

        System.out.println(s.hasCycle(fir));
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }


}
