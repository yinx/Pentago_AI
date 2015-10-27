/**
 * Created by tombe on 27/10/2015.
 */
public class BoardController {
    private Board board;

    private static final int AMOUNT_OF_TOKENS = 2;

    public BoardController(int rows, int cols) {
        board = new Board(rows, cols);
    }

    public void paintBoard() {
        board.paint();
    }

    public void setShiveType(int row, int col, TokenType shiveType) {
        board.setShiveType(row, col, shiveType);
    }

    public TokenType checkEveryCell4Win() {
        TokenType tokenType = TokenType.Empty;

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                if (!board.getShiveType(row,col).equals(TokenType.Empty)) {
                    if (check4Win(row, col)) {
                        if (board.getShiveType(row,col).getColour().equals(TokenType.BLACK.getColour())) {
                            tokenType = TokenType.BLACK;
                        } else{
                            tokenType = TokenType.WHITE;
                        }

                    } else {
                        tokenType = TokenType.Empty;
                    }
                }
            }
        }


        return tokenType;
    }

    public boolean check4Win(int row, int col) {
        return (checkVertically(row, col, AMOUNT_OF_TOKENS) || checkHorizontally(row, col, AMOUNT_OF_TOKENS)
                || checkDiagonally1(row, col, AMOUNT_OF_TOKENS)
                || checkDiagonally2(row, col, AMOUNT_OF_TOKENS));

    }

    //for checking nrOfTokens (win situation: nrOfTokens = 5)
    private boolean checkDiagonally1(int row, int col, int nrOfTokens) {
        for (int j = 0; j < nrOfTokens; j++) {
            int adjacentSameTokens = 0;
            for (int i = 0; i < nrOfTokens; i++) {
                if ((col + i - j) >= 0 && (col + i - j) < board.getColumns()
                        && (row + i - j) >= 0 && (row + i - j) < board.getRows()
                        && board.getPlayerOfTokenAt(row + i - j, col + i - j).equals(board.getPlayerOfTokenAt(row, col)) && !board.getPlayerOfTokenAt(row, col).equals(TokenType.Empty.getColour())) {
                    adjacentSameTokens++;
                }
            }
            if (adjacentSameTokens >= nrOfTokens)
                return true;
        }
        return false;
    }

    private boolean checkDiagonally2(int row, int col, int nrOfTokens) {
        for (int j = 0; j < nrOfTokens; j++) {
            int adjacentSameTokens = 0;
            for (int i = 0; i < nrOfTokens; i++) {
                if ((col - i + j) >= 0 && (col - i + j) < board.getColumns()
                        && (row + i - j) >= 0 && (row + i - j) < board.getRows()
                        && board.getPlayerOfTokenAt(row + i - j, col - i + j).equals(board.getPlayerOfTokenAt(row, col)) && !board.getPlayerOfTokenAt(row, col).equals(TokenType.Empty.getColour())) {
                    adjacentSameTokens++;
                }
            }
            if (adjacentSameTokens >= nrOfTokens)
                return true;
        }
        return false;
    }

    private boolean checkHorizontally(int row, int col, int nrOfTokens) {
        int adjacentSameTokens = 1;
        int i = 1;
        while (col - i >= 0 && board.getPlayerOfTokenAt(row, col - i).equals(board.getPlayerOfTokenAt(row, col)) && !board.getPlayerOfTokenAt(row, col).equals(TokenType.Empty.getColour())) {
            adjacentSameTokens++;
            i++;
        }
        i = 1;
        while (col + i < board.getColumns() && board.getPlayerOfTokenAt(row, col + i).equals(board.getPlayerOfTokenAt(row, col)) && !board.getPlayerOfTokenAt(row, col).equals(TokenType.Empty.getColour())) {
            adjacentSameTokens++;
            i++;
        }
        return (adjacentSameTokens >= nrOfTokens);
    }

    private boolean checkVertically(int row, int col, int nrOfTokens) {
        int adjacentSameTokens = 1;
        int i = 1;
        while (row + i < board.getRows() && board.getPlayerOfTokenAt(row + i, col).equals(board.getPlayerOfTokenAt(row, col)) && !board.getPlayerOfTokenAt(row, col).equals(TokenType.Empty.getColour())) {
            adjacentSameTokens++;
            i++;
        }
        while (row - i > 0 && board.getPlayerOfTokenAt(row - i, col).equals(board.getPlayerOfTokenAt(row, col)) && !board.getPlayerOfTokenAt(row, col).equals(TokenType.Empty.getColour())) {
            adjacentSameTokens++;
            i++;
        }
        return (adjacentSameTokens >= nrOfTokens);
    }

    public void turnBoard(int chosenPart, boolean direction) {
        board.turnBoard(chosenPart, direction);
    }

    public int getColumns() {
        return board.getColumns();
    }

    public TokenType getShiveType(int selectedRow, int selectedCol) {
        return board.getShiveType(selectedRow, selectedCol);
    }
}
