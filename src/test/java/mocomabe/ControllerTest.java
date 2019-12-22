package mocomabe;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import mocomabe.controllers.DefaultMainController;
import mocomabe.controllers.MainController;
import mocomabe.controllers.MainControllerListener;
import mocomabe.models.Difficulty;
import mocomabe.models.SudokuType;
/**
 * ControllerTest
 */
public class ControllerTest {

    private MainController controller; 
    private volatile boolean raised;

    @Before
    public void setup() {
        controller = new DefaultMainController();
        init();
        raised = false; //for event checking
    }

    private void blocking(){
        while(!raised){}
    }


    private void init() {
        controller.setDifficulty(Difficulty.Starter);
        controller.setSize(9);
        controller.setSudokuType(SudokuType.Normal);
    }

    @Test
    public void testSizeChange(){
        controller.setSize(9);
        assertEquals(9, controller.getSize());
    }

    @Test
    public void testDifficultyChange(){
        controller.setDifficulty(Difficulty.Hard);
        assertEquals(Difficulty.Hard, controller.getDifficulty());
    }

    @Test
    public void testSudokuTypeChanged(){
        controller.setSudokuType(SudokuType.Killer);;
        assertEquals(SudokuType.Killer,controller.getSudokuType());
    }

   
    
    @Test(timeout = 1000)
    public void testOptionChangedEvent() {
        controller.addMainControllerListener(new MainControllerListener(){
        
            @Override
            public void sudokuFieldSolved() {
                
            }
        
            @Override
            public void sudokuFieldChanged(int x, int y, int value) {
                
            }
        
            @Override
            public void changeSudokuFieldOptions() {
                raised = true;
            }
        });
        controller.setSize(16);
        blocking();
    }
    
    @Test(timeout = 1000)
    public void testFieldChangedEvent() {

        controller.addMainControllerListener(new MainControllerListener(){
            
            @Override
            public void sudokuFieldSolved() {
                
            }
            
            @Override
            public void sudokuFieldChanged(int x, int y, int value) {
                raised = true;
            }
            
            @Override
            public void changeSudokuFieldOptions() {
                
            }
        });
        controller.createNewSudoku();
        blocking();
    }

    @Test(timeout = 1000)
    public void testSudokuSolvedEvent() {
        controller.addMainControllerListener(new MainControllerListener(){
        
            @Override
            public void sudokuFieldSolved() {
                raised = true;
            }
        
            @Override
            public void sudokuFieldChanged(int x, int y, int value) {
                
            }
        
            @Override
            public void changeSudokuFieldOptions() {
                
            }
        });
        controller.createNewSudoku();
        controller.solveSudoku();
        blocking();
    }

    @Test
    public void testIsSolvingActive()  {
        controller.createNewSudoku();
        controller.solveSudoku();
        assertTrue(controller.isSolving());
    }

    @Test(timeout = 1000)
    public void testIsSolvingInactive() {
        controller.addMainControllerListener(new MainControllerListener(){
        
            @Override
            public void sudokuFieldSolved() {
                raised = true;
                assertFalse(controller.isSolving());
            }
        
            @Override
            public void sudokuFieldChanged(int x, int y, int value) {
                
            }
        
            @Override
            public void changeSudokuFieldOptions() {
                
            }
        });
        controller.createNewSudoku();
        assertFalse(controller.isSolving());
        controller.solveSudoku();
        blocking();
    }

}