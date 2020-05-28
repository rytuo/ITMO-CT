package queue;

// inv: n>=0 ^ ∀i=1 .. n : a[i] != null
public interface Queue {
    // Pre: element != null
    void enqueue(Object element);
    // Post: n=n'+1 ^ a[n] = element
    //       ∀i=1 .. n' : a[i]=a[i]'

    // Pre: n > 0
    Object element();
    // Post: R=a[1] ^ immutable

    // Pre: n > 0
    Object dequeue();
    // Post: R=a[1] ^ n=n'-1 ^ ∀i=1..n : a[i] = a[i]'

    int size();
    // Post: R = n ^ immutable

    boolean isEmpty();
    // Post: R = (n==0) ^ immutable

    void clear();
    // Post: n = 0 ^ ∀i : a[i]=null

    Object[] toArray();
    // Post: R = [a[1], ... , a[n]] ^ immutable
}