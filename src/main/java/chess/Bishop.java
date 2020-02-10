package chess;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the chess.Bishop object.
 *
 * Cite from Wikipedia: The bishop can move any number of squares diagonally, but may not leap over other pieces.
 */

public class Bishop extends Piece{

    final public static String _NAME = "bishop";

    //constructor
    public Bishop(Player player, Square square){
        setPlayer(player);
        setSquare(square);
        setName(Bishop._NAME);
        square.setPiece(this);
    }

    @Override
    public boolean canMove(Board board, Square start, Square dest) {

        //check if the destination square is occupied by player's own piece
        //and if the destination square is out of bound
        if(!passBasicMoveRules(board, start, dest)){
            return false;
        }
        if(hasPieceOnThePath(board, dest)){
            return false;
        }
        //Bishop moving rules
        float dfX = Math.abs(start.getX() - dest.getX());
        float dfY = Math.abs(start.getY() - dest.getY());
        if(dfY == 0){return false;}//avoid divide by zero
        System.out.println(dfX);
        System.out.println(dfY);
        return dfX / dfY == 1.0;
    }


    //check diagonally for bishop if there is a piece on the bishop's moving path
    @Override
    public boolean hasPieceOnThePath(Board board, Square dest) {

        int x = this.getX();int y = this.getY();
        int destX = dest.getX();

        return checkDiagonal(board,dest,x,y,destX);


    }
}
