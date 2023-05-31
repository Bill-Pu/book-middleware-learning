package link;

import lombok.Builder;

@Builder
public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
      ListNode(int val1,int val2,int val3,int val4,int val5,int val6){
      }
      public ListNode add(int value){
            this.next = new ListNode(value);
            return this.next.next;
      }

}