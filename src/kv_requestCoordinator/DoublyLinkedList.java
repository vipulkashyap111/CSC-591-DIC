package kv_requestCoordinator;

/**
 * Created by gmeneze on 11/26/16.
 */

class DLLNode {
    private int value;
    private DLLNode next;
    private DLLNode prev;

    public DLLNode(int value) {
        this.setValue(value);
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DLLNode getNext() {
        return next;
    }

    public void setNext(DLLNode next) {
        this.next = next;
    }

    public DLLNode getPrev() {
        return prev;
    }

    public void setPrev(DLLNode prev) {
        this.prev = prev;
    }
}

public class DoublyLinkedList {
    int value;

    public DLLNode top;

    private DLLNode addHelper;

    public int addNode() {
        if (top == null) {
            top = new DLLNode(0);
            top.setNext(top);
            top.setPrev(top);
            addHelper = top;
            return 0;
        } else {
            int current = addHelper.getValue();
            int next = addHelper.getNext().getValue();

            if (next == 0) {
                next = 100;
            }

            int val = (current + next) / 2;

            DLLNode newNode = new DLLNode(val);
            newNode.setPrev(addHelper);
            newNode.setNext(addHelper.getNext());
            addHelper.setNext(newNode);
            addHelper = newNode.getNext();
            return val;
        }
    }
}
