package search;

import java.util.ArrayList;

public class BinarySearchMissing {
    public static void main(String[] args) {
        // Check needed input data
        if (args.length == 0) {
            System.out.println(0);
            return;
        }

        // Parse input data: x, a[...]
        int x = Integer.parseInt(args[0]);
        ArrayList<Integer> a = new ArrayList<Integer>();
        for (int j = 1; j < args.length; j++) {
            a.add(Integer.parseInt(args[j]));
        }

        // Pre: x ^ a[...] ^ (∀i,j=0 .. n-1 : a[i]>=a[j] ^ i<j)
        int q = IterativeBin(x, a);
        int p = RecursiveBin(x, a);
        // Post: 1) R,       (∃  a[R]: a[R] == x) ^ (a[R-1] > x)
        //       2) (-R -1), (!∃ a[R] == x) ^ (a[R-1] > x > a[R])

        // if (p == q) Output==R; else Output=="error"
        System.out.println(q == p ? q : "error");
    }

    // Pre: x ^ a[...] ^ (∀i,j=0 .. n-1 : a[i]>=a[j] ^ i<j)
    public static int IterativeBin(int x, ArrayList<Integer> a) {
        // a[-1] == +inf  ^  a[last+1] == -inf |=> a[-1] > x > a[last+1]
        int l = -1, r = a.size();
        // (a[l] > x > a[r]) ^ (a[i]>=a[j] ^ i<j) |=> inv: l < ans <= r

        // inv: l < ans <= r
        while (l + 1 < r) {
            int m = (l + r) / 2;
            // a[l'] > a[m] >= a[r'] ?
            if (a.get(m) > x) {
                // a[m] > x >= a[r'] ^ inv |=> m < ans <= r'
                l = m;
            } else {
                // a[l'] > x >= a[m] ^ inv |=> l' < ans <= m
                r = m;
            }
            // narrowing the search segment ^ repeat
        }
        // (l'+1 >= r') ^ inv |=> (l'+1 == r')
        // ans == r'

        // Check for missing element
        if (r < a.size() && a.get(r) == x) {
            // ∃ a[r] == x
            return r;
            // Output: r
        } else {
            // !∃ a[r] || a[r] != x
            return (-r - 1);
            // Output: (-r -1)
        }
    }
    // Post: 1) R,       (∃  a[R]: a[R] == x) ^ (a[R-1] > x)
    //       2) (-R -1), (!∃ a[R] == x) ^ (a[R-1] > x > a[R])

    // Pre: x ^ a[...] ^ (∀i,j=0 .. n-1 : a[i]>=a[j] ^ i<j)
    public static int RecursiveBin(int x, ArrayList<Integer> a) {
        // a[-1] == +inf  ^  a[last+1] == -inf |=> a[-1] > x > a[last+1]
        // (a[l] > x > a[r]) ^ (a[i]>=a[j] ^ i<j) |=> inv: l < ans <= r

        // inv: l < ans <= r
        // Pre: (a[i]>=a[j] ^ i<j)
        int R = RecursiveBin(x, a, -1, a.size());
        // Post: a[R-1] > x >= a[R]

        // Check for missing element
        if (R < a.size() && a.get(R) == x) {
            // ∃ a[R] == x
            return R;
            // Output: R
        } else {
            // !∃ a[R] || a[R] != x
            return (-R - 1);
            // Output: (-R -1)
        }
    }
    // Post: 1) R,       (∃  a[R]: a[R] == x) ^ (a[R-1] > x)
    //       2) (-R -1), (!∃ a[R] == x) ^ (a[R-1] > x > a[R])

    // inv: l < ans <= r
    // Pre: (a[i]>=a[j] ^ i<j) ^ (a[l] > x > a[r])
    private static int RecursiveBin(int x, ArrayList<Integer> a, int l, int r) {
        if (l + 1 >= r) {
            // (l'+1 >= r') ^ inv |=> (l'+1 == r')
            // ans == r'
            return r;
        }

        int m = (l + r) / 2;
        // a[l'] >= a[m] > a[r']

        // a[l'] > a[m] >= a[r'] ?
        if (a.get(m) > x) {
            // a[m] > x >= a[r'] ^ inv |=> m < ans <= r'
            return RecursiveBin(x, a, m, r);
        } else {
            // a[l'] > x >= a[m] ^ inv |=> l' < ans <= m
            return RecursiveBin(x, a, l, m);
        }
        // narrowing the search segment ^ repeat
    }
    // Post: a[R-1] > x >= a[R]
}