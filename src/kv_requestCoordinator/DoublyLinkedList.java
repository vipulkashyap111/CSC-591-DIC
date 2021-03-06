package kv_requestCoordinator;

import java.io.Serializable;

/**
 * Created by gmeneze on 11/26/16.
 */

class DLLNode implements Serializable {
    public static final long serialVersionUID = -3040196452457271695L;
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

public class DoublyLinkedList implements Serializable {
    public static final long serialVersionUID = -3040196452457271695L;
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
            newNode.getNext().setPrev(newNode);
            addHelper = newNode.getNext();

            System.out.println(" newNode is: " + newNode.getValue() + " prevNode is: " + newNode.getPrev().getValue() + " nextNode is: " + newNode.getNext().getValue());

            return val;
        }
    }

    public Integer getPrevValue(int value, int n) {
        DLLNode current = top;

        while (current.getNext() != null) {
            if (current.getValue() == value) {
                //go back n steps
                for (int i = 0; i < n; i++) {
                    System.out.println("current is: " + current.getValue());
                    System.out.println(" **** prev value is: " + current.getPrev().getValue() + " prev prev is: " + current.getPrev().getPrev().getValue());
                    current = current.getPrev();
                }
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    public Integer getNextValue(int value, int n) {
        DLLNode current = top;

        while (current.getNext() != null) {
            if (current.getValue() == value) {
                //go back n steps
                for (int i = 0; i < n; i++) {
                    current = current.getNext();
                }
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

}
