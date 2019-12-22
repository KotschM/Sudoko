package mocomabe.models;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * SudokuBase
 */
public abstract class SudokuBase {

    private ArrayList<SudokuBaseListener> listeners = new ArrayList<>();
    
    public abstract boolean canAddNumber(int x, int y, int value);
    public abstract void AddNumber(int x, int y, int value);
    public abstract int getSize();
    public abstract int getField(int x, int y);
    public abstract void raiseFieldChangedEvents();

    public void AddSudokuListener(SudokuBaseListener listener) {
        listeners.add(listener);
    }

    protected void raiseEvent(Consumer<SudokuBaseListener> delegate) {
        listeners.forEach(delegate);
    }
    
}