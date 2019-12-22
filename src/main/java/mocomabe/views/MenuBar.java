package mocomabe.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import mocomabe.controllers.*;
import mocomabe.models.*;

/**
 * Creates a JMenuBar with the different items where settings can be made. One
 * can choose between the size of the sudoku grid, the level of difficulty and
 * also between the game mode.
 * 
 * @author Benita Dietrich
 */

public class MenuBar extends JMenuBar {

    private static final long serialVersionUID = 1L;
    private JMenu sizeMenu, difficultyMenu;
    private Color hover;
    int currentSize = 9;
    MainController c;

    MenuBar(MainController controller) {

        super();

        c = controller;
        // create design elements
        hover = new Color(104, 215, 234);
        Font menu = new Font("Arial", Font.PLAIN, 14);
        Border border = BorderFactory.createEmptyBorder();

        // Set default selection colors
        UIDefaults uiDefaults = UIManager.getDefaults();
        uiDefaults.put("Menu.selectionBackground", new javax.swing.plaf.ColorUIResource(hover));
        uiDefaults.put("RadioButtonMenuItem.selectionBackground", new javax.swing.plaf.ColorUIResource(hover));

        // Settings
        setBackground(Color.WHITE);
        setOpaque(true);
        setBorder(border);
        setFont(menu);

        // Creating a new Menu
        sizeMenu = new JMenu("Spielfeldgröße");
        sizeMenu.setOpaque(true);
        sizeMenu.setBackground(Color.WHITE);
        sizeMenu.setFont(menu);

        // Adding the items to a button group so only one size can be chosen
        ButtonGroup bgSize = new ButtonGroup();

        /*
         * Creating the different MenuItems as RadioButtons so the user knows which size
         * onw has chosen This happens by adding the sizes as integers to an int array
         * for each element a JRadioButtonMenuItem is created
         */
        int[] sizes = new int[] {4, 9, 16, 25};
        for (int sizeInt : sizes) {
            JRadioButtonMenuItem but = new JRadioButtonMenuItem(String.format("%d x %d Sudoku", sizeInt, sizeInt));
            but.setBackground(Color.WHITE);
            but.setFont(menu);
            but.addActionListener(new MenuListener());
            but.setActionCommand(String.format("SIZE%d", sizeInt)); // setting ActionCommand so it is later easier to
                                                                    // change the size of the Sudoku grid
            if (sizeInt == 9) {
                but.setSelected(true);
                c.setSize(sizeInt);
            }
            bgSize.add(but);
            sizeMenu.add(but);
        }

        // Adding the menu to the menubar
        add(sizeMenu);

        // Creating a new menu for the difficulty
        difficultyMenu = new JMenu("Schwierigkeitsgrad");
        difficultyMenu.setOpaque(true);
        difficultyMenu.setBackground(Color.WHITE);
        difficultyMenu.setFont(menu);

        // Creating the different MenuItems as RadioButtons so the user knows which
        // difficulty he has chosen
        String[] difficulties = new String[] { "Mauerblümchen", "Beginner", "Mittel", "Schwer", "Teuflisch" };
        ButtonGroup bgDiff = new ButtonGroup();
        for (String difficultyString : difficulties) {
            JRadioButtonMenuItem but = new JRadioButtonMenuItem(difficultyString);
            but.setFont(menu);
            but.addActionListener(new MenuListener());
            but.setActionCommand(String.format("DIFFICULTY%s", difficultyString));
            bgDiff.add(but);
            if (difficultyString.equals("Mittel")) {
                but.setSelected(true);
                c.setDifficulty(Difficulty.Medium);
            }
            difficultyMenu.add(but);
        }


        // Adding the menu to the menubar
        add(difficultyMenu);

    }

    /**
     * This class decides what is supposed to happen when you click on a
     * RadioButtonItem
     */
    private class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = ((JRadioButtonMenuItem) e.getSource()).getActionCommand();
            if (cmd.startsWith("SIZE")) {
                c.setSize(Integer.parseInt(cmd.substring(4)));
            } else if (cmd.startsWith("DIFFICULTY")) {
                String temp = cmd.substring(10);
                switch (temp) {
                case "Mauerblümchen":
                    c.setDifficulty(Difficulty.Wallflower);
                    break;

                case "Beginner":
                    c.setDifficulty(Difficulty.Starter);
                    break;

                case "Mittel":
                    c.setDifficulty(Difficulty.Medium);
                    break;

                case "Schwer":
                    c.setDifficulty(Difficulty.Hard);
                    break;

                case "Teuflisch":
                    c.setDifficulty(Difficulty.Devilish);
                    break;
                }
            }
        }
    }
}