package mocomabe.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import mocomabe.controllers.MainController;
import mocomabe.controllers.MainControllerListener;

/** This class creates the button panel below the grid
 *  
 *  @author Benita Dietrich
 */

public class ButtonField extends JPanel{

    private static final long serialVersionUID = 1L;
    private JButton resolve, generate;
    private Font fontButton = new Font("Arial",Font.CENTER_BASELINE,15);
    private Dimension dimButt = new Dimension(200, 50);

    ButtonField(MainController c){
        this.setLayout(new FlowLayout());
        this.setOpaque(false);  //Opaque false so the background image will be visible
        c.addMainControllerListener(new MainControllerListener(){
        
            @Override
            public void sudokuFieldSolved() {
               
            }
        
            @Override
            public void sudokuFieldChanged(int x, int y, int value) {
                
            }
        
            @Override
            public void changeSudokuFieldOptions() {
                resolve.setEnabled(true);
            }
        });
        //Button settings
        resolve = new JButton("Lösen");
        resolve.setPreferredSize(dimButt);
        resolve.setFont(fontButton);
        resolve.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                c.solveSudoku();
                resolve.setEnabled(false);
            }
        });

        generate = new JButton ("Neues Sudoku");
        generate.setPreferredSize(dimButt);
        generate.setFont(fontButton);
        generate.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                c.createNewSudoku();
                resolve.setEnabled(true);
            }
        });
        
        //Adding the buttons
        this.add(resolve);
        this.add(generate);


    }
}