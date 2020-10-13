import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;
 
public class Main {
    public static Random rand = new Random();
 
    static class Node {
        int val, c, y, isRev;
        Node l, r;
        Node(int val) {
            this.val = val;
            this.c = 1;
            this.isRev = 0;
            this.y = rand.nextInt();
            this.l = null;
            this.r = null;
        }
        Node() {}
    }
 
    static class Pair {
        Node f, s;
        Pair(Node f, Node s) {
            this.f = f;
            this.s = s;
        }
    }
 
    static void upd(Node n) {
        n.c = (n.l == null ? 0 : n.l.c) + (n.r == null ? 0 : n.r.c) + 1;
    }
 
    static void makeRev(Node n) {
        Node t = n.l;
        n.l = n.r;
        n.r = t;
        if (n.l != null)
            n.l.isRev ^= 1;
        if (n.r != null)
            n.r.isRev ^= 1;
        n.isRev = 0;
    }
 
    static Pair split(Node head, int x) {
        Pair ans = new Pair(null, null);
        if (head == null)
            return ans;
        if (head.isRev == 1) {
            makeRev(head);
        }
        int cur = head.l == null ? 0 : head.l.c;
        if (cur < x) {
            ans = split(head.r, x - cur - 1);
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
        if (left == null)
            return right;
        if (right == null)
            return left;
        if (left.isRev == 1)
            makeRev(left);
        if (right.isRev == 1)
            makeRev(right);
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
    
    static void print(Node head) {
        if (head == null)
            return;
        if (head.isRev == 1)
            makeRev(head);
        print(head.l);
        System.out.print(head.val + " ");
        print(head.r);
    }
 
    public static void main(String[] args) throws IOException {
        Reader in = new BufferedReader(new InputStreamReader(System.in));
        int l, r, n = nextInt(in), m = nextInt(in);
        Node head = null;
        for (int i = 0; i < n; i++) {
            head = merge(split(head, i).f, new Node(i + 1));
        }
        for(int i = 0; i < m; i++) {
            l = nextInt(in);
            r = nextInt(in);
            Pair t = split(head, r);
            Pair p = split(t.f, l - 1);
            p.s.isRev = 1;
            head = merge(merge(p.f, p.s), t.s);
        }
 
        print(head);
    }
}