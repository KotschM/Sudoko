//Start: package and import area
package mocomabe.services;

import java.util.*;
//End: package and import area
import java.util.function.Consumer;

import javax.swing.SwingWorker;

import mocomabe.models.SudokuBase;
import mocomabe.models.SudokuType;

public class Solver {

    private SudokuBase sudoku;
    private SudokuBase result;
    private SolverBackgroundWorker worker;
    private volatile boolean working;
    private ArrayList<SolverListener> listeners;
    // End: global references and variables


    public Solver() {
        super();
        listeners = new ArrayList<>();
        working = false;
    }

    public void solveSudoku(SudokuType type, SudokuBase sudokuBase, SudokuBase resultBase) {
        if (working) {
            throw new IllegalStateException();
        }
        this.sudoku = sudokuBase;
        this.result = resultBase;
        switch (type) {
        case Normal:
            this.worker = new SolverBackgroundWorker();
            this.working = true;
            this.worker.execute();
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    public void cancelSolving() {
        while (!(this.worker.cancel(true) || this.worker.isCancelled() || this.worker.isDone())) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                // do nothing
            }
        }
        working = false;
    }

    public boolean isRunning() {
        return working;
    }


    public void addSolverListener(SolverListener listener) {
        listeners.add(listener);
    }

    private void raiseListeners(Consumer<SolverListener> delegate) {
        listeners.forEach(delegate);
    }

    private class SolverBackgroundWorker extends SwingWorker<Object, BackgroundNotification> {

        @Override
        protected Boolean doInBackground() throws Exception {
            solveSudoku();
            publish(new BackgroundNotification(0,0,0,true));
            return null;
        }

        @Override
        protected synchronized void process(List<BackgroundNotification> chunks) {
            for (BackgroundNotification chunk : chunks) {
                if(chunk.finished) 
                {
                    raiseListeners(l -> l.solvingFinished());
                }
                else if (working) {
                    result.AddNumber(chunk.x, chunk.y, chunk.value);
                }
                else {
                    break;
                }
            }
        }

        private boolean solveSudoku() {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                // not importent if it goes in a exception.
            }
            int n = sudoku.getSize();
            int row = -1;
            int col = -1;
            boolean isEmpty = true;
            // A cell is searched in which the number 0 is located.
            // Cells in which other numbers are already at the
            // beginning are not treated in the backtracking algorithm.
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (sudoku.getField(i, j) == 0) {
                        row = i;
                        col = j;
                        isEmpty = false;
                        break;
                    }
                }
                if (!isEmpty) {
                    break;
                }
            }
            if (isEmpty) { // This is the base case of the backtracking algorithm
                return true;
            }
            for (int num = 1; num <= n; num++) {
                if (sudoku.canAddNumber(row, col, num)) {
                    publish(new BackgroundNotification(row, col, num, false));
                    sudoku.AddNumber(row, col, num);
                    if (solveSudoku()) {
                        return true;
                    } else {
                        publish(new BackgroundNotification(row, col, 0, false));
                        sudoku.AddNumber(row, col, 0);// replace it
                    }
                }
            }
            return false;
        }
    }
}