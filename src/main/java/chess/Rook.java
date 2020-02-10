package chess;

/**
 * This is the chess.Rook object
 *
 * From Wikipedia:
 * The rook can move any number of squares along any rank or file, but may not leap over other pieces.
 */
public class Rook extends Piece{

    final static String _NAME = "rook";

    //constructors
    public Rook(Player player, Square square){
        setPlayer(player);
        setSquare(square);
        setName(Rook._NAME);
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
            System.out.println("31");
            return false;
        }

        //rook moving rules
        return Math.abs(start.getX() - dest.getX()) * Math.abs(start.getY() - dest.getY()) == 0;
    }



    @Override
    public boolean hasPieceOnThePath(Board board, Square dest) {
        int x = this.getX();int y = this.getY();
        int destX = dest.getX();int destY = dest.getY();

        //checking four directions for rook
        return checkStraight(board,dest,x,y,destX,destY);
    }
}
