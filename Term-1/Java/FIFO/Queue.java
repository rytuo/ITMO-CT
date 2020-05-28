package queue;

// inv: n>=0 ^ ∀i=0 .. n-1 : a[i] != null
public interface Queue {
    // Pre: element != null
    void enqueue(Object element);
    // Post: n=n'+1 ^ a[n-1] = element
    //       ∀i=0 .. n' : a[i]=a[i]'

    // Pre: n > 0
    Object element();
    // Post: R=a[0] ^ immutable

    // Pre: n > 0
    Object dequeue();
    // Post: R=a[0] ^ n=n'-1 ^ ∀i=0..n-1 : a[i] = a[i]'

    int size();
    // Post: R = n ^ immutable

    boolean isEmpty();
    // Post: R = (n==0) ^ immutable

    void clear();
    // Post: n = 0 ^ ∀i : a[i]=null

    Object[] toArray();
    // Post: R = [a[0], ... , a[n-1]] ^ immutable
}