package mocomabe;
import static org.junit.Assert.assertTrue;
import org.junit.*;

import mocomabe.models.*;
import mocomabe.services.*;
/**
 * SolverTest
 */
public class SolverTest {

    private Solver solver;
    private SudokuBase base;
    private SudokuBase result;
    private volatile boolean raised;

    @Before
    public void setup() {
        solver = new Solver();
        Generator gen = new Generator();
        base =  gen.createSudoku(SudokuType.Normal, Difficulty.Medium, 9);
        result = gen.CopySudoku(base);
        raised = false; //for event checking
    }

    private void blocking(){
        while(!raised){}
    }

    

    @Test(timeout = 1000)
    public void testSolvingFinished() {
        solver.addSolverListener(new SolverListener(){
        
            @Override
            public void solvingFinished() {
                raised = true;
            }
        });
        solver.solveSudoku(SudokuType.Normal, base, result);
        blocking();
    }

    @Test
    public void testCancelSolving()
    {
        solver.solveSudoku(SudokuType.Normal, base, result);
        solver.cancelSolving();
        Assert.assertFalse(solver.isRunning());
    }

    @Test()
    public void testCheckingIsRunning() {
        solver.solveSudoku(SudokuType.Normal, base, result);
        assertTrue(solver.isRunning());
    }

    @Test()
    public void testCheckingIsRunningAfterSolved() {
        solver.addSolverListener(new SolverListener(){
        
            @Override
            public void solvingFinished() {
                Assert.assertFalse(solver.isRunning());
            }
        });
        solver.solveSudoku(SudokuType.Normal, base, result);
    }
}