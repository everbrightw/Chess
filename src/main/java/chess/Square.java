package chess;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * chess.Square class which represents each squares on the board. There should be 64 cells on a 8x8 chess board.
 */
public class Square {

    private boolean hasChess = false;//false by default
    private Piece piece;
    private int x, y;//coordinates

    //Constructors
    public Square(Piece piece, int x, int y){

        this.x = x;
        this.y = y;
        this.piece = piece;

    }

    //getters and setters
    public Piece getPiece() {
        return piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setHasChess(boolean hasChess) {
        this.hasChess = hasChess;
    }

    public void setPiece(Piece piece) {
        if(piece == null){
            this.piece = null;
            setHasChess(false);
        }
        else{
            this.piece = piece;
            this.piece.setSquare(this);
            setHasChess(true);
        }

    }
    public void setNullPiece(){
        this.piece = null;
        setHasChess(false);
    }

    public boolean hasChess(){
        return hasChess;
    }

}
