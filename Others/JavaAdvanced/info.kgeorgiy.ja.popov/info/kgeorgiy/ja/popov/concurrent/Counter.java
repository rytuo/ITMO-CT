package info.kgeorgiy.ja.popov.concurrent;

public class Counter {
    private int val;

    Counter(int val) {
        this.val = val;
    }

    public void tick() {
        this.val--;
    }

    public boolean finished() {
        return this.val == 0;
    }
}
