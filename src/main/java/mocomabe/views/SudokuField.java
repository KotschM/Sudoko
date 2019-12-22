package mocomabe.views;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 * In this class the grid for the Sudoku will be generated. This works by
 * creating a big JPanel, then several smaller panels (dependig on the size of the Sudoku) are created and placed in the big
 * panel. In this smaller panels the JTextFields are placed. In each small panel
 * we have the same ammount of JTextFields.
 * 
 * @author Benita Dietrich
 */
public class SudokuField extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField[][] fields;
    private JPanel bigSquare;
    private JPanel[][] smallSquare;
    private int smallHeight, smallWidth;
    private Dimension dimension;

    public SudokuField(int sudokuSize) {


        //Setting
        this.fields = new JTextField[sudokuSize][sudokuSize];
        this.setOpaque(false);

        // Creating different border styles
        Border borderSmall = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border borderThick = BorderFactory.createLineBorder(Color.BLACK, 2);

        // Font styles
        Font fontSmallGrid = new Font("Calibri", Font.CENTER_BASELINE, 36);
        Font fontBigGrid = new Font("Calibri", Font.CENTER_BASELINE, 24);
        Font fontSmallerGrid = new Font("Calibri", Font.CENTER_BASELINE, 18);

        //Dimension for JTextFields depending on how big the Sudoku is
        if (sudokuSize<=10){
            dimension = new Dimension(80, 80);
        } else if(sudokuSize<=15){
            dimension = new Dimension(50,50);
        } else if (sudokuSize<=20){
            dimension = new Dimension(40,40);
        } else {
            dimension = new Dimension(25,25);
        }

        // Setting layout and design of the JTextFields and saving them in the 2D-Array
        // fields[][]
        for (int y = 0; y < sudokuSize; ++y) {
            for (int x = 0; x < sudokuSize; ++x) {
                JTextField jtf = new JTextField();
                jtf.setBorder(borderSmall);
                if(sudokuSize<=10){
                    jtf.setFont(fontSmallGrid);
                } else if(sudokuSize<=20){
                    jtf.setFont(fontBigGrid);
                } else {
                    jtf.setFont(fontSmallerGrid);
                }
                jtf.setHorizontalAlignment(JTextField.CENTER);
                jtf.setPreferredSize(dimension);
                fields[y][x] = jtf;
            }
        }
        // Calculating the size of the smaller panels
        if (Math.pow(Math.sqrt(sudokuSize), 2) == sudokuSize) { //if the sudokuSize has an integer root
            smallHeight = (int) (Math.sqrt(sudokuSize));
            smallWidth = (int) (Math.sqrt(sudokuSize));
        } else {
            smallHeight = (int) (Math.sqrt(sudokuSize));
            smallWidth = (int) (Math.sqrt(sudokuSize) + 1);
        }
        // Setting layout of the big panel
        this.bigSquare = new JPanel(new GridLayout(smallWidth, smallHeight));

        // Initializing the small panel array
        this.smallSquare = new JPanel[smallHeight][smallWidth];

        /* Creating the smaller panels and storing them in the 2D Array smallSquare
         * Each Panel gets a GridLayout to later add the TextFields
         */
        for (int y = 0; y < smallHeight; ++y) {
            for (int x = 0; x < smallWidth; ++x) {
                JPanel jpa = new JPanel();
                jpa.setLayout(new GridLayout(smallHeight, smallWidth));
                jpa.setBorder(borderSmall);
                smallSquare[y][x] = jpa;
                bigSquare.add(jpa); // adding the small panel to the big panel
            }
        }

        // Adding the TextFields to the small panels
        for (int y = 0; y < sudokuSize; ++y) {
            for (int x = 0; x < sudokuSize; ++x) {
                int squareX = x / smallHeight;
                int squareY = y / smallWidth;   //calculation to correctly add the text fields
                smallSquare[squareY][squareX].add(fields[y][x]);
            }
        }

        this.bigSquare.setBorder(borderThick);
        this.add(bigSquare);
    }

    /** Gives back the JTextFieldArray 
     * 
     * @return JTextField[][]
     */
    public JTextField[][] getFields() {
        return fields;
    }

}