package link;

/**
 * @Author PYB
 * @Date 2023/5/22 15:47
 * @Version 1.0
 */
/*
* public class ListNode {
      int val;
      ListNode next;*/
public class DeleteOneElement {
    public ListNode removeElementsStander(ListNode head, int val) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode temp = dummyHead;
        while (temp.next != null) {
            if (temp.next.val == val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return dummyHead.next;
    }

    public static ListNode removeElements(ListNode head, int val) {
        ListNode listNode = new ListNode(0);
        listNode.next = head;
        ListNode headTemp = listNode;
        while (headTemp.next != null) {
            if (headTemp.next.val == val) {
                //跳过被命中的节点
                headTemp.next = headTemp.next.next;
            }
            else
                headTemp = headTemp.next;
        }
        return listNode.next;
    }


}
