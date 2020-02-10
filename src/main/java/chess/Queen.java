package chess;

/**
 * This is the chess.Queen Object.
 *
 * From Wikipedia,
 * The queen combines the power of the rook and bishop and can move any number of squares along rank, file, or diagonal,
 * but it may not leap over other pieces.
 */
public class Queen extends Piece{

    final public static String _NAME = "queen";

    //constructor
    public Queen(Player player, Square square){
        setPlayer(player);
        setSquare(square);
        setName(Queen._NAME);
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

        //Queen's moving rules
        System.out.println("queen movedddddddd???");
        float dfX = Math.abs(start.getX() - dest.getX());
        float dfY = Math.abs(start.getY() - dest.getY());
        if(start.getX() != dest.getX() && start.getY() != dest.getY()){
            //moving diagonal
            if(dfY == 0){return false;}//avoid divide by zero
            return dfX / dfY == 1.0;
        }
        return Math.abs(start.getX() - dest.getX()) * Math.abs(start.getY() - dest.getY()) == 0;
    }


    @Override
    public boolean hasPieceOnThePath(Board board, Square dest) {

        int x = this.getX();int y = this.getY();
        int destX = dest.getX();int destY = dest.getY();

        if(getDirection(this.getSquare(),dest) == Direction.DOWN ||
                getDirection(this.getSquare(),dest) == Direction.UP ||
                getDirection(this.getSquare(),dest) == Direction.RIGHT ||
                getDirection(this.getSquare(),dest) == Direction.LEFT){
            return checkStraight(board,dest,x,y,destX,destY);

        }
        else{
            return checkDiagonal(board,dest,x,y,destX);
        }
    }
}
