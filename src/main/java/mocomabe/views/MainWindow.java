package mocomabe.views;

import java.awt.*;
import javax.swing.*;

import mocomabe.controllers.*;

/**
 * This class creates the main window of the Sudoku Game; where the grid,
 * headline and buttons are placed.
 * 
 * @author Benita Dietrich
 */

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    public SudokuField grid;
    private ButtonField buttons;
    private HeadlineField headline;
    private MenuBar menuBar;
    private MainController c;

    public MainWindow(String s) {
        super(s);

        c = new DefaultMainController();
        init();
        c.addMainControllerListener(new MainControllerListener() {

            @Override
            public void sudokuFieldSolved() {
                JOptionPane.showMessageDialog(null, "Finished", "Sudoku ist gelöst", JOptionPane.OK_CANCEL_OPTION);
            }

            @Override
            public void sudokuFieldChanged(int x, int y, int value) {
                changeField(x, y, value);
            }

            @Override
            public void changeSudokuFieldOptions() {
                changeGrid();
            }
        });
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background image
        setContentPane(new JLabel(new ImageIcon("src/main/java/mocomabe/resources/backgroundimage.jpg")));

        // Layout
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        // Add the different types of fields
        menuBar = new MenuBar(c);
        setJMenuBar(menuBar);
        add(headline = new HeadlineField());
        createGrid();
        add(buttons = new ButtonField(c));

        setResizable(true);
        pack();

        // Placing the window in the middle of the screen
        int windowWidth = 800;
        int windowHeight = 950;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // get screen dimesnions in pixel
        setBounds((screenSize.width - windowWidth) / 2, (screenSize.height - windowHeight) / 2, windowWidth,
                windowHeight); // placing in the middle

        setVisible(true);

    }

    /** Fills in the Sudoku grid */
    public void fillSudokuGrid(int[][] generatedNumbers, JTextField[][] fields) {
        for (int y = 0; y < c.getSize(); y++) {
            for (int x = 0; x < c.getSize(); x++) {
                if (generatedNumbers[y][x] == 0) {
                    fields[y][x].setText("");
                } else {
                    fields[y][x].setText(Integer.toString(generatedNumbers[y][x]));
                }
            }

        }

    }

    /**
     * Creates a new grid by checking the currently selected Sudoku size
     * 
     * @return JPanel
     */
    public void createGrid() {
        grid = new SudokuField(c.getSize());
        add(grid);
    }

    public void changeField(int x, int y, int value) {
        JTextField[][] temp = grid.getFields();
        String txt = value == 0 ? "" : "" + value;
        temp[y][x].setText(txt);
        boolean editable = (value == 0) ? true : false;
        temp[y][x].setEditable(editable);
        if (c.isSolving()) {
            temp[y][x].setForeground(new Color(240, 50, 50));
        } else {
            temp[y][x].setForeground(new Color(0, 0, 0));
        }
    }

    public void changeGrid() {
        remove(grid);
        remove(buttons);
        remove(headline);
        grid = new SudokuField(c.getSize());
        add(headline);
        add(grid);
        add(buttons);
        revalidate();
        repaint();

    }

}