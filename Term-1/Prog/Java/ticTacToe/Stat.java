package ticTacToe;

import java.util.ArrayList;
import java.util.List;

public class Stat {
    private List<Integer> stat;

    public Stat(int n) {
         stat = new ArrayList<>();
         for (int i = 0; i < n; i++) {
             stat.add(0);
         }
    }

    public void add(int player, int amount) {
        stat.set(player - 1, stat.get(player - 1) + amount);
    }

    public void printStat() {
        for (int i = 1; i <= stat.size(); i++) {
            System.out.println("Player " + i + " stat: " + stat.get(i - 1));
        }
    }

    public List<Integer> winners() {
        int max = stat.get(0);
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 1; i < stat.size(); i++) {
            if (stat.get(i) > max) {
                max = stat.get(i);
            }
        }
        for (int i = 0; i < stat.size(); i++) {
            if (stat.get(i) == max) {
                ans.add(i + 1);
            }
        }
        return ans;
    }
}
