package mocomabe;

import mocomabe.models.NormalSudoku;
import mocomabe.models.SudokuBaseListener;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * SodokuTest
 */
public class SodokuTest {

    private NormalSudoku normalSudoku;
    private boolean raised;

    @Before
    public void setup() {
        normalSudoku = new NormalSudoku(9);
        raised = false;
    }

    @Test
    public void canAddNumberTest_True() {
        Assert.assertTrue(normalSudoku.canAddNumber(8, 8, 9));
    }

    @Test
    public void canAddNumberTest_DoubleNeighbor() {
        normalSudoku.AddNumber(0, 0, 1);
        Assert.assertFalse(normalSudoku.canAddNumber(0, 1, 1));
    }

    @Test
    public void canAddNumberTest_DoubleInGroup() {
        normalSudoku.AddNumber(0, 0, 5);
        Assert.assertFalse(normalSudoku.canAddNumber(2, 2, 5));
    }

    @Test
    public void addNumberTest() {
        normalSudoku.AddNumber(0, 0, 3);
        Assert.assertEquals(3, normalSudoku.getField(0, 0));
    }

    @Test(timeout = 1000)
    public void fieldChangedEventTest() {
        normalSudoku.AddSudokuListener(new SudokuBaseListener() {
            @Override
            public void fieldChanged(int x, int y, int value) {
                Assert.assertEquals(0, x);
                Assert.assertEquals(0, y);
                Assert.assertEquals(5, value);
            }
        });

        normalSudoku.AddNumber(0, 0, 5);

    }

    private boolean result;
    @Test
    public void raiseAllFieldChangesEvent() {
        normalSudoku.AddSudokuListener(new SudokuBaseListener() {
            @Override
            public void fieldChanged(int x, int y, int value) {
                result = true;
            }
        });

        normalSudoku.raiseFieldChangedEvents();
        assertTrue(result);
    }
}
