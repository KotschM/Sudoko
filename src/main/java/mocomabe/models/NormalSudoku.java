package mocomabe.models;

/**
 * NormalSudoku
 */
public class NormalSudoku extends SudokuBase {

    private final int[][] storage;
    private final int size;

    public NormalSudoku(int size) {
        super();
        this.size = size;
        storage = new int[size][size];
    }

    @Override
    public int getField(int x, int y) {
        return storage[x][y];
    }

    @Override
    public boolean canAddNumber(int row, int col, int value) {
        if (row >= size || col >= size) throw new IndexOutOfBoundsException();
        //Vertical and horizontal validation
        for (int i = 0; i < storage.length; i++) {
            if (storage[row][i] == value || storage[i][col] == value) {

                return false;
            }
        }
        int sizeSQRT = (int)Math.sqrt(size);
        //Group validation
        int group = sizeSQRT * (row / sizeSQRT) + (col / sizeSQRT);//Determination of the group
        int groupRowStart = sizeSQRT * (group / sizeSQRT);//First row in the group
        int groupColStart = sizeSQRT * (group % sizeSQRT);//First column in the group
        for (int r = groupRowStart; r < groupRowStart + sizeSQRT; r++) { 
            for (int d = groupColStart; d < groupColStart + sizeSQRT; d++) { 
                if (storage[r][d] == value) { 
                    return false; //the number is compared with each element in the group.
                } 
            } 
        } 
        return true; 
    }

    @Override
    public void AddNumber(int x, int y, int value) {
        //todo: maybe check for adding?
        if (x >= size || y >= size) throw new IndexOutOfBoundsException();
        if (storage[x][y] != value) {
            storage[x][y] = value;
            this.raiseEvent(l -> l.fieldChanged(x, y, value));
        }
    }

    @Override
    public void raiseFieldChangedEvents() {
        for (int i = 0; i < size; i++) {
            for (int d = 0; d < size; d++) {
                int ii = i;
                int dd = d;
                this.raiseEvent(l -> l.fieldChanged(ii, dd, storage[ii][dd]));
            }
        }
    }

    @Override
    public int getSize() {
        return this.storage.length;
    }

    
}