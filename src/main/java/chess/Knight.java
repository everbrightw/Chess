package chess;



/**
 * This is the chess.Knight obejct.
 *
 * From Wikipedia:
 * The knight moves to any of the closet squares that are not on the same rank, file or diagonal,
 * thus the move forms an "L-shape": two squares vertically and one square horizontally, or two squares horizontally and one square vertically.
 * The knight is the only piece that can leap over other pieces.
 */

public class Knight extends Piece{

    final public static String _NAME = "knight";
    //constructor
    public Knight(Player player, Square square){
        setPlayer(player);
        setSquare(square);
        setName(Knight._NAME);
        square.setPiece(this);
    }

    @Override
    public boolean canMove(Board board, Square start, Square dest) {

        //check if the destination square is occupied by player's own piece
        //and if the destination square is out of bound
        if(!passBasicMoveRules(board, start, dest)){
            return false;
        }
        //knight moving rules
        return Math.abs(start.getX() - dest.getX()) * Math.abs(start.getY() - dest.getY()) == 2;
    }

    //knight does not need this method, since it can ignore the chess piece on its path
    @Override
    public boolean hasPieceOnThePath(Board board, Square dest) {
        return false;
    }
}
