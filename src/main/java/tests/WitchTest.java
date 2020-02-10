package tests;

import chess.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WitchTest {
    Game game = new Game(new Player(Player.WHITE), new Player(Player.BLACK), new Board(Board.BLANK_BOARD));
    Board board = game.getBoard();

    @Test
    void testWitchMove(){

        board = new Board(Board.BLANK_BOARD);
        game = new Game(new Player(Player.WHITE), new Player(Player.BLACK), board);

        Square start = board.getSquare(4,4);
        Square block = board.getSquare(3,4);
        Square dest = board.getSquare(2,4);

        Witch witch = new Witch(game.getPlayer1(), start);
        new Rook(game.getPlayer1(),block);//creating environment for witch to jump over
        new Pawn(game.getPlayer2(), dest);//set up an enemy to be captured by witch

        witch.move(game,dest);

        assertTrue(dest.getPiece() instanceof Queen);//see if the witch has been upgraded to a queen after capturing an enemy
        assertFalse(start.getPiece() instanceof Witch);//test moved
        assertEquals(dest.getPiece().getPlayer(), game.getPlayer1());
    }

    @Test
    void testWitchInvalidMove(){
        board = new Board(Board.BLANK_BOARD);
        Square start = board.getSquare(4,4);
        Square dest = board.getSquare(1,1);

        assertWitchInvalidMove(start, dest);
    }

    @Test
    void testWitchInvalidMove2(){
        board = new Board(Board.BLANK_BOARD);
        Square start = board.getSquare(4,4);
        Square dest = board.getSquare(2,4);//it should be a valid destination as long as there is a chess next to witch

        assertWitchInvalidMove(start, dest);
    }

    private void assertWitchInvalidMove(Square start, Square dest) {
        Witch witch = new Witch(game.getPlayer1(), start);
        witch.move(game, dest);
        assertTrue(start.getPiece() instanceof Witch);
        assertFalse(dest.hasChess());
    }
}
