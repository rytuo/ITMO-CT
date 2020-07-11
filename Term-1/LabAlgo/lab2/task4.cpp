import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        LList a = new LList();

        String c;
        int n, t;
        n = in.nextInt();

        for (int i = 0; i < n; i++) {
            c = in.next();
            if (c.equals("+")) {
                t = in.nextInt();
                a.add(t);
            } else if (c.equals("*")) {
                t = in.nextInt();
                a.addMiddle(t);
            } else {
                a.pop();
            }
        }
    }
}

class Unit {
    int value;
    Unit L;
    Unit R;

    public Unit() {
        L = null;
        R = null;
    }
}

class LList {
    Unit first, last, mid;
    public int size;

    public LList () {
        first = new Unit();
        last = new Unit();
        first.R = last;
        last.L = first;
        mid = first;
        size = 0;
    }

    void add (int x) {
        Unit n = new Unit();
        n.value = x;
        n.L = last.L;
        n.R = last;
        last.L.R = n;
        last.L = n;
        size++;
        if (size % 2 == 1) {
            mid = mid.R;
        }
    }

    void addMiddle(int x) {
        Unit n = new Unit();
        n.value = x;
        n.L = mid;
        n.R = mid.R;
        mid.R.L = n;
        mid.R = n;
        size++;
        if (size % 2 == 1) {
            mid = mid.R;
        }
    }

    void pop () {
        first = first.R;
        first.L = null;
        System.out.println(first.value);
        size--;
        if (size % 2 == 1) {
            mid = mid.R;
        }
    }
}