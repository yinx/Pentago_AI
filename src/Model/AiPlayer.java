package Model;

import java.util.ArrayList;

/**
 * Created by Philippe Damen on 20/10/2015.
 */
public class AiPlayer {

    private TokenType colour;

    public AiPlayer(TokenType colour) {
        this.colour = colour;
    }


    public BoardState minimax(int depth, BoardState boardState, Boolean max){
        if(max){
            boardState.setHeuristicValue(Double.NEGATIVE_INFINITY);
            if(depth!=0){
                ArrayList children;
                children = generateChildren(boardState,colour);

                while(!children.isEmpty()&&boardState.getHeuristicValue()!=Double.POSITIVE_INFINITY&&!(boardState.getBeta()<=boardState.getAlpha())) {
                    boardState.add((BoardState) children.remove(0));
                    BoardState newBoard = (BoardState)boardState.getChildAt(boardState.getChildCount()-1);

                    if(newBoard.calculateBoardValue(colour)!=Double.POSITIVE_INFINITY) {
                        newBoard.setBeta(boardState.getBeta());
                        newBoard.setAlpha(boardState.getAlpha());
                        BoardState HBoard = minimax(depth - 1, newBoard, false);
                        if (HBoard.getHeuristicValue() > boardState.getHeuristicValue()) {
                            boardState.setHeuristicValue(HBoard.getHeuristicValue());
                            boardState.setHeuristicBoard(HBoard);
                        }
                        if(HBoard.getHeuristicValue()>  boardState.getAlpha()){
                            boardState.setAlpha(HBoard.getHeuristicValue());
                        }
                    }else{
                        boardState.setHeuristicValue(Double.POSITIVE_INFINITY);
                    }
                }
            }else{
                Double value = boardState.calculateBoardValue(colour);
                boardState.setHeuristicValue(value);
                boardState.setHeuristicBoard(boardState);
            }
        }else{
            boardState.setHeuristicValue(Double.POSITIVE_INFINITY);
            if(depth!=0){
                ArrayList children;
                children = generateChildren(boardState,colour==TokenType.BLACK?TokenType.WHITE:TokenType.BLACK);

                while(!children.isEmpty()&&boardState.getHeuristicValue()!=Double.NEGATIVE_INFINITY&&!(boardState.getBeta()<=boardState.getAlpha())) {
                    boardState.add((BoardState) children.remove(0));
                    BoardState newBoard = (BoardState)boardState.getChildAt(boardState.getChildCount() - 1);

                    if(newBoard.calculateBoardValue(colour)!=Double.NEGATIVE_INFINITY) {
                        newBoard.setBeta(boardState.getBeta());
                        newBoard.setAlpha(boardState.getAlpha());
                        BoardState HBoard = minimax(depth - 1, newBoard, true);
                        if (HBoard.getHeuristicValue() < boardState.getHeuristicValue()) {
                            boardState.setHeuristicValue(HBoard.getHeuristicValue());
                            boardState.setHeuristicBoard(HBoard);
                        }
                        if(HBoard.getHeuristicValue() <  boardState.getBeta()){
                            boardState.setBeta(HBoard.getHeuristicValue());
                        }
                    }else{
                        boardState.setHeuristicValue(Double.NEGATIVE_INFINITY);
                    }
                }
            }else{
                Double value =  boardState.calculateBoardValue(colour);
                boardState.setHeuristicValue(value);
                boardState.setHeuristicBoard(boardState);
            }
        }
        return boardState.getHeuristicBoard();
    }

    private ArrayList<BoardState> generateChildren(BoardState board,TokenType colour){
        ArrayList<BoardState> children = new ArrayList<>();
        for(int i = 0;i<6;i++){
            for(int j = 0;j<6;j++){
                BoardState newState = new BoardState(board);
                if(newState.setTokenOnBoard(colour,j,i)){
                    BoardState temp1 = new BoardState(newState);
                    BoardState temp2 = new BoardState(newState);
                    BoardState temp3 = new BoardState(newState);
                    BoardState temp4 = new BoardState(newState);
                    BoardState temp5 = new BoardState(newState);
                    BoardState temp6 = new BoardState(newState);
                    BoardState temp7 = new BoardState(newState);
                    BoardState temp8 = new BoardState(newState);
                    temp1.turnBoard(1,true);
                    temp2.turnBoard(1,false);
                    temp3.turnBoard(2,true);
                    temp4.turnBoard(2,false);
                    temp5.turnBoard(3,true);
                    temp6.turnBoard(3,false);
                    temp7.turnBoard(4,true);
                    temp8.turnBoard(4,false);
                    if (!children.contains(temp1)) children.add(temp1);
                    if (!children.contains(temp2)) children.add(temp2);
                    if (!children.contains(temp2)) children.add(temp3);
                    if (!children.contains(temp3)) children.add(temp4);
                    if (!children.contains(temp4)) children.add(temp5);
                    if (!children.contains(temp5)) children.add(temp6);
                    if (!children.contains(temp6)) children.add(temp7);
                    if (!children.contains(temp7)) children.add(temp8);
                }
            }
        }
        return children;
    }

}

