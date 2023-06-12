package linked_list_set;

import kotlinx.atomicfu.AtomicRef;

public class SetImpl implements Set {
    private class Node {
        final int x;
        final AtomicRef<Node> next;

        Node(int x, Node next) {
            this.next = new AtomicRef<Node>(next);
            this.x = x;
        }
    }

    class Removed extends Node {
        Removed(int x, Node next) {
            super(x, next);
        }
    }

    private class Window {
        Node cur, next;
    }

    private final Node head = new Node(Integer.MIN_VALUE, new Node(Integer.MAX_VALUE, null));

    /**
     * Returns the {@link Window}, where cur.x < x <= next.x
     */
    private Window findWindow(int x) {
        Window w = new Window();
        w.cur = head;
        w.next = w.cur.next.getValue();
        while (w.next.x < x) {
            Node node = w.next.next.getValue();
            if (node instanceof Removed) {
                if (!w.cur.next.compareAndSet(w.next,
                        new Node(node.x, node.next.getValue()))) {
                    return findWindow(x);
                }
                w.next = node;
            } else {
                w.cur = w.next;
                w.next = w.cur.next.getValue();
            }
        }
        return w;
    }

    public boolean add(int x) {
        while (true) {
            Window w = findWindow(x);
            if (w.next.x == x) {
                return false;
            }
            Node node = new Node(x, w.next);
            if (w.cur.next.compareAndSet(w.next, node)) {
                return true;
            }
        }
    }

    public boolean remove(int x) {
        while (true) {
            Window w = findWindow(x);
            if (w.next.x != x) {
                return false;
            }
            Node node = w.next.next.getValue();
            if (!(node instanceof Removed) &&
                    w.next.next.compareAndSet(node,
                            new Removed(node.x, node.next.getValue()))) {
                if (!(w.next instanceof Removed)) {
                    w.cur.next.compareAndSet(w.next, node);
                }
                return true;
            }
        }
    }

    public boolean contains(int x) {
        Window w = findWindow(x);
        return w.next.x == x;
    }
}