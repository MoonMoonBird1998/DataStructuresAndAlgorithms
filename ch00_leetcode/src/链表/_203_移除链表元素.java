package 链表;


/**
 * 删除链表中等于给定值 val 的所有节点。
 *
 * 示例:
 *
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 * https://leetcode-cn.com/problems/remove-linked-list-elements/
 */
public class _203_移除链表元素 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(6);
        ListNode node5 = new ListNode(4);
        ListNode node6 = new ListNode(6);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = null;

        ListNode node = removeElements(node1, 6);
        System.out.println(node.val);
        System.out.println(node.next.val);
        System.out.println(node.next.next.val);
        System.out.println(node.next.next.next.val);
    }

    public static ListNode removeElements(ListNode head, int val) {
        if (head == null) return null;
        //设置一个虚假头节点
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        ListNode temp = fakeHead;
        while(temp.next != null){
            if (temp.next.val != val){
                temp = temp.next;
            }else {
                ListNode current = temp.next;
                while (current.next != null &&  current.next.val == val){
                    current = current.next;
                }
                temp.next = current.next;
            }
        }
        return fakeHead.next;
    }
}
