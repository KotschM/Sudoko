//Start: package and import area
package mocomabe.services;
import java.util.*;
//End: package and import area

import mocomabe.models.Difficulty;
import mocomabe.models.NormalSudoku;
import mocomabe.models.SudokuBase;
import mocomabe.models.SudokuType;

public class Generator{

    //Start: global references and variables
    int[][] unsolvedSudoku;
    int[] row;
    int size;
    Random rnd = new Random();
    //End: global references and variables


    /**
     * In this method, the int array "row" is filled with values.
     * For this a list is needed to recognize already inserted numbers.
     * The int array "row" contains the numbers 1 to X at the end but in random order.
     * @author Moris Kotsch
     */
    private void createRow(){
        
        List<Integer> listOneToX = new ArrayList<>();
        row = new int[this.size];
        for (int i = 1; i <= this.size; i++) {  //For loop initializes the list
            listOneToX.add(i);
        }
        for (int i = 0; i < row.length; i++) {
            int randomNumber = listOneToX.get(rnd.nextInt(listOneToX.size()));
            row[i] = randomNumber;
            listOneToX.remove(Integer.valueOf(randomNumber));
        }
    }


    /**
     * This method ensures that columns and rows are swapped so that
     * no patterns can be recognized in the later Sudoku.
     * Which columns or rows are swapped is chosen randomly.
     * In this case the swapping is repeated 2 times.
     * @author Moris Kotsch
    */

    private void swapRowColumn(int sizeOfSudoku){
        int tempSize = (int)Math.sqrt(sizeOfSudoku);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < tempSize; y++) {//Only columns of the same group may be swapped.
                int r = rnd.nextInt(tempSize);
                int s = rnd.nextInt(tempSize);
                int[] cache = new int[sizeOfSudoku];
                for (int i = 0; i < unsolvedSudoku.length; i++) {
                    cache[i] = unsolvedSudoku[r+(y*tempSize)][i];
                    unsolvedSudoku[r+(y*tempSize)][i] = unsolvedSudoku[s+(y*tempSize)][i];
                    unsolvedSudoku[s+(y*tempSize)][i] = cache[i];
                }
            }
            for (int y = 0; y < tempSize; y++) {//Only rows of the same group may be swapped.
                int r = rnd.nextInt(tempSize);
                int s = rnd.nextInt(tempSize);
                int[] cache = new int[sizeOfSudoku];
                for (int i = 0; i < unsolvedSudoku.length; i++) {
                    cache[i] = unsolvedSudoku[i][r+(y*tempSize)];
                    unsolvedSudoku[i][r+(y*tempSize)] = unsolvedSudoku[i][s+(y*tempSize)];
                    unsolvedSudoku[i][s+(y*tempSize)] = cache[i];
                }
            }
        }
    }


    /**
     * This method randomly sets different cells within the
     * int array "unsolvedSudoku" to 0.
     * The parameter "int n" specifies how many cells should be set to 0.
     * Cells that are already set to 0 are skipped.
     * @param maxEmptyCells The value represents the empty cells in the finished Sudoku.
     * @author Moris Kotsch
     */
    
    private void deleteCells(int maxEmptyCells){
        int c;
        int r;
        int count = 0;
        boolean[][] alreadyVisited = new boolean[this.size][this.size];
        while (count < maxEmptyCells) {
            r = rnd.nextInt(this.size);
            c = rnd.nextInt(this.size);
            if (!alreadyVisited[r][c]) {
                unsolvedSudoku[r][c] = 0;
                alreadyVisited[r][c] = true;
                count++;
            }
        }
    }
    

    /**
     * In this method, the first row is created first.
     * This is inserted into the remaining rows by rotating to the right.
     * Then, columns/rows are swapped randomly in the int array.
     * Finally n digits are set to 0 to get an unresolved Sudoku.
     * The unresolved Sudoku is then returned.
     * @param emptyCells The value represents the empty cells in the finished Sudoku.
     * @return int[][]
     * @author Moris Kotsch
     */
    private int[][] createSudoku(int emptyCells){
        unsolvedSudoku = new int[this.size][this.size];
        int shift = 0;
        createRow();
        for (int i = 0; i < row.length; i++) {
            for (int s = 0; s < row.length; s++) {
                switch (size) {
                    case 4:
                        shift = (s<2) ? (s*2) :
                                ((s*2)+1);
                        break;
                
                    case 9:
                        shift = (s<3) ? (s*3) :
                                ((s<6) ? ((s*3)+1) :
                                ((s*3)+2));
                        break;

                    case 16:
                        shift = (s<4) ? (s*4) :
                                ((s<8) ? ((s*4)+1) :
                                ((s<12) ? ((s*4)+2) :
                                ((s*4)+3)));
                        break;

                    case 25:
                        shift = (s<5) ? (s*5) :
                                ((s<10) ? ((s*5)+1) :
                                ((s<15) ? ((s*5)+2) :
                                ((s<20) ? ((s*5)+3) :
                                ((s*5)+4))));
                        break;
                    default:
                        break;
                }
                unsolvedSudoku[(i + shift) % row.length][s] = row[i];

            }
        }
        swapRowColumn(this.size);
        deleteCells(emptyCells);
        return unsolvedSudoku;
    }

    public SudokuBase createSudoku(SudokuType type, Difficulty difficulty, int size) {
        this.size = size;
        double diffi = (difficulty == Difficulty.Wallflower) ? (0.2) :
                    ((difficulty == Difficulty.Starter) ? (0.3) :
                    ((difficulty == Difficulty.Medium) ? (0.4) :
                    ((difficulty == Difficulty.Hard) ? (0.5) : (0.6))));
        int emptyCells = (int)(diffi * (this.size*this.size));

        SudokuBase sudoku;
        
        switch (type) {
            case Normal:
                sudoku = new NormalSudoku(size);
                int[][] data = createSudoku(emptyCells);
                for (int i = 0; i < data.length; i++) {
                    for (int d = 0; d < data[i].length; d++) {
                        sudoku.AddNumber(i, d, data[i][d]);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        return sudoku;
    }

    public SudokuBase createEmptySudoku(SudokuType type, int size) {
        switch (type) {
            case Normal:
                return new NormalSudoku(size);
            default:
                throw new IllegalArgumentException();
        }
    }

    public SudokuBase CopySudoku(SudokuBase base) {

        SudokuBase result;
        if (base.getClass() == NormalSudoku.class) {
            result = createEmptySudoku(SudokuType.Normal, base.getSize());
        }
        else {
            throw new IllegalArgumentException();
        }
        for (int x = 0; x < base.getSize(); x++) {
            for (int y = 0; y < base.getSize(); y++) {
                result.AddNumber(x, y, base.getField(x, y));
            }
        }
        return result;
    }

}