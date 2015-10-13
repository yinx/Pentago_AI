import java.util.Scanner;

/**
 * Created by Tom on 24/09/2015.
 */
public class Run {
    private static Scanner input = new Scanner(System.in);
    private Board board;
    private TokenType currentPlayer;
    private GameState gameState;
    private int lastSetRow;
    private int lastSetCol;

    public Run() {
        board = new Board(6, 6);

        initGame();
        board.paint();
        do {
            doPlayerMove(currentPlayer);

            currentPlayer = (currentPlayer == TokenType.BLACK) ? TokenType.WHITE : TokenType.BLACK;

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
        currentPlayer = TokenType.BLACK;
        gameState = GameState.PLAYING;
    }

    public void doPlayerMove(TokenType currentPlayer) {
        boolean validInput = false;
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
                        board.turnBoard(keuze, false);
                        break;
                    case 'r':
                        board.turnBoard(keuze, true);
                        break;
                }
            } else {
                System.out.println("Verkeerde keuze");
            }
        } while (keuze <= 0 && keuze >= 5);
        board.paint();

        do {
            if (currentPlayer == TokenType.BLACK) {
                System.out.print("Black player, enter your move (column 1-6) (row 1-6) (vb 3,5): ");
            } else {
                System.out.print("White player, enter your move (column 1-6) (row 1-6) (vb 3,5): ");
            }
            String in = input.nextLine();
            int selectedCol = Integer.parseInt(in.split(",")[0]) - 1;
            int selectedRow = Integer.parseInt(in.split(",")[1]) - 1;
            if (selectedCol >= 0 && selectedCol < board.getColumns() && selectedRow >= 0 && selectedRow < board.getColumns()) {

                if (board.getShiveType(selectedRow, selectedCol) == TokenType.Empty) {
                    board.setShiveType(selectedRow, selectedCol, currentPlayer);
                    validInput = true;
                    lastSetCol = selectedCol;
                    lastSetRow = selectedRow;

                    break;
                }


            } else {
                System.out.println("This move is invalid");
            }
        } while (!validInput);
        board.paint();
        if (board.check4Win(lastSetRow,lastSetCol)) {
            if (currentPlayer == TokenType.WHITE) gameState = GameState.WHITE_WON;
            else if (currentPlayer == TokenType.BLACK) gameState = GameState.BLACK_WON;
        }
    }

    public static void main(String[] args) {
        new Run();
    }
}
