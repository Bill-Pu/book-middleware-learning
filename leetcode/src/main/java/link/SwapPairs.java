package link;

/**
 * @Author PYB
 * @Date 2023/5/22 21:10
 * @Version 1.0
 */
public class SwapPairs {
    public ListNode swapPairs(ListNode head) {
        ListNode cur = new ListNode(0);
        ListNode ori = cur;
        cur.next = head;
        ListNode first = null;
        ListNode second = null;
        ListNode third = null;
        while(cur.next != null && cur.next.next != null){
            first = cur.next;
            second = first.next;
            third = second.next;
            cur.next = second;
            second.next = first;
            first.next = third;
            cur = second;
        }
        return ori.next;
    }
}
