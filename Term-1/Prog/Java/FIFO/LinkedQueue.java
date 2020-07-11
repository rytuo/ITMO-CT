package queue;

// unique queue && unique methods
public class LinkedQueue extends AbstractQueue {
    // inv: n >= 0 ^ ∀i=0 .. n-1 : a[i] != null ^
    //      a[-1] == null ^ a[n] == null

    private Node head = new Node();
    private Node tail = new Node();

    public LinkedQueue() {
        renew();
    }

    // Pre: element != null
    protected void put(Object element) {
        Node newNode = new Node();
        newNode.value = element;
        newNode.R = tail;
        newNode.L = tail.L;
        tail.L.R = newNode;
        tail.L = newNode;
    }
    // Post: n = n' + 1 ^ a[n-1] = element

    // Pre: n > 0
    protected void deleteHead() {
        head.R = head.R.R;
        head.R.L = head;
    }
    // Post: R = a[0] ^ ∀i=0 .. n-2 : a[i] = a'[i+1]

    protected void renew() {
        head.R = tail;
        tail.L = head;
    }
    // Post: n = 0 ^ ∀i : a[i] = null

    // Pre: n > 0
    protected Object getHead() {
        return head.R.value;
    }
    // Post: R = a[0] ^ immutable
}