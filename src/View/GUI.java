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
        boardPanel.setSize(100,100);
        boardPanel.setMaximumSize(new Dimension(600,600));
        cells = new JLabel[6][6];

    }

    private void makeLayout() {
        boardPanel.setLayout(new GridLayout(6,6));

        ImageIcon imageIcon = new ImageIcon ("C:\\Users\\Tom\\Documents\\KDG\\IAO301\\Artificiel Intelligence\\GitPentagoAI\\Pentago_AI\\images\\cell.png");
        for (int i =0; i < 6; i++) {
            for (int j=0; j < 6; j++) {
                cells[i][j] = new JLabel(imageIcon);
                boardPanel.add(cells[i][j]);
                cells[i][j].setSize(100,100);



            }
        }



        add(boardPanel, BorderLayout.CENTER);
    }

    private void showFrame(){
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800,800);
    }
}
