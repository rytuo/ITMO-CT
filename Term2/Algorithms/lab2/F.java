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
 
    static int nextStr(Reader in) throws IOException {
        int c = in.read();
        while (Character.isWhitespace(c))
            c = in.read();
        in.read();
        in.read();
        return c;
    }
 
    public static void main(String[] args) throws IOException {
        Reader in = new BufferedReader(new InputStreamReader(System.in));
        int x, j, n = nextInt(in), m = nextInt(in);
        Node head = new Node();
        for (int i = 0; i < n; i++) {
            Pair t = split(head, i);
            x = nextInt(in);
            head = merge(merge(t.f, new Node(x)), t.s);
        }
        int s;
        for(int i = 0; i < m; i++) {
            s = nextStr(in);
            if (s == 'a') {
                j = nextInt(in);
                x = nextInt(in);
                Pair t = split(head, j);
                head = merge(merge(t.f, new Node(x)), t.s);
            } else {
                x = nextInt(in);
                Pair t = split(head, x - 1);
                t.s = split(t.s, 1).s;
                head = merge(t.f, t.s);
            }
        }
        System.out.println(head.c);
        print(head);
    }
}