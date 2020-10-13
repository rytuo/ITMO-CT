import java.util.Scanner;
import java.util.Random;
 
public class Main {
    public static Random rand = new Random();
 
    static class Node {
        int val, y, w;
        Node l, r;
        Node(int val, int w) {
            this.val = val;
            this.y = rand.nextInt();
            this.w = w;
            this.l = new Node();
            this.r = new Node();
        }
        Node() {
            this.w = 0;
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
        n.w = n.l.w + n.r.w + 1;
    }
 
    static Pair split(Node head, int x) {
        Pair ans = new Pair(new Node(), new Node());
        if (head.w == 0)
            return ans;
        if (head.val < x) {
            ans = split(head.r, x);
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
        if (left.w == 0)
            return right;
        if (right.w == 0)
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
        if (head.w == 0)
            return false;
        if (head.val == x)
            return true;
        if (head.val > x)
            return exists(head.l, x);
        else
            return exists(head.r, x);
    }
 
    static int next(Node head, int x) {
        if (head.w == 0)
            return x;
        if (head.val > x) {
            int nx = next(head.l, x);
            if (nx > x)
                return nx;
            return head.val;
        } else {
            return next(head.r, x);
        }
    }
 
    static int prev(Node head, int x) {
        if (head.w == 0)
            return x;
        if (head.val < x) {
            int nx = prev(head.r, x);
            if (nx < x)
                return nx;
            return head.val;
        } else {
            return prev(head.l, x);
        }
    }
 
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s;
        int x;
        Node head = new Node();
        while(in.hasNext()) {
            s = in.next();
            x = Integer.parseInt(in.next());
            if (s.charAt(0) == 'i') {
                if (!exists(head, x)) {
                    Pair t = split(head, x);
                    head = merge(merge(t.f, new Node(x, 1)), t.s);
                }
            } else if (s.charAt(0) == 'd') {
                if (exists(head, x)) {
                    Pair t = split(head, x);
                    t.s = split(t.s, x + 1).s;
                    head = merge(t.f, t.s);
                }
            } else if (s.charAt(0) == 'e') {
                if (exists(head, x))
                    System.out.println("true");
                else
                    System.out.println("false");
            } else if (s.charAt(0) == 'n') {
                int val = next(head, x);
                if (val == x)
                    System.out.println("none");
                else
                    System.out.println(val);
            } else {
                int val = prev(head, x);
                if (val == x)
                    System.out.println("none");
                else
                    System.out.println(val);
            }
        }
    }
}