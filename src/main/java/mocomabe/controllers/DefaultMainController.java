package mocomabe.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import mocomabe.models.*;
import mocomabe.services.*;

/**
 * DefaultMainController
 */
public class DefaultMainController implements MainController {

    private List<MainControllerListener> listeners;
    private SudokuBase sudoku;
    private SudokuBase result;

    private int size;
    private SudokuType type;
    private Difficulty difficulty;
    private Solver solver;
    private Generator generator;


    public DefaultMainController() {
        super();
        this.type = SudokuType.Normal;
        this.listeners = new ArrayList<>();
        this.generator= new Generator();
        addMainControllerListener(new MainControllerListener(){
        
            @Override
            public void sudokuFieldSolved() {}
        
            @Override
            public void sudokuFieldChanged(int x, int y, int value) {}
        
            @Override
            public void changeSudokuFieldOptions() {
                if (solver != null) {
                    solver.cancelSolving();
                }
            }
        });
    }

    @Override
    public void addMainControllerListener(MainControllerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void createNewSudoku() {
        if (this.solver != null) {
            this.solver.cancelSolving();
        }
        
        sudoku = generator.createSudoku(this.type, this.difficulty, this.size);
        result = generator.CopySudoku(sudoku);
        result.AddSudokuListener(new SudokuBaseListener(){
        
            @Override
            public void fieldChanged(int x, int y, int value) {
                raiseEvents(l -> l.sudokuFieldChanged(x,y, value));
            }
        });

        result.raiseFieldChangedEvents();
    }

    @Override
    public void solveSudoku() {
            if (this.solver != null && this.solver.isRunning()) {
                throw new IllegalArgumentException();
            }
            this.solver = new Solver();
            this.solver.addSolverListener(new SolverListener(){
            
                @Override
                public void solvingFinished() {
                    raiseEvents(l -> l.sudokuFieldSolved());
                }
            });
            this.solver.solveSudoku(this.type, this.sudoku, this.result);
    }

    @Override
    public void setSize(int size) {
        if (this.size != size) {
            this.size = size;
            raiseEvents(c -> c.changeSudokuFieldOptions());
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {
        if (this.difficulty != difficulty) {
            this.difficulty = difficulty;
            raiseEvents(c -> c.changeSudokuFieldOptions());
        }
    }

    @Override
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    public void setSudokuType(SudokuType type) {
        if (this.type != type) {
            this.type = type;
            raiseEvents(c -> c.changeSudokuFieldOptions());
        }
    }

    @Override
    public SudokuType getSudokuType() {
        return this.type;
    }


    private void raiseEvents(Consumer<MainControllerListener> delegate){
        listeners.forEach(delegate);
    }

    @Override
    public boolean isSolving() {
        return this.solver != null && this.solver.isRunning();
    }
}