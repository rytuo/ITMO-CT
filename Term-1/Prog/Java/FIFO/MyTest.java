package queue;

public class MyTest {
    public static void main(String[] args) {
        Queue queue = new LinkedQueue();
        fill(queue);
        queue.dequeue();
        dump(queue.toArray());
    }

    static void fill(Queue q) {
        for (int i = 0; i < 10; i++) {
            q.enqueue(i);
        }
    }

    static void dump(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
