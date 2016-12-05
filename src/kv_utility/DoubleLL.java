package kv_utility;

/**
 * Created by abhishek on 11/27/16.
 */

public class DoubleLL {

    private ValueDetail head;
    private ValueDetail tail;
    private int size;

    public DoubleLL() {
        size = 0;
    }

    public int size() {
        return size;
    }

    /**
     * return whether the list is empty or not
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * adds element at the starting of the linked list
     *
     * @param element
     */
    public void addFirst(ValueDetail element) {
        if (head != null) {
            head.prev = element;
        }
        head = element;
        if (tail == null) {
            tail = element;
        }
        size++;
        System.out.println("adding: " + element.getValue());
    }

    /**
     * this method removes element from the start of the linked list
     *
     * @return
     */
    public boolean removeElement(ValueDetail node)
    {
        if (size == 0) return false;
        if (node.prev != null)
            node.prev.next = node.next;
        else
            head = node.next;
        if (node.next != null)
            node.next.prev = node.prev;
        else
            tail = node.prev;
        size--;
        System.out.println("deleted: " + node.getValue() + ":" + node.getUnixTS());
        return true;
    }


    public ValueDetail reverseIterator(){return tail;}

    public ValueDetail getRNext(ValueDetail node)
    {
        if(node.prev != null)
            return node.prev;
        else
            return null;
    }
}
