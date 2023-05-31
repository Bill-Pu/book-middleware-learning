package link;
/*leetcode 707*/
class MyLinkedList {
    int val;
    MyLinkedList next;
    public MyLinkedList() {

    }

    public MyLinkedList(int val) {
        this.val = val;
    }

    public int get(int index) {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.next = this;
        MyLinkedList tempLink = myLinkedList;
        for (int i = 0; i <index ; i++) {
            if (tempLink.next == null) {
                return -1;
            }
            tempLink = tempLink.next;
        }
        return tempLink.next.val;
    }
    
    public void addAtHead(int val) {
        MyLinkedList myLinkedList = new MyLinkedList(val);
        myLinkedList.next = this;

    }
    
    public void addAtTail(int val) {

    }
    
    public void addAtIndex(int index, int val) {

    }
    
    public void deleteAtIndex(int index) {

    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */