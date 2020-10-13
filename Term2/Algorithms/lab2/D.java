import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;
 
public class Main {
    public static Random rand = new Random();
 
    static class Node {
        int val, c, y, min, max;
        long sum;
        Node l, r;
        Node(int val) {
            this.val = val;
            this.sum = val;
            this.c = 1;
            this.y = rand.nextInt();
            this.l = new Node();
            this.r = new Node();
        }
        Node() {
            this.c = 0;
            this.sum = 0;
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
        n.sum = n.l.sum + n.r.sum + n.val;
    }
 
    static Pair split(Node head, int x) {
        Pair ans = new Pair(new Node(), new Node());
        if (head.c == 0)
            return ans;
        Node t = new Node(head.val);
        t.l = head.l;
        t.r = head.r;
        if (head.val <= x) {
            ans = split(t.r, x);
            t.r = ans.f;
            upd(t);
            return new Pair(t, ans.s);
        } else {
            ans = split(t.l, x);
            t.l = ans.s;
            upd(t);
            return new Pair(ans.f, t);
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
 
    static boolean exists(Node head, int x) {
        if (head.c == 0)
            return false;
        if (head.val == x)
            return true;
        if (head.val > x)
            return exists(head.l, x);
        else
            return exists(head.r, x);
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
 
    static int nextStr(Reader in) throws IOException {
        int c = in.read();
        while (Character.isWhitespace(c))
            c = in.read();
        return c;
    }
 
    public static void main(String[] args) throws IOException {
        Reader in = new BufferedReader(new InputStreamReader(System.in));
        int s, l, r, n = nextInt(in);
        long prev = 0;
        Node head = new Node();
        for(int i = 0; i < n; i++) {
            s = nextStr(in);
            if (s == '+') {
                l = (nextInt(in) + (int)(prev % 1_000_000_000)) % 1_000_000_000;
                if (!exists(head, l)) {
                    Pair t = split(head, l);
                    head = merge(merge(t.f, new Node(l)), t.s);
                }
                prev = 0;
            } else {
                l = nextInt(in);
                r = nextInt(in);
                prev = split(split(head, l - 1).s, r).f.sum;
                System.out.println(prev);
            }
        }
    }
}