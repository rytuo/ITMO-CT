package queue;

// unique queue && unique methods
public class LinkedQueue extends AbstractQueue {
    // inv: n >= 0 ^ ∀i=1 .. n : a[i] != null

    // inv: a[0] = null
    private Node head = new Node();
    // inv: a[n+1] = null
    private Node tail = new Node();
    //
    private Node current;
    //
    private int currentInd;

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
    // Post: n = n' + 1 ^ a[n] = element

    // Pre: n > 0
    protected void deleteHead() {
        head.R = head.R.R;
        head.R.L = head;
    }
    // Post: R = a[1] ^ n = n' - 1

    protected void renew() {
        head.R = tail;
        tail.L = head;
        current = head;
        currentInd = -1;
    }
    // Post: n = 0 ^ ∀i : a[i] = null

    protected Object getHead() {
        return head.R.value;
    }

    protected Object getElement(int i) {
        while (currentInd < i) {
            currentInd++;
            current = current.R;
        }
        while (currentInd > i) {
            currentInd--;
            current = current.L;
        }
        return current.value;
    }
    // Post: R = a[1] ^ immutable
}