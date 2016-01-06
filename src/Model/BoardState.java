package Model;

import View.Board;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

/**
 * Created by Tom on 13/10/2015.
 */
public class BoardState extends DefaultMutableTreeNode{
    private Board board;
    private BoardState heuristic;
    private double heuristicValue;
    private BoardController controller;
    private double beta = Double.POSITIVE_INFINITY;
    private double alpha = Double.NEGATIVE_INFINITY;

    public BoardState(BoardController controller,TokenType player,BoardState heuristic) {
        this.board= controller.getBoard();
        if(heuristic==null)this.heuristic = this;
        else this.heuristic=heuristic;
        this.controller = controller;
    }

    public BoardState(BoardState boardState) {
        this.board = new Board(boardState.getBoard());
        this.heuristic= boardState.getHeuristicBoard();
        this.controller = boardState.controller;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setHeuristicBoard(BoardState heuristic) {
        this.heuristic = heuristic;
    }

    public BoardState getHeuristicBoard() {
        return heuristic;
    }

    public double getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(double heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    public double calculateBoardValue(TokenType player) {
        double value = 0.0;
        ArrayList<String> lines = new ArrayList<>();
        for(int i = 0;i<6;i++) {
            StringBuilder line = new StringBuilder();
            StringBuilder line2 = new StringBuilder();
            for(int j = 0;j<6;j++) {
                if(board.getPlayerOfTokenAt(i,j).equals(" ")){
                    line.append('_');
                }else{
                    line.append(board.getPlayerOfTokenAt(i,j));
                }
                if(board.getPlayerOfTokenAt(j,i).equals(" ")){
                    line2.append('_');
                }else{
                    line2.append(board.getPlayerOfTokenAt(j,i));
                }
            }
            if (!lines.contains(line.toString())) lines.add(line.toString());
            if (!lines.contains(line2.toString())) lines.add(line2.toString());
        }

        lines.add(getDiagonal1(1,0).toString());
        lines.add(getDiagonal1(0,1).toString());
        lines.add(getDiagonal2(4, 0).toString());
        lines.add(getDiagonal2(5, 0).toString());
        lines.add(getDiagonal2(5, 1).toString());

        for (String line : lines) {
            value += calculateLineValue(player,line.toLowerCase());
        }
        return value;
    }

    private StringBuilder getDiagonal1(int row,int col){
        StringBuilder diagonalLines = new StringBuilder();
        do {
            if (board.getPlayerOfTokenAt(row, col).equals(" ")) {
                diagonalLines.append('_');
            } else {
                diagonalLines.append(board.getPlayerOfTokenAt(row, col));
            }
            col++;
            row++;
        }while(col<6&&row<6);
        return diagonalLines;
    }

    private StringBuilder getDiagonal2(int row, int col){
        StringBuilder diagonalLines = new StringBuilder();
        do{
            if (board.getPlayerOfTokenAt(row, col).equals(" ")) {
                diagonalLines.append('_');
            } else {
                diagonalLines.append(board.getPlayerOfTokenAt(row, col));
            }
            col++;
            row--;
        }while(col<6&&row>=0);
        return diagonalLines;
    }

    public boolean setTokenOnBoard(TokenType player,int row,int col){
        if(board.getShiveType(row,col)==TokenType.Empty){
            board.setShiveType(row,col,player);
            return true;
        }else{
            return false;
        }
    }

    public void turnBoard(int chosenPart, boolean direction){
        board.turnBoard(chosenPart, direction);
    }

    public double calculateLineValue(TokenType player,String line){
        double value=0;
        if(line.matches("(bb____|____bb)")){
            if(player==TokenType.BLACK) {
                value += 5;
            }else value-=5;
        }else if(line.matches("(bbb___|___bbb)")){
            if(player==TokenType.BLACK) {
                value += 10;
            }else value-=10;
        }else if(line.matches("(bbbb__|__bbbb)")){
            if(player==TokenType.BLACK) {
                value += 45;
            }else value-=45;
        }else if(line.matches("(bbbbb_|_bbbbb)")){
            if(player==TokenType.BLACK) {
                return Double.POSITIVE_INFINITY;
            }else return Double.NEGATIVE_INFINITY;
        }else if(line.matches("(_bbb__|__bbb_)")){
            if(player==TokenType.BLACK) {
                value += 12;
            }else value-=12;
        }else if(line.matches("(wbbb__|__bbbw)")){
            if(player==TokenType.BLACK) {
                value += 10;
            }else value-=10;
        }else if(line.matches("(w_bbb_|_bbb_w)")){
            if(player==TokenType.BLACK) {
                value += 12;
            }else value-=12;
        }else if(line.matches("(__bb__)")){
            if(player==TokenType.BLACK) {
                value += 8;
            }else value-=8;
        }else if(line.matches("(_bbbb_)")){
            if(player==TokenType.BLACK) {
                value += 50;
            }else value-=50;
        }else if(line.matches("(wbbbb_|_bbbbw)")){
            if(player==TokenType.BLACK) {
                value += 45;
            }else value-=45;
        }else if(line.matches("(_bb___|___bb_)")){
            if(player==TokenType.BLACK) {
                value += 7;
            }else value-=7;
        }else if(line.matches("(w__bb_|_bb__w)")){
            if(player==TokenType.BLACK) {
                value += 7;
            }else value-=7;
        }else if(line.matches(".*(wb)*.")){
            value -= 1;
        }else if(line.matches(".*(b).*")){
            if(player==TokenType.BLACK) {
                value += 1;
            }else value-=1;
        }else if(line.matches(".*(bb).*")){
            if(player==TokenType.BLACK) {
                value += 5;
            }else value-=5;
        }else if(line.matches(".*(bbb).*")){
            if(player==TokenType.BLACK) {
                value += 10;
            }else value-=10;
        }else if(line.matches(".*(bbbb).*")){
            if(player==TokenType.BLACK) {
                value += 50;
            }else value-=50;
        }else if(line.matches(".*(bbbbb).*")){
            if(player==TokenType.BLACK) {
                return Double.POSITIVE_INFINITY;
            }else return Double.NEGATIVE_INFINITY;
        }else if(line.matches("(ww____|____ww)")){
            if(player==TokenType.WHITE) {
                value += 5;
            }else value-=5;
        }else if(line.matches("(www___|___www)")){
            if(player==TokenType.WHITE) {
                value += 10;
            }else value-=10;
        }else if(line.matches("(wwww__|__wwww)")){
            if(player==TokenType.WHITE) {
                value += 45;
            }else value-=45;
        }else if(line.matches("(wwwww_|_wwwww)")){
            if(player==TokenType.WHITE) {
                return Double.NEGATIVE_INFINITY;
            }else return Double.POSITIVE_INFINITY;
        }else if(line.matches("(_www__|__www_)")){
            if(player==TokenType.WHITE) {
                value += 12;
            }else value-=12;
        }else if(line.matches("(bwww__|__wwwb)")){
            if(player==TokenType.WHITE) {
                value += 10;
            }else value-=10;
        }else if(line.matches("(b_www_|_www_b)")){
            if(player==TokenType.WHITE) {
                value += 12;
            }else value-=12;
        }else if(line.matches("(__ww__)")){
            if(player==TokenType.WHITE) {
                value += 8;
            }else value-=8;
        }else if(line.matches("(_wwww_)")){
            if(player==TokenType.WHITE) {
                value += 50;
            }else value-=50;
        }else if(line.matches("(bwwww_|_wwwwb)")){
            if(player==TokenType.WHITE) {
                value += 45;
            }else value-=45;
        }else if(line.matches("(_ww___|___ww_)")){
            if(player==TokenType.WHITE) {
                value += 7;
            }else value-=7;
        }else if(line.matches("(b__ww_|_ww__b)")){
            if(player==TokenType.WHITE) {
                value += 7;
            }else value-=7;
        }else if(line.matches("(b_ww__|__ww_b)")){
            if(player==TokenType.WHITE) {
                value += 7;
            }else value-=7;
        }else if(line.matches(".*(w).*")){
            if(player==TokenType.WHITE) {
                value += 1;
            }else value-=1;
        }else if(line.matches(".*(ww).*")){
            if(player==TokenType.WHITE) {
                value += 5;
            }else value-=5;
        }else if(line.matches(".*(www).*")){
            if(player==TokenType.WHITE) {
                value += 10;
            }else value-=10;
        }else if(line.matches(".*(wwww).*")){
            if(player==TokenType.WHITE) {
                value += 50;
            }else value-=50;
        }else if(line.matches(".*(wwwww).*")) {
            if (player == TokenType.WHITE) {
                return Double.NEGATIVE_INFINITY;
            } else return Double.POSITIVE_INFINITY;
        }
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.board.equals(((BoardState)obj).board)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return board.toString()+" HValue = "+heuristicValue;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getAlpha() {
        return alpha;
    }
}

