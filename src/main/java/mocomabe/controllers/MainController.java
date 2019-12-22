package mocomabe.controllers;

import mocomabe.models.Difficulty;
import mocomabe.models.SudokuType;

/**
 * MainController
 */
public interface MainController {

    /**
     * 
     * @param listener
     */
    void addMainControllerListener(MainControllerListener listener);

    /**
     * 
     */
    void createNewSudoku();
    /**
     * 
     */
    void solveSudoku();

    /**
     * 
     * @param size
     */
    void setSize(int size);
    /**
     * @return
     */
    int getSize();

    /**
     * 
     * @param difficulty
     */
    void setDifficulty(Difficulty difficulty);
    /**
     * 
     * @return
     */
    Difficulty getDifficulty();

    /**
     * 
     * @param type
     */
    void setSudokuType(SudokuType type);
    
    /**
     * 
     * 
     * @return
     */
    SudokuType getSudokuType();

    boolean isSolving();
    
}