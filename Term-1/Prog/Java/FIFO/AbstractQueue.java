package queue;

// unique queue && unique methods
abstract class AbstractQueue implements Queue {
    // inv: n >= 0 ^ ∀i=0 .. n-1 : a[i] != null

    // n
    protected int size = 0;

    // Pre: n > 0
    abstract protected Object getHead();
    // Post: R = a[0] ^ immutable

    // Pre: n > 0
    abstract protected void deleteHead();
    // Post: R = a[0] ^ ∀i=0 .. n-2 : a[i] = a'[i+1]

    // Pre: element != null
    abstract protected void put(Object element);
    // Post: n = n' + 1 ^ a[n - 1] = element

    abstract protected void renew();
    // Post: n = 0 ^ ∀i : a[i] = null

    // Pre: element != null
    public void enqueue(Object element) {
        assert element != null;
        put(element);
        size++;
    }
    // Post: n=n'+1 ^ a[n] = element
    //       ∀i=1 .. n' : a[i]=a[i]'

    // Pre: n > 0
    public Object element() {
        assert size > 0;
        return getHead();
    }
    // Post: R=a[1] ^ immutable

    // Pre: n > 0
    public Object dequeue() {
        assert size > 0;
        final Object head = getHead();
        deleteHead();
        size--;
        return head;
    }
    // Post: R=a[1] ^ n=n'-1 ^ ∀i=1..n : a[i] = a[i]'

    public int size() {
        return size;
    }
    // Post: R = n ^ immutable

    public boolean isEmpty() {
        return (size == 0);
    }
    // Post: R = (n==0) ^ immutable

    public void clear() {
        size = 0;
        renew();
    }
    // Post: n = 0 ^ ∀i : a[i]=null

    public Object[] toArray() {
        Object[] elements = new Object[size];
        for (int i = 0; i < size; i++) {
            elements[i] = dequeue();
            enqueue(elements[i]);
        }
        return elements;
    }
    // Post: R = [a[1], ... , a[n]] ^ immutable
}
