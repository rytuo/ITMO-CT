package queue;

// shared queue && shared functions
public class ArrayQueueModule {
    // inv: n >= 0 ^ ∀i=1 .. n : a[i] != null
    private static ArrayQueue arr;

    public ArrayQueueModule() {
        arr = new ArrayQueue();
    }

    // Pre: element != null
    public static void enqueue(Object element) {
        arr.enqueue(element);
    }
    // Post: n=n'+1 ^ a[n] = element
    //       ∀i=1 .. n' : a[i]=a[i]'

    // Pre: n > 0
    public static Object element() {
        return arr.element();
    }
    // Post: R=a[1] ^ immutable

    // Pre: n > 0
    public static Object dequeue() {
        return arr.dequeue();
    }
    // Post: R=a[1] ^ n=n'-1 ^ ∀i=1..n : a[i] = a[i+1]'

    public static int size() {
        return arr.size();
    }
    // Post: R = n ^ immutable

    public static boolean isEmpty() {
        return arr.isEmpty();
    }
    // Post: R = (n==0) ^ immutable

    public static void clear() {
        arr.clear();
    }
    // Post: n = 0 ^ ∀i : a[i]=null

    public static Object[] toArray() {
        return arr.toArray();
    }
    // Post: R = [a[1], ... , a[n]] ^ immutable
}
