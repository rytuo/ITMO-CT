package ticTacToe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            int i, j;
            while (true) {
                try {
                    System.out.println("Enter row and column");
                    i = Integer.parseInt(in.next());
                    j = Integer.parseInt(in.next());
                    break;
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Invalid numbers, try again:");
                }
            }
            final Move move = new Move(i, j, cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
}
