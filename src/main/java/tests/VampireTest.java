package tests;

import chess.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VampireTest {
    Game game = new Game(new Player(Player.WHITE), new Player(Player.BLACK), new Board(Board.BLANK_BOARD));
    Board board = game.getBoard();

    @Test
    void testVampireMove(){

        board = new Board(Board.BLANK_BOARD);
        game = new Game(new Player(Player.WHITE), new Player(Player.BLACK), board);

        Square dest = board.getSquare(2,4);
        Square start = board.getSquare(4,4);

        new Queen(game.getPlayer2(), dest);//enemy's piece
        Vampire vampire = new Vampire(game.getPlayer1(),start);
        vampire.move(game,dest);

        assertTrue(dest.getPiece() instanceof Vampire);
        assertTrue(start.getPiece() instanceof Queen);
        assertEquals(start.getPiece().getPlayer(),vampire.getPlayer());//test if the queen has been transformed

    }

    @Test
    void testVampireInvalidMove(){

        board = new Board(Board.BLANK_BOARD);
        game = new Game(new Player(Player.WHITE), new Player(Player.BLACK), board);

        Square start = board.getSquare(4,4);
        Square dest = board.getSquare(2,4);
        Square block = board.getSquare(3,4);

        new Queen(game.getPlayer2(),block);//setting up the block
        Vampire vampire = new Vampire(game.getPlayer1(),start);
        vampire.move(game,dest);

        assertFalse(dest.getPiece() instanceof  Vampire);
        assertTrue(start.getPiece() instanceof  Vampire);
    }


}
