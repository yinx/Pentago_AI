package View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tom on 13/10/2015.
 */
public class GUI extends JFrame {
    private JLabel[][] cells;
    private JPanel boardPanel;

    public GUI() {
        makeComponents();
        makeLayout();
        showFrame();
    }

    private void makeComponents() {

        boardPanel = new JPanel();
        cells = new JLabel[6][6];

    }

    private void makeLayout() {
        boardPanel.setLayout(new GridLayout(6,6));

        for (int i =0; i < 6; i++) {
            for (int j=0; j < 6; j++) {
                cells[i][j] = new JLabel();
                boardPanel.add(cells[i][j]);
                cells[i][j].setPreferredSize(new Dimension(100,100));
                cells[i][j].setBackground(Color.RED);
                cells[i][j].setOpaque(true);

                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            }
        }


        this.add(boardPanel);

    }

    private void showFrame(){
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600,600);
    }
}
