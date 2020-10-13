import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;
 
public class Main {
    public static Random rand = new Random();
 
    static class Node {
        int val, c, y, f0, ld;
        Node l, r;
        Node(int val) {
            this.val = val;
            this.c = 1;
            if (val == 0) {
                this.f0 = 1;
            } else {
                this.f0 = 2;
            }
            if (val > 0)
                this.ld = 1;
            else
                this.ld = 0;
            this.y = rand.nextInt();
            this.l = new Node();
            this.r = new Node();
        }
        Node() {
            this.c = 0;
            this.f0 = 1;
            this.ld = 0;
            this.l = null;
            this.r = null;
        }
    }
 
    static class Pair {
        Node f, s;
        Pair(Node f, Node s) {
            this.f = f;
            this.s = s;
        }
    }
 
    static void upd(Node n) {
        n.c = n.l.c + n.r.c + 1;
 
        if (n.l.f0 > n.l.c) {
            if (n.val == 0) {
                n.f0 = n.l.c + 1;
            } else {
                n.f0 = n.l.c + 1 + n.r.f0;
            }
        } else {
            n.f0 = n.l.f0;
        }
 
        if (n.r.ld > 0) {
            n.ld = n.l.c + 1 + n.r.ld;
        } else {
            if (n.val > 0) {
                n.ld = n.l.c + 1;
            } else {
                n.ld = n.l.ld;
            }
        }
    }
 
    static Pair split(Node head, int x) {
        Pair ans = new Pair(new Node(), new Node());
        if (head.c == 0)
            return ans;
        if (head.l.c < x) {
            ans = split(head.r, x - head.l.c - 1);
            head.r = ans.f;
            upd(head);
            return new Pair(head, ans.s);
        } else {
            ans = split(head.l, x);
            head.l = ans.s;
            upd(head);
            return new Pair(ans.f, head);
        }
    }
 
    static Node merge(Node left, Node right) {
        if (left.c == 0)
            return right;
        if (right.c == 0)
            return left;
        if (left.y > right.y) {
            left.r = merge(left.r, right);
            upd(left);
            return left;
        } else {
            right.l = merge(left, right.l);
            upd(right);
            return right;
        }
    }
 
    static int nextInt(Reader in) throws IOException {
        int c = in.read(), res = 0, trigger = 1;
        while (Character.isWhitespace(c))
            c = in.read();
        if (c == '-') {
            trigger = -1;
            c = in.read();
        }
        while(c != -1 && !Character.isWhitespace(c)) {
            res = 10 * res + c - 48;
            c = in.read();
        }
        return trigger * res;
    }
 
    static int print(Node head, int num, int x) {
        if (head.c == 0)
            return num;
        if (num < x) {
            num = print(head.l, num, x);
            if (num < x) {
                System.out.print(head.val + " ");
                num++;
                if (num < x)
                    num = print(head.r, num, x);
            }
        }
        return num;
    }
 
    public static void main(String[] args) throws IOException {
        Reader in = new BufferedReader(new InputStreamReader(System.in));
        int l, n = nextInt(in), m = nextInt(in);
        Node head = new Node();
        for (int i = 0; i < m; i++) {
            Pair t = split(head, 0);
            head = merge(new Node(0), t.s);
        }
        for(int i = 0; i < n; i++) {
            l = nextInt(in);
            Pair t = split(head, l - 1);
            Pair p = split(t.s, t.s.f0 - 1);
            head = merge(merge(t.f, new Node(i + 1)), merge(p.f, split(p.s, 1).s));
        }
        System.out.println(head.ld);
        print(head, 0, head.ld);
    }
}