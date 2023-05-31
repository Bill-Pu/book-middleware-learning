package link;

/**
 * @Author PYB
 * @Date 2023/5/23 20:12
 * @Version 1.0
 *//*106链表相交*/
public class IntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        int aL = 0;
        int bL = 0;
        ListNode nodeA = headA;
        ListNode nodeB = headB;
        while (headA != null) {
            aL++;
            headA = headA.next;
        }
        while (headB != null){
            bL++;
            headB = headB.next;
        }
        int lage = Math.max(aL, bL);
        int index = Math.max(aL, bL) - Math.min(aL, bL);
        if(aL > bL) {
            for(int i = index; i >0; i--) {
                nodeA = nodeA.next;
            }
        }
        else if (aL < bL) {
            for(int i = index; i >0; i--) {
                nodeB = nodeB.next;
            }
        }
        //完成了两个链表指针初始化到相应位置
        //同步后移，直到相等
        while (nodeA!= null) {
            if (nodeA == nodeB) {
                return nodeA;
            }
            else {
                nodeA = nodeA.next;
                nodeB = nodeB.next;
            }
        }
        return null;
    }
}
