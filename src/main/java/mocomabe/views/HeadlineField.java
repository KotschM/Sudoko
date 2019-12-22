package mocomabe.views;

import java.awt.Font;
import javax.swing.*;

/** This class creates a headline panel
 * 
 * @author Benita Dietrich
 */

public class HeadlineField extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel headline;
    private Font headlineFont = new Font("Monotype Corsiva", Font.BOLD, 60);

    public HeadlineField() {

        this.setOpaque(false);

        //Anpassen der Überschrift
        headline = new JLabel("Sudoku");
        headline.setFont(headlineFont);

        add(headline);


    }
}