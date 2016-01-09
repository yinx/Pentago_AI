package View;

import Model.*;
import Model.Button;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by Tom on 13/10/2015.
 */
public class GUI extends JFrame implements IBoardListener {
    private BoardController boardController;
    private JLabel[][] cells;
    private JPanel boardPanel;
    private JPanel framePanel;
    private JMenuBar mbMenu;
    private JMenu mnSpel;
    private JMenu mnHelp;
    private JMenuItem mniNieuw;
    private JMenuItem mniOpenFromFile;
    private JMenuItem mniInfo;
    private JMenuItem mniSpelRegels;
    private FileReader fileReader;
    private JButton btn1Left;
    private JButton btn1Right;
    private JButton btn2Left;
    private JButton btn2Right;
    private JButton btn3Left;
    private JButton btn3Right;
    private JButton btn4Left;
    private JButton btn4Right;
    private AiPlayer aiPlayer;
    private int depth = 2;

    //false = black || true = white
    private boolean currentPlayer = true;

    private boolean buttonClicked = false;
    private boolean labelClicked = false;

    private final JFileChooser jfChooser = new JFileChooser();

    public GUI(BoardController boardController, FileReader fileReader,AiPlayer aiPlayer) {
        this.boardController = boardController;
        this.fileReader = fileReader;
        this.aiPlayer = aiPlayer;
        makeComponents();
        makeLayout();
        showFrame();
        makeListeners();
    }

    private void makeComponents() {

        boardPanel = new JPanel();
        framePanel = new JPanel();
        cells = new JLabel[boardController.getRows()][boardController.getColumns()];

        mbMenu = new JMenuBar();
        mnSpel = new JMenu("Spel");
        mnHelp = new JMenu("Help");
        mniNieuw = new JMenuItem("Nieuw");
        mniOpenFromFile = new JMenuItem("Open");
        mniSpelRegels = new JMenuItem("SpelRegels");
        mniInfo = new JMenuItem("Info");

        btn1Left = new Button("btn1Left", false, 1);
        btn1Right = new Button("btn1Right", true, 1);
        btn2Left = new Button("btn2Left", false, 2);
        btn2Right = new Button("btn2Right", true, 2);
        btn3Left = new Button("btn3Left", false, 3);
        btn3Right = new Button("btn3Right", true, 3);
        btn4Left = new Button("btn4Left", false, 4);
        btn4Right = new Button("btn4Right", true, 4);

    }

    private void makeLayout() {

        framePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        boardPanel.setLayout(new GridLayout(6, 6));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        framePanel.add(boardPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        framePanel.add(btn1Left, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        framePanel.add(btn1Right, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        framePanel.add(btn2Left, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        framePanel.add(btn2Right, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        framePanel.add(btn3Left, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        framePanel.add(btn3Right, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        framePanel.add(btn4Left, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        framePanel.add(btn4Right, gbc);

        setJMenuBar(mbMenu);

        mbMenu.add(mnSpel);
        mbMenu.add(mnHelp);

        mnSpel.add(mniNieuw);
        mnSpel.add(mniOpenFromFile);
        mnHelp.add(mniSpelRegels);
        mnHelp.add(mniInfo);

        paintBoard();

        this.add(framePanel);


    }

    private void paintBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                cells[i][j] = new JLabel();
                boardPanel.add(cells[i][j]);
                cells[i][j].setPreferredSize(new Dimension(100, 100));
                setEachCellBackground(i, j);
                cells[i][j].setOpaque(true);

                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        }
    }

    private void setEachCellBackground(int row, int col) {


        if (boardController.getShiveType(row, col).equals(TokenType.Empty)) {
            cells[row][col].setBackground(Color.RED);
        } else if (boardController.getShiveType(row, col).equals(TokenType.BLACK)) {
            cells[row][col].setBackground(Color.BLACK);
        } else {
            cells[row][col].setBackground(Color.WHITE);
        }


    }

    private void showFrame() {
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 600);
    }

    private void makeListeners() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                cells[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!labelClicked) {
                            for (int i = 0; i < 6; i++) {
                                for (int j = 0; j < 6; j++) {
                                    if (e.getSource() == cells[i][j]) {
                                        onLabelClicked(i, j);
                                    }
                                }

                            }
                        }
                    }
                });
            }
        }

        mniOpenFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfChooser.setCurrentDirectory(new File("./files"));
                int returnVal = jfChooser.showOpenDialog(GUI.this);

                if (returnVal == jfChooser.APPROVE_OPTION) {
                    File file = jfChooser.getSelectedFile();
                    fileReader.readFile(file);

                }

            }
        });

        btn1Left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Button button = (Button) e.getSource();
                onButtonClicked(button);
            }
        });

        btn2Left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Button button = (Button) e.getSource();
                onButtonClicked(button);
            }
        });

        btn3Left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Button button = (Button) e.getSource();
                onButtonClicked(button);
            }
        });

        btn4Left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Button button = (Button) e.getSource();
                onButtonClicked(button);
            }
        });

        btn1Right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Button button = (Button) e.getSource();
                onButtonClicked(button);
            }
        });

        btn2Right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Button button = (Button) e.getSource();
                onButtonClicked(button);
            }
        });

        btn3Right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Button button = (Button) e.getSource();
                onButtonClicked(button);
            }
        });

        btn4Right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Button button = (Button) e.getSource();
                onButtonClicked(button);

            }
        });
    }

    private void onButtonClicked(Button button) {
        if(!buttonClicked) {
            labelClicked = false;
        /*if (!currentPlayer) {
            currentPlayer = true;
            boardController.turnBoard(button.getPart(), button.isDirection());
        } else {
            currentPlayer = false;
            boardController.turnBoard(button.getPart(), button.isDirection());
        }*/

            boardController.turnBoard(button.getPart(), button.isDirection());

            TokenType colour = boardController.checkEveryCell4Win(boardController.getBoard());
            buttonClicked = true;
            if (!colour.equals(TokenType.Empty)) {
                int reply = JOptionPane.showConfirmDialog(this, colour + " heeft gewonnen, nog een keer?");

                if (reply == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }

            BoardState boardState = aiPlayer.minimax(depth, new BoardState(boardController, TokenType.BLACK, null), true);
            Board result;
            result = ((BoardState)boardState.getHeuristicBoard().getParent()).getBoard();
            boardController.setBoard(result);
            updateBoard();

            colour = boardController.checkEveryCell4Win(boardController.getBoard());
            buttonClicked = true;
            if (!colour.equals(TokenType.Empty)) {
                int reply = JOptionPane.showConfirmDialog(this, colour + " heeft gewonnen, nog een keer?");

                if (reply == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        }
    }

    private void onLabelClicked(int i, int j) {
        if (boardController.getShiveType(i, j).equals(TokenType.Empty)) {
            if (!currentPlayer) {
                boardController.setShiveType(i, j, TokenType.BLACK);
                labelClicked = true;
                buttonClicked = false;

            } else {
                boardController.setShiveType(i, j, TokenType.WHITE);
                labelClicked = true;
                buttonClicked = false;
            }
        }
    }


    @Override
    public void updateBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                setEachCellBackground(i, j);
            }
        }
    }
}
