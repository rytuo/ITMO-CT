package ticTacToe;

import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        final Tournament tournament = new Tournament();
        List<Integer> result;
        do {
            result = tournament.play();
            System.out.println("\tGAME OVER!");
            if (result.size() == 1) {
                System.out.println("Winner is player " + result.get(0));
            } else {
                System.out.print("Winners are players");
                for (int i = 0; i < result.size(); i++) {
                    System.out.println(" " + result.get(i));
                }
            }
        } while (result.size() != 0);
    }
}
