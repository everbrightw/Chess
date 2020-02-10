package tests;

import chess.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class ChessTest{

     Game game;
     Board board;


    @Test
     void testBasicPawnMove(){

        board = new Board(Board.START_BOARD);
        game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), board);
        Square start = board.getSquare(1,0);
        Square dest = board.getSquare(2,0);
        start.getPiece().move(game,dest);
        //assert
        assertTrue(dest.getPiece() instanceof Pawn, "Pawn Moved To Destination");
        assertNull(start.getPiece(), "Original Piece Is Moved");
        assertTrue(dest.hasChess(), "Destination Has Chess");
        assertFalse(start.hasChess(), "Start Does Not Have Chess ");
        board.printBoard();

    }
    @Test
     void testPawnFistStepMove(){


        Square start = board.getSquare(1,1);
        Square dest = board.getSquare(3,1);
        start.getPiece().move(game,dest);
        //assert
        assertTrue(dest.getPiece() instanceof Pawn, "Pawn Moved To Destination");
        assertNull(start.getPiece(), "Original Piece Is Moved");
        assertFalse( start.hasChess(), "Has Chess Checked");
        assertTrue(dest.hasChess(), "Destination Has Chess");

    }
    @Test
     void testPawnMoveHasPieceOnPath(){

        Square start = board.getSquare(1,3);
        Square dest = board.getSquare(3,3);
        Square pieceOnPath = board.getSquare(2,3);
        pieceOnPath.setPiece(new Rook(game.getPlayer1(), pieceOnPath));
        start.getPiece().move(game,dest);

        assertFalse( dest.getPiece() instanceof Pawn, "Pawn Moved To Destination");
        assertTrue(start.getPiece() instanceof Pawn, "Original Piece Is Moved");

    }

    @Test
     void testBishopBasicMove(){
        board = new Board(Board.BLANK_BOARD);
        game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), board);

        Square start = board.getSquare(7,5);
        Square dest = board.getSquare(6,4);
        Square dest2 = board.getSquare(0,0);
        Square dest3 = board.getSquare(5,5);
        Square dest4 = board.getSquare(7,7);

        Bishop testBishop = new Bishop(game.getPlayer1(), start);
        start.setPiece(testBishop);

        //asserting
        testBishop.move(game,dest);
//        testBishop.move(game,dest2);
//        testBishop.move(game,dest3);
//        testBishop.move(game,dest4);

        assertTrue(dest.hasChess());
//        assertFalse(dest3.hasChess());
        assertTrue(dest.getPiece() instanceof Bishop );
//        assertFalse(dest3.getPiece() instanceof Bishop);


    }

    @Test
    void testBishopInvalidMove(){

        board = new Board(Board.BLANK_BOARD);
        game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), board);


