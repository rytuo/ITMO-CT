package queue;

// unique queue && shared functions
public class ArrayQueueADT {
    // inv: n >= 0 ^ ∀i=1 .. n : a[i] != null
    private ArrayQueue arr;

    public ArrayQueueADT() {
        arr = new ArrayQueue();
    }

    // Pre: element != null
    public static void enqueue(ArrayQueueADT queue, Object element) {
        queue.arr.enqueue(element);
    }
    // Post: n=n'+1 ^ a[n] = element
    //       ∀i=1 .. n' : a[i]=a[i]'

    // Pre: n > 0
    public static Object element(ArrayQueueADT queue) {
        return queue.arr.element();
    }
    // Post: R=a[1] ^ immutable

    // Pre: n > 0
    public static Object dequeue(ArrayQueueADT queue) {
        return queue.arr.dequeue();
    }
    // Post: R=a[1] ^ n=n'-1 ^ ∀i=1..n : a[i] = a[i]'

    public static Integer size(ArrayQueueADT queue) {
        return queue.arr.size();
    }
    // Post: R = n ^ immutable

    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.arr.isEmpty();
    }
    // Post: R = (n==0) ^ immutable

    public static void clear(ArrayQueueADT queue) {
        queue.arr.clear();
    }
    // Post: n = 0 ^ ∀i : a[i]=null

    public static Object[] toArray(ArrayQueueADT queue) {
        return queue.arr.toArray();
    }
    // Post: R = [a[1], ... , a[n]] ^ immutable
}
