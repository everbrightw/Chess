package chess;

/**
 * abstract methods used by chess pieces
 */
public interface Moveable {

    boolean hasPieceOnThePath(Board board, Square dest);//return true if there is a chess on the pass from start to destination

}
