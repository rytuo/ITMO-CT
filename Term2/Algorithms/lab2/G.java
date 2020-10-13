import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;
 
public class Main {
    public static Random rand = new Random();
 
    static class Node {
        int val, c, y;
        Node l, r;
        Node(int val) {
            this.val = val;
            this.c = 1;
            this.y = rand.nextInt();
            this.l = new Node();
            this.r = new Node();
        }
        Node() {
            this.c = 0;
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
 
    static void print(Node head) {
        if (head.c == 0)
            return;
        print(head.l);
        System.out.print(head.val + " ");
        print(head.r);
    }
 
    static int nextInt(Reader in) throws IOException {
        int c = in.read(), res = 0;
        while (Character.isWhitespace(c))
            c = in.read();
        while(c != -1 && !Character.isWhitespace(c)) {
            res = 10 * res + c - 48;
            c = in.read();
        }
        return res;
    }
 
    public static void main(String[] args) throws IOException {
        Reader in = new BufferedReader(new InputStreamReader(System.in));
        int l, r, n = nextInt(in), m = nextInt(in);
        Node head = new Node();
        for (int i = 0; i < n; i++) {
            Pair t = split(head, i);
            head = merge(merge(t.f, new Node(i + 1)), t.s);
        }
        for(int i = 0; i < m; i++) {
            l = nextInt(in);
            r = nextInt(in);
            Pair t = split(head, l - 1);
            Pair p = split(t.s, r - l + 1);
            head = merge(p.f, merge(t.f, p.s));
        }
        print(head);
    }
}