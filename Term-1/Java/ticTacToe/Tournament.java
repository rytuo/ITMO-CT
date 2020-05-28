package ticTacToe;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tournament {
    final Scanner in;
    final Stat stat;
    int n, c;

    public Tournament () {
        in = new Scanner(System.in);

        System.out.println("Welcome to the m/n/k round tournament");
        while (true) {
            try {
                System.out.println("Enter number of players:");
                n = Integer.parseInt(in.next());
                if (n < 2) {
                    throw new java.lang.NumberFormatException();
                } else {
                    break;
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Invalid number, try again:");
            }
        }
        while (true) {
            try {
                System.out.println("Enter number of circles:");
                c = Integer.parseInt(in.next());
                if (c < 1) {
                    throw new java.lang.NumberFormatException();
                } else {
                    break;
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Invalid number, try again:");
            }
        }

        stat = new Stat(n);
    }

    public List<Integer> play() {
        for (int first = 1; first < n; first++) {
            for (int second = first + 1; second < n + 1; second++) {
                final Game game = new Game(false, new HumanPlayer(), new HumanPlayer());
                int result1 = 0, result2 = 0, result;
                for (int i = 1; i <= c; i++) {
                    System.out.println("Player " + first + " vs Player " + second);
                    System.out.println("\tGame № " + i);
                    result = game.play(new TicTacToeBoard());
                    if (result == 1) {
                        result1 += 3;
                        stat.add(first, 3);
                        System.out.println("Game result: " + first + " player won this round!");
                    } else if (result == 2) {
                        result2 += 3;
                        stat.add(second, 3);
                        System.out.println("Game result: " + second + " player won this round!");
                    } else if (result == 0) {
                        result1++;
                        stat.add(first, 1);
                        result2++;
                        stat.add(second, 1);
                        System.out.println("DRAW!");
                    } else {
                        i--;
                    }
                }
                stat.printStat();
            }
        }

        return stat.winners();
    }
}
