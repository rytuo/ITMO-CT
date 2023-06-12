package stack;

import kotlinx.atomicfu.AtomicRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StackImpl implements Stack {
    private static class Node {
        final AtomicRef<Node> next;
        final int x;

        Node(int x, Node next) {
            this.next = new AtomicRef<>(next);
            this.x = x;
        }
    }

    // elimination array size
    private final int SIZE = 1000;
    private final Random random = new Random(0);
    // head pointer
    private final AtomicRef<Node> head = new AtomicRef<>(null);
    // elimination array
    private final List<AtomicRef<Integer>> elimination = new ArrayList<>();


    public StackImpl() {
        for (int i = 0; i < SIZE; i++) {
            elimination.add(new AtomicRef<Integer>(null));
        }
    }

    @Override
    public void push(int x) {
        AtomicRef<Integer> store = null;
        int ind = random.nextInt(SIZE);
        Integer boxed = x;

        if (elimination.get(ind).compareAndSet(null, boxed)) {
            store = elimination.get(ind);
        } else if (elimination.get((ind + 1) % SIZE).compareAndSet(null, boxed)) {
            store = elimination.get((ind + 1) % SIZE);
        } else if (elimination.get(Math.abs(ind - 1) % SIZE).compareAndSet(null, boxed)) {
            store = elimination.get(Math.abs(ind - 1) % SIZE);
        }

        if (store != null) {
            for (int i = 0; i < 1000000000; i++) { /* spin wait */ }
            if (!store.compareAndSet(boxed, null)) return;
        }

        while (true) {
            Node curHead = head.getValue();
            Node newHead = new Node(x, curHead);
            if (head.compareAndSet(curHead, newHead)) return;
        }
    }

    @Override
    public int pop() {
        int ind = random.nextInt(SIZE);

        Integer x = elimination.get(ind).getAndSet(null);
        if (x != null) {
            return x;
        } else {
            x = elimination.get((ind + 1) % SIZE).getAndSet(null);
            if (x != null) {
                return x;
            } else {
                x = elimination.get(Math.abs(ind - 1) % SIZE).getAndSet(null);
                if (x != null) {
                    return x;
                }
            }
        }

        while (true) {
            Node curHead = head.getValue();
            if (curHead == null) return Integer.MIN_VALUE;
            if (head.compareAndSet(curHead, curHead.next.getValue())) {
                return curHead.x;
            }
        }
    }
}
