package chess;

import java.util.logging.Level;

/**
 * Custom Chess Piece
 * Vampire can move like knight or move to any vertical or horizontal direction 2 square at once.
 * and transform enemy's chess piece into his/her own chess piece after capturing it and move the
 * captured enemy's piece to vampire's original location
 *
 */

public class Vampire extends Piece implements Moveable{

    final static String _NAME = "vampire";

    public Vampire(Player player, Square square){
        setPlayer(player);
        setSquare(square);
        setName(Vampire._NAME);
        square.setPiece(this);
    }

    //need to override parent's move method since Vampires need extra actions after moving to a new square
    @Override
    public boolean move(Game game, Square dest){

        if (moveBasicCheck(game, dest)) return false;

        if(canMove(game.getBoard(),this.getSquare(), dest)){
            if(dest.hasChess()){

                //do the vampire magic(transform enemies)
                game.getVc().vampireTransform(getSquare(),dest);

            }
            else{
                //move the piece
                Square prevSquare = this.getSquare();
                this.setSquare(dest);
                dest.setPiece(this);
                prevSquare.setNullPiece();
                return true;
            }
            return true;
        }

        return false;
    }


    @Override
    public boolean canMove(Board board, Square start, Square dest) {

        if(!passBasicMoveRules(board, start, dest)
                || hasPieceOnThePath(board, dest)){
            return false;
        }

        //vampire moving rules; Combine knight's and king's moving rules
        int difX = Math.abs(start.getX() - dest.getX());
        int difY = Math.abs(start.getY() - dest.getY());

        if(difX == 0 || difY == 0){//vertical or horizontal
            return (difX == 0 && difY <=2) || (difY == 0 && difX <=2);
        }
        //same as knight
        return difX * difY == 2;
    }

    //vampire's knight move can ignore the piece on path so we will skip it
    //vampire's another move need to check piece on its path
    @Override
    public boolean hasPieceOnThePath(Board board, Square dest) {

        int destX = dest.getX();
        int destY = dest.getY();

        return checkStraight(board,dest,getX(),getY(),destX,destY);
    }
}
