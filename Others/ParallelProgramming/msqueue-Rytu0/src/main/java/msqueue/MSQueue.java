package msqueue;

import kotlinx.atomicfu.AtomicRef;

public class MSQueue implements Queue {
    private final AtomicRef<Node> head;
    private final AtomicRef<Node> tail;

    public MSQueue() {
        Node dummy = new Node(0);
        this.head = new AtomicRef<>(dummy);
        this.tail = new AtomicRef<>(dummy);
    }

    @Override
    public void enqueue(int x) {
        Node newTail = new Node(x);
        while (true) {
            Node curTail = tail.getValue();
            Node curTailNext = curTail.next.getValue();

            if (curTail == tail.getValue()) {
                if (curTailNext == null) {
                    if (curTail.next.compareAndSet(null, newTail)) {
                        tail.compareAndSet(curTail, newTail);
                        return;
                    }
                } else {
                    tail.compareAndSet(curTail, curTailNext);
                }
            }
        }
    }

    @Override
    public int dequeue() {
        while (true) {
            Node curHead = head.getValue();
            Node curTail = tail.getValue();
            Node curHeadNext = curHead.next.getValue();

            if (curHead == head.getValue()) {
                if (curHead == curTail) {
                    if (curHeadNext == null) {
                        return Integer.MIN_VALUE;
                    }
                    tail.compareAndSet(curTail, curHeadNext);
                } else {
                    int x = curHeadNext.x;
                    if (head.compareAndSet(curHead, curHeadNext)) {
                        return x;
                    }
                }
            }
        }
    }

    @Override
    public int peek() {
        Node curHead = head.getValue();
        Node next = curHead.next.getValue();
        if (next == null)
            return Integer.MIN_VALUE;
        return next.x;
    }

    private class Node {
        final int x;
        AtomicRef<Node> next = new AtomicRef<>(null);

        Node(int x) {
            this.x = x;
        }
    }
}