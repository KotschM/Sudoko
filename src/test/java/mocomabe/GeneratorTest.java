package mocomabe;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.*;

import mocomabe.models.*;
import mocomabe.services.Generator;
/**
 * Generator
 */
public class GeneratorTest {

    private Generator generator;

    @Before
    public void setup() {
        generator = new Generator();
    }

    @Test()
    public void testDifficultyWallflower() {
        int[] emptyCells = new int[]{0,0,0,0};
        SudokuBase[] sudokus = new SudokuBase[4];
        sudokus[0] = generator.createSudoku(SudokuType.Normal, Difficulty.Wallflower, 4);
        sudokus[1] = generator.createSudoku(SudokuType.Normal, Difficulty.Wallflower, 9);
        sudokus[2] = generator.createSudoku(SudokuType.Normal, Difficulty.Wallflower, 16);
        sudokus[3] = generator.createSudoku(SudokuType.Normal, Difficulty.Wallflower, 25);
        for (int i = 0; i < sudokus.length; i++) {
            for (int x = 0; x < sudokus[i].getSize(); x++) {
                for (int y = 0; y < sudokus[i].getSize(); y++) {
                    if (sudokus[i].getField(x, y) == 0) {
                        emptyCells[i]++;
                    }
                }
            }
        }
        assertArrayEquals(new int[]{(int)(0.2*4*4),(int)(0.2*9*9),(int)(0.2*16*16),(int)(0.2*25*25)}, emptyCells);
        
    }

    @Test
    public void testDifficultyDevilish() {
        int[] emptyCells = new int[]{0,0,0,0};
        SudokuBase[] sudokus = new SudokuBase[4];
        sudokus[0] = generator.createSudoku(SudokuType.Normal, Difficulty.Devilish, 4);
        sudokus[1] = generator.createSudoku(SudokuType.Normal, Difficulty.Devilish, 9);
        sudokus[2] = generator.createSudoku(SudokuType.Normal, Difficulty.Devilish, 16);
        sudokus[3] = generator.createSudoku(SudokuType.Normal, Difficulty.Devilish, 25);
        for (int i = 0; i < sudokus.length; i++) {
            for (int x = 0; x < sudokus[i].getSize(); x++) {
                for (int y = 0; y < sudokus[i].getSize(); y++) {
                    if (sudokus[i].getField(x, y) == 0) {
                        emptyCells[i]++;
                    }
                }
            }
        }
        assertArrayEquals(new int[]{(int)(0.6*4*4),(int)(0.6*9*9),(int)(0.6*16*16),(int)(0.6*25*25)}, emptyCells);
    }

    @Test
    public void testDifficultyStarter() {
        int[] emptyCells = new int[]{0,0,0,0};
        SudokuBase[] sudokus = new SudokuBase[4];
        sudokus[0] = generator.createSudoku(SudokuType.Normal, Difficulty.Starter, 4);
        sudokus[1] = generator.createSudoku(SudokuType.Normal, Difficulty.Starter, 9);
        sudokus[2] = generator.createSudoku(SudokuType.Normal, Difficulty.Starter, 16);
        sudokus[3] = generator.createSudoku(SudokuType.Normal, Difficulty.Starter, 25);
        for (int i = 0; i < sudokus.length; i++) {
            for (int x = 0; x < sudokus[i].getSize(); x++) {
                for (int y = 0; y < sudokus[i].getSize(); y++) {
                    if (sudokus[i].getField(x, y) == 0) {
                        emptyCells[i]++;
                    }
                }
            }
        }
        assertArrayEquals(new int[]{(int)(0.3*4*4),(int)(0.3*9*9),(int)(0.3*16*16),(int)(0.3*25*25)}, emptyCells);
    }

    @Test
    public void testDifficultyMedium() {
        int[] emptyCells = new int[]{0,0,0,0};
        SudokuBase[] sudokus = new SudokuBase[4];
        sudokus[0] = generator.createSudoku(SudokuType.Normal, Difficulty.Medium, 4);
        sudokus[1] = generator.createSudoku(SudokuType.Normal, Difficulty.Medium, 9);
        sudokus[2] = generator.createSudoku(SudokuType.Normal, Difficulty.Medium, 16);
        sudokus[3] = generator.createSudoku(SudokuType.Normal, Difficulty.Medium, 25);
        for (int i = 0; i < sudokus.length; i++) {
            for (int x = 0; x < sudokus[i].getSize(); x++) {
                for (int y = 0; y < sudokus[i].getSize(); y++) {
                    if (sudokus[i].getField(x, y) == 0) {
                        emptyCells[i]++;
                    }
                }
            }
        }
        assertArrayEquals(new int[]{(int)(0.4*4*4),(int)(0.4*9*9),(int)(0.4*16*16),(int)(0.4*25*25)}, emptyCells);
    }
    @Test
    public void testDifficultyHard() {
        int[] emptyCells = new int[]{0,0,0,0};
        SudokuBase[] sudokus = new SudokuBase[4];
        sudokus[0] = generator.createSudoku(SudokuType.Normal, Difficulty.Hard, 4);
        sudokus[1] = generator.createSudoku(SudokuType.Normal, Difficulty.Hard, 9);
        sudokus[2] = generator.createSudoku(SudokuType.Normal, Difficulty.Hard, 16);
        sudokus[3] = generator.createSudoku(SudokuType.Normal, Difficulty.Hard, 25);
        for (int i = 0; i < sudokus.length; i++) {
            for (int x = 0; x < sudokus[i].getSize(); x++) {
                for (int y = 0; y < sudokus[i].getSize(); y++) {
                    if (sudokus[i].getField(x, y) == 0) {
                        emptyCells[i]++;
                    }
                }
            }
        }
        assertArrayEquals(new int[]{(int)(0.5*4*4),(int)(0.5*9*9),(int)(0.5*16*16),(int)(0.5*25*25)}, emptyCells);
    }

    @Test
    public void testCopySudoku() {

        SudokuBase base = generator.createSudoku(SudokuType.Normal, Difficulty.Medium, 9);
        SudokuBase copy = generator.CopySudoku(base);
        assertEquals(base.getSize(), copy.getSize());
        for (int x = 0; x < base.getSize(); x++) {
            for (int y = 0; y < copy.getSize(); y++) {
                assertEquals(base.getField(x, y), copy.getField(x, y));
            }
        }
    }

    @Test
    public void testEmptySudoku() {
        SudokuBase sudoku = generator.createEmptySudoku(SudokuType.Normal, 9);
        for (int x = 0; x < sudoku.getSize(); x++) {
            for (int y = 0; y < sudoku.getSize(); y++) {
                assertEquals(0, sudoku.getField(x, y));
            }
        }
    }
}