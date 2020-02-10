package chess;

import java.util.logging.Level;

/**
 * This is the chess.Pawn object.
 *
 * From Wikipedia,
 * The pawn may move forward to the unoccupied square immediately in front of it on the same file; or on its first move
 * it may advance two squares along the same file provided both squares are unoccupied; or it may move to a square occupied by
 * an opponent's piece which is diagonally in front of it on an adjacent file, capturing that piece.
 */

public class Pawn extends Piece implements Moveable{

    final public static String _NAME = "pawn";

    private boolean firstStep = true;//This pawn has not been moved, so it may advance two squares;

    private int moveDistance = 0;

    public Pawn(Player player, Square square) {
        setPlayer(player);
        setSquare(square);
        setName(Pawn._NAME);
        square.setPiece(this);
    }


    @Override
    public boolean move(Game game, Square dest) {

        if(!this.getPlayer().getColor().equals(game.getPlayerTurn())){
            System.out.println("Not Your Turn");
            return false;
        }

        if(dest == null || game.getBoard().getSquare(dest.getX(), dest.getY()) == null){
            Main.LOGGER.log(Level.INFO, "Destination Out Of Bound");
            return false;
        }


        if(this.canMove(game.getBoard(), this.getSquare(), dest)){

                //moving piece
//                System.out.println("31");

            Square prevSquare = this.getSquare();
            this.setSquare(dest);
            dest.setPiece(this);
            prevSquare.setNullPiece();


            this.firstStep = false;
            moveDistance++;
            return true;
        }
        printCanNotMoveToWarning(dest);
        return false;
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

        if(this.getPlayer().getColor().equals(Player.WHITE)){//only moving down
            if (getDirection(start,dest) != Direction.UP){

                return false;
            }
        }else{// only moving up
            if(getDirection(start,dest) != Direction.DOWN){

                return false;
            }
        }

        return (Math.abs(start.getX() - dest.getX()) == 1 && start.getY() == dest.getY())
                ||(firstStep && (Math.abs(start.getX() - dest.getX()) == 2 && start.getY() == dest.getY()));

    }


    @Override
    public boolean hasPieceOnThePath(Board board, Square dest) {

        if(firstStep && Math.abs(dest.getX() - getSquare().getX()) == 2){
            if(this.getDirection(this.getSquare(), dest) == Direction.UP){
                //moving down
                boolean twoSpace = false;
                if( board.getSquare(this.getX() + 2, this.getY())!= null){
                    twoSpace =  board.getSquare(this.getX() + 2, this.getY()).hasChess();
                }
                return board.getSquare(this.getX() + 1, this.getY()).hasChess() || twoSpace;

            }else {
                //moving up
                boolean twoSpace = false;
                if( board.getSquare(this.getX() - 2, this.getY())!= null){
                    twoSpace =  board.getSquare(this.getX() - 2, this.getY()).hasChess();
                }
                return board.getSquare(this.getX() - 1, this.getY()).hasChess() ||
                        twoSpace;
            }
        }
        return false;
    }

    @Override
    public Direction getDirection(Square start, Square dest) {
        return super.getDirection(start, dest);
    }

    public void setFirstStep(boolean firstStep) {
        this.firstStep = firstStep;
    }
    public boolean getFirstStep(){
        return firstStep;
    }

    public int getMoveDistance() {
        return moveDistance;
    }

    public void setMoveDistance(int moveDistance) {
        this.moveDistance = moveDistance;
    }
}