//        Square dest = board.getSquare(6,7);//invalid move
//        Square dest2 = board.getSquare(8,8);//invalid move
//        Square start = board.getSquare(5,5);
//        Square dest3 = board.getSquare(8,8);//out of bound invalid move
//        Square dest4 = board.getSquare(7,5);//invalid move
//
//        Bishop testBishop = new Bishop(game.getPlayer1(), start);
//        board.getSquare(5,5).setPiece(testBishop);
//
//        testBishop.move(game,dest);
//        assertTrue(start.getPiece() instanceof Bishop);
//
//        testBishop.move(game,dest2);
//        assertTrue(start.getPiece() instanceof Bishop);
//
//        testBishop.move(game,dest3);
//        testBishop.move(game,dest4);
//        assertTrue(start.getPiece() instanceof Bishop);

        Square start = board.getSquare(7,5);
        Square dest = board.getSquare(5,7);
        Square pawnSquare = board.getSquare(6,6);
        Pawn pawn = new Pawn(game.getPlayer1(),start);
        pawnSquare.setPiece(pawn);
        Bishop test = new Bishop(game.getPlayer1(),start);
        start.setPiece(test);
        test.move(game,dest);
        assertFalse(dest.getPiece() instanceof Bishop);


    }

    @Test
    void testKnightMove(){



        game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), new Board(Board.BLANK_BOARD));

        Square start = game.getBoard().getSquare(5,5);
        Knight testKnight = new Knight(game.getPlayer1(), start);
        Square dest = game.getBoard().getSquare(4,3);//valid move

        testKnight.move(game,dest);
        assertTrue(dest.getPiece() instanceof Knight);
        assertFalse(start.getPiece() instanceof Knight);

        start = testKnight.getSquare();
        dest = game.getBoard().getSquare(4,4);//invalid move
        testKnight.move(game,dest);
        assertTrue(start.getPiece() instanceof Knight);
        assertFalse(dest.getPiece() instanceof Knight);

        //test knight ignore piece on path
        start =testKnight.getSquare();
        Square blockSquare = game.getBoard().getSquare(3,3);
        //set up a block on the path
        new Knight(game.getPlayer1(), blockSquare);
        dest = game.getBoard().getSquare(2,2);
        testKnight.move(game,dest);

        assertTrue(dest.getPiece() instanceof Knight);
        assertFalse(start.getPiece() instanceof Knight);


    }

    @Test
     void testRookMove(){


        game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), new Board(Board.BLANK_BOARD));

        Square start = game.getBoard().getSquare(5,5);
        Rook testRook = new Rook(game.getPlayer1(), start);

        Square dest;

        //moving up
        dest = game.getBoard().getSquare(3,5);
        testRook.move(game,dest);
        assertTrue(dest.getPiece() instanceof Rook);
        assertFalse(start.getPiece() instanceof Rook);

        //moving down
        dest = game.getBoard().getSquare(7,5);
        start = testRook.getSquare();
        testRook.move(game, dest);
        assertTrue(dest.getPiece() instanceof Rook);
        assertFalse(start.getPiece() instanceof Rook);

        //moving left and right
        start = testRook.getSquare();
        dest = game.getBoard().getSquare(7,3);
        testRook.move(game, dest);
        dest = game.getBoard().getSquare(0,3);
        testRook.move(game,dest);
        assertTrue(dest.getPiece() instanceof Rook);
        assertFalse(start.getPiece() instanceof Rook);

        //blocked path move (invalid move)
        new Rook(game.getPlayer1(), game.getBoard().getSquare(0,2));//set up a block on the path
        start = testRook.getSquare();
        dest = game.getBoard().getSquare(0,0);
        testRook.move(game, dest);
        assertFalse(dest.getPiece() instanceof Rook);
        assertTrue(start.getPiece() instanceof Rook);

    }
    @Test
    void testUIRooke(){
        board = new Board(Board.START_BOARD);
        game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), board);
        Rook rook = (Rook) board.getSquare(7,7).getPiece();
        board.getSquare(7,5).setNullPiece();
        rook.move(game,board.getSquare(7,5));

        assertFalse(board.getSquare(7,3).getPiece() instanceof Rook);



    }

    @Test
     void testKingMove(){

        board = new Board(Board.BLANK_BOARD);

        Square start = board.getSquare(5,5);
        King king = new King(game.getPlayer1(), start);

        Square[] dests = new Square[8];//8 direction
        dests[0] = board.getSquare(4,5);
        dests[1] = board.getSquare(4,4);
        dests[2] = board.getSquare(4,6);
        dests[3] = board.getSquare(6,5);
        dests[6] = board.getSquare(5,4);
        dests[7] = board.getSquare(5,6);
        dests[4] = board.getSquare(6,6);
        dests[5] = board.getSquare(6,4);


        assertKingMoveHelper(dests, king, 0, start);
        assertKingMoveHelper(dests, king, 1, start);
        assertKingMoveHelper(dests, king, 2, start);
        assertKingMoveHelper(dests, king, 3, start);
        assertKingMoveHelper(dests, king, 4, start);
        assertKingMoveHelper(dests, king, 5, start);
        assertKingMoveHelper(dests, king, 6, start);
        assertKingMoveHelper(dests, king, 7, start);


    }
     void assertKingMoveHelper(Square[] dests, King king, int i, Square start){
         king.move(game,dests[i]);
         assertTrue(dests[i].getPiece() instanceof King);
         king.move(game, start);
     }


    @Test
     void testQueenMove(){

        game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), new Board(Board.BLANK_BOARD));

        Square start = game.getBoard().getSquare(5,5);
        Queen queen = new Queen(game.getPlayer1(), start);

        Square[] dests = new Square[8];
        dests[0] = game.getBoard().getSquare(4,5);
        dests[1] = game.getBoard().getSquare(2,2);
        dests[6] = game.getBoard().getSquare(5,4);
        dests[2] = game.getBoard().getSquare(4,6);
        dests[3] = game.getBoard().getSquare(7,7);
        dests[4] = game.getBoard().getSquare(6,6);
        dests[5] = game.getBoard().getSquare(7,3);
        dests[7] = game.getBoard().getSquare(5,6);

        assertQueenMoveHelper(dests, queen, 0, start);
        assertQueenMoveHelper(dests, queen, 1, start);
        assertQueenMoveHelper(dests, queen, 2, start);
        assertQueenMoveHelper(dests, queen, 3, start);
        assertQueenMoveHelper(dests, queen, 4, start);
        assertQueenMoveHelper(dests, queen, 5, start);
        assertQueenMoveHelper(dests, queen, 6, start);
        assertQueenMoveHelper(dests, queen, 7, start);

    }

     void assertQueenMoveHelper(Square[] dests, Queen queen, int i, Square start){
         queen.move(game,dests[i]);
         assertTrue(dests[i].getPiece() instanceof Queen);
         queen.move(game, start);
     }
     @Test
     void testQueenInvalidMove(){

         board = new Board(Board.BLANK_BOARD);
         game = new Game(new Player(Player.BLACK), new Player(Player.WHITE), new Board(Board.BLANK_BOARD));

         Square start = board.getSquare(5,5);
         Queen queen = new Queen(game.getPlayer1(), start);
         Square dest = board.getSquare(7,7);
         Square block = board.getSquare(6,6);
         new Queen(game.getPlayer1(),block);//set up block
         queen.move(game,dest);

         //1
         assertQueenInvalid(start,dest);
         //2
         dest = board.getSquare(2,5);
         block = board.getSquare(3,5);
         new Queen(game.getPlayer1(), block);//set up block
         queen.move(game,dest);
         assertQueenInvalid(start,dest);

     }

     void assertQueenInvalid(Square start, Square dest){
         assertTrue(start.getPiece() instanceof Queen);
         assertFalse(dest.getPiece() instanceof Queen);
     }

     @Test
     void testPieceOutOfBoundMove(){
        board = new Board(Board.BLANK_BOARD);
        Square start = board.getSquare(4,4);
        Queen queen = new Queen(game.getPlayer1(), start);
        Square dest = board.getSquare(8,8);//out of bound destination
        queen.move(game,dest);
        assertTrue(start.getPiece() instanceof Queen);
     }

     @Test
     void testPieceOutOfBoundMove2(){
         board = new Board(Board.BLANK_BOARD);
         Square start = board.getSquare(0,4);
         Queen queen = new Queen(game.getPlayer1(), start);
         Square dest = board.getSquare(8,4);//out of bound destination
         queen.move(game,dest);
         assertTrue(start.getPiece() instanceof Queen);
     }

     @Test
     void testPieceMoveToSquareHasPiece(){
        board = new Board(Board.BLANK_BOARD);
        Square start = board.getSquare(0,4);
        Square dest = board.getSquare(4,4);
        Rook rook = new Rook(game.getPlayer1(), start);
        new King(game.getPlayer1(), dest);//set to be own chess piece

        rook.move(game, dest);
        assertTrue(dest.getPiece() instanceof King);
        assertTrue(start.getPiece() instanceof Rook);

     }

     @Test
     void testPieceMoveToSquareHasPiece2(){
         board = new Board(Board.BLANK_BOARD);
         Square start = board.getSquare(2,4);
         Square dest = board.getSquare(4,4);
         Knight knight = new Knight(game.getPlayer1(), start);
         new King(game.getPlayer1(), dest);//set to be own chess piece

         knight.move(game, dest);
         assertTrue(dest.getPiece() instanceof King);
         assertTrue(start.getPiece() instanceof Knight);

     }

     @Test
     void testDirectionUp(){
        board = new Board(Board.BLANK_BOARD);
        Square start = board.getSquare(2,4);
        Queen queen = new Queen(game.getPlayer1(), start);
        Square dest = board.getSquare(1,4);
        assertEquals(Direction.UP, queen.getDirection(start, dest));
     }
     @Test
     void testDirectionDown(){
         board = new Board(Board.BLANK_BOARD);
         Square start = board.getSquare(2,4);
         Queen queen = new Queen(game.getPlayer1(), start);
         Square dest = board.getSquare(3,4);
         assertEquals(Direction.DOWN, queen.getDirection(start, dest));
     }


     @Test
     void testDirectionRight(){
         board = new Board(Board.BLANK_BOARD);
         Square start = board.getSquare(2,4);
         Queen queen = new Queen(game.getPlayer1(), start);
         Square dest = board.getSquare(2,7);
         assertEquals(Direction.RIGHT, queen.getDirection(start, dest));
     }

     @Test
     void testDirectionDownRight(){
         board = new Board(Board.BLANK_BOARD);
         Square start = board.getSquare(2,4);
         Queen queen = new Queen(game.getPlayer1(), start);
         Square dest = board.getSquare(3,5);
         assertEquals(Direction.DOWN_RIGHT, queen.getDirection(start, dest));
     }
     @Test
     void testDirectionUpRight(){
         board = new Board(Board.BLANK_BOARD);
         Square start = board.getSquare(2,4);
         Queen queen = new Queen(game.getPlayer1(), start);
         Square dest = board.getSquare(3,3);
         assertEquals(Direction.UP_RIGHT, queen.getDirection(start, dest));
     }
     @Test
     void testDirectionUpLeft(){
         board = new Board(Board.BLANK_BOARD);
         Square start = board.getSquare(2,4);
         Queen queen = new Queen(game.getPlayer1(), start);
         Square dest = board.getSquare(1,3);
         assertEquals(Direction.UP_LEFT, queen.getDirection(start, dest));
     }
     @Test
     void testDirectionDownLeft(){
         board = new Board(Board.BLANK_BOARD);
         Square start = board.getSquare(2,4);
         Queen queen = new Queen(game.getPlayer1(), start);
         Square dest = board.getSquare(1,5);
         assertEquals(Direction.DOWN_LEFT, queen.getDirection(start, dest));
     }


}
