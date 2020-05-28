package ticTacToe;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TicTacToeBoard implements Board, Position {
    private Scanner in;
    private PrintStream out;
    private int n, m, k;
    private int empty;

    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private Cell turn;

    public TicTacToeBoard() {
        in = new Scanner(System.in);
        out = System.out;
        while (m < 1) {
            try {
                out.println("Enter number of rows:");
                m = Integer.parseInt(in.next());
                if (m < 1) {
                    throw new java.lang.NumberFormatException();
                }
                break;
            } catch (java.lang.NumberFormatException e) {
                out.println("Invalid number, try again:");
            }
        }
        while (true) {
            try {
                out.println("Enter number of columns:");
                n = Integer.parseInt(in.next());
                if (n < 1) {
                    throw new java.lang.NumberFormatException();
                }
                break;
            } catch (java.lang.NumberFormatException e) {
                out.println("Invalid number, try again:");
            }
        }
        while (true) {
            try {
                out.println("Enter win row:");
                k = Integer.parseInt(in.next());
                if (k < 1 || (k > n && k > m)) {
                    throw new java.lang.NumberFormatException();
                }
                break;
            } catch (java.lang.NumberFormatException e) {
                out.println("Invalid number, try again:");
            }
        }
        this.cells = new Cell[n][m];
        empty = n * m;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();

        int inDiag1 = 1 + cnt(move, 1, 1) + cnt(move, -1, -1);
        int inDiag2 = 1 + cnt(move, -1, 1) + cnt(move, 1, -1);
        int inRow = 1 + cnt(move, 0, 1) + cnt(move, 0, -1);
        int inColumn = 1 + cnt(move, -1, 0) + cnt(move, -1, 0);
        empty--;

        if (inRow >= k || inColumn >= k || inDiag1 >= k || inDiag2 >= k) {
            return Result.WIN;
        }
        if (empty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getColumn() && move.getColumn() < n
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell()
                && move.getColumn() < m && move.getRow() < n;
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < m; i++) {
            sb.append(Integer.toString(i));
        }
        for (int r = 0; r < n; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < m; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }

    private int cnt(Move move, int dir1, int dir2) {
        int res = 0;
        for (int u = 1; u < k; u++) {
            if (move.getRow() + dir1 * u < n && move.getRow() + dir1 * u >= 0 &&
                    move.getColumn() + dir2 * u < n && move.getColumn() + dir2 * u >= 0 &&
                    cells[move.getRow() + dir1 * u][move.getColumn() + dir2 * u] == turn) {
                res++;
            }
        }
        return res;
    }

    /*private int parse() {
        int res;
        while (true) {
            try {
                out.println("Enter number of rows:");
                res = Integer.parseInt(in.next());
                break;
            } catch (java.lang.NumberFormatException e) {
                out.println("Invalid number, try again:");
            }
        }
        return res;
    }*/
}
