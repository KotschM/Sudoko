package mocomabe.controllers;

/**
 * MainControllerListener
 */
public interface MainControllerListener {
    /**
     * 
     * @param x
     * @param y
     * @param value
     */
    void sudokuFieldChanged(int x, int y, int value);
    void sudokuFieldSolved();
    
    void changeSudokuFieldOptions();
    
}