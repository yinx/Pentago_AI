import Model.*;
import View.Board;

import java.util.Scanner;

/**
 * Created by Tom on 24/09/2015.
 */
public class ConsoleRun {
    private static Scanner input = new Scanner(System.in);
    private BoardController boardController;
    private TokenType currentPlayer;
    private GameState gameState;
    private int lastSetRow;
    private int lastSetCol;

    public ConsoleRun() {
        boardController = new BoardController(6, 6);

        initGame();
        boardController.paintBoard();
        do {
            doPlayerMove(currentPlayer);

            AiPlayer aiBlack = new AiPlayer(TokenType.BLACK);
            BoardState boardState = aiBlack.minimax(2, new BoardState(boardController, TokenType.BLACK, null), true);
            boardController.setBoard(((BoardState)boardState.getParent()).getBoard());
            System.out.println("Black AI plays!");
            boardController.paintBoard();

            //currentPlayer = (currentPlayer == TokenType.BLACK) ? TokenType.WHITE : TokenType.BLACK;

        } while (gameState == GameState.PLAYING);
        if (gameState == GameState.BLACK_WON) {
            System.out.println("Black WINS!");
        } else if (gameState == GameState.WHITE_WON) {
            System.out.println("White WINS!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public void initGame() {
        currentPlayer = TokenType.WHITE;
        gameState = GameState.PLAYING;
    }

    public void doPlayerMove(TokenType currentPlayer) {
        boolean validInput = false;
        do {
            if (currentPlayer == TokenType.BLACK) {
                System.out.print("Black player, enter your move (column 1-6) (row 1-6) (vb 3,5): ");
            } else {
                System.out.print("White player, enter your move (column 1-6) (row 1-6) (vb 3,5): ");
            }
            String in = input.nextLine();
            int selectedCol = Integer.parseInt(in.split(",")[0]) - 1;
            int selectedRow = Integer.parseInt(in.split(",")[1]) - 1;
            if (selectedCol >= 0 && selectedCol < boardController.getColumns() && selectedRow >= 0 && selectedRow < boardController.getColumns()) {

                if (boardController.getShiveType(selectedRow, selectedCol) == TokenType.Empty) {
                    boardController.setShiveType(selectedRow, selectedCol, currentPlayer);
                    validInput = true;
                    lastSetCol = selectedCol;
                    lastSetRow = selectedRow;

                    break;
                }


            } else {
                System.out.println("This move is invalid");
            }
        } while (!validInput);

        boardController.paintBoard();
        int keuze;
        do {
            System.out.print("\nWelk deel van het bord wil je draaien? (1-4)");
            keuze = input.nextInt();
            if (keuze > 0 && keuze < 5) {
                System.out.print("In welke richting wilt u het bord draaien? (l of r)");
                input = new Scanner(System.in);
                String richting = input.nextLine();
                switch (richting.charAt(0)) {
                    case 'l':
                        boardController.turnBoard(keuze, false);
                        break;
                    case 'r':
                        boardController.turnBoard(keuze, true);
                        break;
                }
            } else {
                System.out.println("Verkeerde keuze");
            }
        } while (keuze <= 0 && keuze >= 5);
        boardController.paintBoard();


        switch (boardController.checkEveryCell4Win()) {
            case BLACK:
                gameState = GameState.BLACK_WON;
                break;
            case WHITE:
                gameState = GameState.WHITE_WON;
                break;
            case Empty:
                gameState = GameState.PLAYING;
                break;
        }
    }

    public static void main(String[] args) {
        new ConsoleRun();
    }
}
