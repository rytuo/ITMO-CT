package queue;

// unique queue && unique methods
public class ArrayQueue extends AbstractQueue {
    // inv: n >= 0 ^ ∀i=0 .. n-1 : a[i] != null

    // ind of first element
    private int head = 0;
    // array with elements
    private Object[] arr;

    public ArrayQueue() {
        renew();
    }

    // Pre: element != null
    protected void put(Object element) {
        ensureCapacity(size + 1);
        arr[(head + size) % arr.length] = element;
    }
    // Post: n = n' + 1 ^ a[n-1] = element

    private void ensureCapacity(int size) {
        if (arr.length >= size) {
            // arr.length >= size
            return;
        }
        // arr.length < size ^ size == arr.length + 1 |->
        // |-> (size <= arr.length * 2)

        final int capacity = arr.length * 2;
        final Object[] newQueue = new Object[capacity];
        for (int i = 0; i < size - 1; i++) {
            newQueue[i] = arr[(head + i) % arr.length];
        }
        arr = newQueue;
        head = 0;
        // arr.length = arr.length * 2
        // arr.length >= size
    }
    // Post: a.size() > n

    // Pre: n > 0
    protected void deleteHead() {
        head = (head + 1) % arr.length;
    }
    // Post: R = a[0] ^ ∀i=0 .. n-2 : a[i] = a'[i+1]

    // Pre: n > 0
    protected Object getHead() {
        return arr[head];
    }
    // Post: R = a[0] ^ immutable

    protected void renew() {
        head = 0;
        arr = new Object[10];
    }
    // Post: n = 0 ^ ∀i : a[i] = null
}