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

    public BoardState(BoardController controller,TokenType player,BoardState heuristic) {
        this.board= controller.getBoard();
        this.heuristic = heuristic;
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

    public void calculateBoardValue(TokenType player) {
        if(controller.checkEveryCell4Win()==player){
            heuristicValue = Double.POSITIVE_INFINITY;
        }else if(controller.checkEveryCell4Win()!=TokenType.Empty){
            heuristicValue = Double.NEGATIVE_INFINITY;
        }
        double value = 0.0;
        ArrayList<String> lines = new ArrayList<>();
        for(int i = 0;i<6;i++) {
            StringBuilder line = new StringBuilder();
            for(int j = 0;j<6;j++) {
                if(board.getPlayerOfTokenAt(i,j).equals(" ")){
                    line.append('_');
                }else{
                    line.append(board.getPlayerOfTokenAt(i,j));
                }
            }
            lines.add(line.toString());
        }
        for(int i = 0;i<6;i++) {
            StringBuilder line = new StringBuilder();
            for(int j = 0;j<6;j++) {
                if(board.getPlayerOfTokenAt(j,i).equals(" ")){
                    line.append('_');
                }else{
                    line.append(board.getPlayerOfTokenAt(j,i));
                }
            }
            lines.add(line.toString());
        }

        lines.add(getDiagonal1(0,0).toString());
        lines.add(getDiagonal1(1,0).toString());
        lines.add(getDiagonal1(0,1).toString());
        lines.add(getDiagonal2(0, 4).toString());
        lines.add(getDiagonal2(0, 5).toString());
        lines.add(getDiagonal2(1, 5).toString());

        for (String line : lines) {
            value += calculateLineValue(player,line.toLowerCase());
        }
        heuristicValue = value;
    }

    private StringBuilder getDiagonal1(int col,int row){
        StringBuilder diagonalLines = new StringBuilder();
        do {
            if (board.getPlayerOfTokenAt(col, row).equals(" ")) {
                diagonalLines.append('_');
            } else {
                diagonalLines.append(board.getPlayerOfTokenAt(col, row));
            }
            col++;
            row++;
        }while(col<6&&row<6);
        return diagonalLines;
    }

    private StringBuilder getDiagonal2(int col, int row){
        StringBuilder diagonalLines = new StringBuilder();
        do{
            if (board.getPlayerOfTokenAt(col, row).equals(" ")) {
                diagonalLines.append('_');
            } else {
                diagonalLines.append(board.getPlayerOfTokenAt(col, row));
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
        }else if(line.matches("(w_bb__|__bb_w)")){
            if(player==TokenType.BLACK) {
                value += 7;
            }else value-=7;
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

    public void flipBoard(boolean flip) {
        board.flipBoard(flip);
    }
}

