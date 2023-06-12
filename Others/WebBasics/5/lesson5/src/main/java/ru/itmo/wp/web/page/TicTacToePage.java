package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {

    public enum cell {
        X,
        O
    }

    public enum phases {
        RUNNING,
        WON_X,
        WON_O,
        DRAW
    }

    public static class State {
        private final int size;
        private final cell[][] cells;
        private int emptyAmount;
        private phases phase;
        private Boolean crossesMove;

        public State(int size) {
            this.size = size;
            cells = new cell[size][size];
            emptyAmount = size * size;
            phase = phases.RUNNING;
            crossesMove = true;
        }

        public int getSize() {
            return size;
        }

        public cell[][] getCells() {
            return cells;
        }

        public int getEmptyAmount() {
            return emptyAmount;
        }

        public phases getPhase() {
            return phase;
        }

        public Boolean getCrossesMove() {
            return crossesMove;
        }
    }

    private static State state;

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (state == null) {
            newGame(request, view);
        }
        view.put("state", state);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        // IMPORTANT: 0 < size < 10
        state = new State(3);
        view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        if (state == null) {
            newGame(request, view);
        }

        if (state.phase == phases.RUNNING) {
            Optional<String> cellName = request.getParameterMap().keySet().stream()
                    .filter(param -> param.matches("cell_[0-9][0-9]"))
                    .findFirst();

            if (cellName.isPresent()) {
                int row = cellName.get().charAt(5) - 48;
                int col = cellName.get().charAt(6) - 48;

                if (row < state.size && col < state.size &&
                        state.cells[row][col] == null) {
                    makeMove(row, col);
                }
            }
        }

        view.put("state", state);
    }

    private void makeMove(int row, int col) {
        state.cells[row][col] = state.crossesMove ? cell.X : cell.O;
        if (--state.emptyAmount == 0) {
            state.phase = phases.DRAW;
        }
        checkWinner(state);
        state.crossesMove = !state.crossesMove;
    }

    private void checkWinner(State state) {
        checkWinnerInLines(state);
        checkWinnerInDiagonals(state);
    }

    private void checkWinnerInLines(State state) {
        for (int i = 0; i < state.size; i++) {
            if (state.cells[i][0] != null &&
                    Arrays.stream(state.cells[i]).allMatch(state.cells[i][0]::equals)) {
                setWinner(state);
                return;
            }

            final int idx = i;
            if (state.cells[0][idx] != null &&
                    Arrays.stream(state.cells).allMatch(arr -> arr[idx] == state.cells[0][idx])) {
                setWinner(state);
                return;
            }
        }
    }

    private void checkWinnerInDiagonals(State state) {
        final int[] i = {0};
        if (state.cells[0][0] != null &&
                Arrays.stream(state.cells).allMatch(arr -> arr[i[0]++] == state.cells[0][0])) {
            setWinner(state);
            return;
        }
        i[0] = state.size - 1;
        if (state.cells[0][state.size - 1] != null &&
                Arrays.stream(state.cells).allMatch(arr -> arr[i[0]--] == state.cells[0][state.size - 1])) {
            setWinner(state);
        }
    }

    private void setWinner(State state) {
        if (state.crossesMove) {
            state.phase = phases.WON_X;
        } else {
            state.phase = phases.WON_O;
        }
    }
}