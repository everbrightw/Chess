package chess;

/**
 * This is chess.King object
 * From Wikipedia,
 * The chess.King moves one square in any direction.
 */


public class King extends Piece{

    final public static String _NAME = "king";

    private Piece checkPiece;
    //constructor
    public King(Player player, Square square){
        setPlayer(player);
        setSquare(square);
        setName(King._NAME);
        square.setPiece(this);
    }


    @Override
    public boolean canMove(Board board, Square start, Square dest) {

        //check if the destination square is occupied by player's own piece
        //and if the destination square is out of bound
        if(!passBasicMoveRules(board, start, dest)){
            return false;
        }
        float dfX = Math.abs(start.getX() - dest.getX());
        float dfY = Math.abs(start.getY() - dest.getY());

        if(getDirection(start,dest) == Direction.UP ||
                getDirection(start,dest) == Direction.DOWN ||
                getDirection(start,dest) == Direction.RIGHT ||
                getDirection(start,dest) == Direction.LEFT){
            return Math.abs(start.getX() - dest.getX()) + Math.abs(start.getY() - dest.getY()) == 1;
        }
        else{
            return dfX == 1 && dfY == 1;
        }
    }

    //king does not need this method since it can only move 1 square
    @Override
    public boolean hasPieceOnThePath(Board board, Square dest) {
        return false;
    }

    public void setCheckPiece(Piece checkPiece) {
        this.checkPiece = checkPiece;
    }

    public Piece getCheckPiece() {
        return checkPiece;
    }
}
