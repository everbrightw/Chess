package tests;

import chess.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {


    @Test
    void testBoardCreate(){
        Game game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), new Board(Board.START_BOARD));
        Board board = game.getBoard();

        Piece[] whitePawns = new Pawn[8];
        for(int i = 0; i < 8; i++){
            whitePawns[i] = board.getSquare(1,i).getPiece();
        }
        Piece[] blackPawns = new Pawn[8];
        for(int i = 0; i < 8; i++){
            blackPawns[i] = board.getSquare(6,i).getPiece();
        }
        Piece whiteRook1 =  board.getSquare(0,0).getPiece();
        Piece whiteRook2 =  board.getSquare(0,7).getPiece();
        Piece whiteKnight1 =  board.getSquare(0,1).getPiece();
        Piece whiteKnight2 =  board.getSquare(0,6).getPiece();
        Piece whiteBishop1 =  board.getSquare(0,2).getPiece();
        Piece whiteBishop2 =  board.getSquare(0,5).getPiece();
        Piece whiteQueen =  board.getSquare(0,3).getPiece();
        Piece whiteKing =  board.getSquare(0,4).getPiece();

        Piece blackRook1 =  board.getSquare(7,0).getPiece();
        Piece blackRook2 =  board.getSquare(7,7).getPiece();
        Piece blackKnight1 =  board.getSquare(7,1).getPiece();
        Piece blackKnight2 =  board.getSquare(7,6).getPiece();
        Piece blackBishop1 =  board.getSquare(7,2).getPiece();
        Piece blackBishop2 =  board.getSquare(7,5).getPiece();
        Piece blackQueen =  board.getSquare(7,3).getPiece();
        Piece blackKing =  board.getSquare(7,4).getPiece();

        //asserting
        //pawns
        for(int i = 0; i < 8; i ++){
            //white pawn
            assertTrue(whitePawns[i] instanceof Pawn
                    && whitePawns[i].getPlayer().getColor().equals(Player.WHITE));
            assertTrue(blackPawns[i] instanceof Pawn
                    && blackPawns[i].getPlayer().getColor().equals(Player.BLACK));
        }
        //whit other pieces
        assertTrue(whiteRook1 instanceof Rook && whiteRook1.getPlayer().getColor().equals(Player.WHITE));
        assertTrue(whiteRook2 instanceof Rook && whiteRook2.getPlayer().getColor().equals(Player.WHITE));
        assertTrue(whiteBishop1 instanceof Bishop && whiteBishop1.getPlayer().getColor().equals(Player.WHITE));
        assertTrue(whiteBishop2 instanceof Bishop && whiteBishop2.getPlayer().getColor().equals(Player.WHITE));
        assertTrue(whiteKnight1 instanceof Knight && whiteKnight1.getPlayer().getColor().equals(Player.WHITE));
        assertTrue(whiteKnight2 instanceof Knight && whiteKnight2.getPlayer().getColor().equals(Player.WHITE));
        assertTrue(whiteQueen instanceof Queen && whiteQueen.getPlayer().getColor().equals(Player.WHITE));
        assertTrue(whiteKing instanceof King && whiteKing.getPlayer().getColor().equals(Player.WHITE));
        //black other pieces
        assertTrue(blackRook1 instanceof Rook && blackRook1.getPlayer().getColor().equals(Player.BLACK));
        assertTrue(blackRook2 instanceof Rook && blackRook2.getPlayer().getColor().equals(Player.BLACK));
        assertTrue(blackBishop1 instanceof Bishop && blackBishop1.getPlayer().getColor().equals(Player.BLACK));
        assertTrue(blackBishop2 instanceof Bishop && blackBishop2.getPlayer().getColor().equals(Player.BLACK));
        assertTrue(blackKnight1 instanceof Knight && blackKnight1.getPlayer().getColor().equals(Player.BLACK));
        assertTrue(blackKnight2 instanceof Knight && blackKnight2.getPlayer().getColor().equals(Player.BLACK));
        assertTrue(blackQueen instanceof Queen && blackQueen.getPlayer().getColor().equals(Player.BLACK));
        assertTrue(blackKing instanceof King && blackKing.getPlayer().getColor().equals(Player.BLACK));

    }
}
