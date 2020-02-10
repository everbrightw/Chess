package chess;

import java.util.ArrayList;

public class Board {

    public final static String BLANK_BOARD = "blank";
    public final static String START_BOARD = "start";
    public final static String CUSTOM_BOARD = "custom";
    public static int NUM_PIECES = 16;

    public Square[][] board;

    public Board(String option){
        board = new Square[NUM_PIECES/2][NUM_PIECES/2];
        if(option.equals(BLANK_BOARD)){
            initBlankBoard();
        }
        else if(option.equals(START_BOARD)){
            this.initBoard(new Player(Player.WHITE), new Player(Player.BLACK));
        }
        else if(option.equals(CUSTOM_BOARD)){
            this.initCustomBoard(new Player(Player.WHITE), new Player(Player.BLACK));
        }

    }

    private void initBoard(Player player1, Player player2){
        //initializing white spaces
        initBlankBoard();
        //initializing player's start pieces
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = null;
                if(i == 1){//white pawn
                    piece = new Pawn(player1,board[i][j]);
                }
                if(i == 6){//black pawn
                    piece = new Pawn(player2, board[i][j]);
                }
                Player currPlayer;
                if(i == 0 || i == 7){//white or white other pieces
                    if(i == 0){//white
                        currPlayer = player1;
                    }else{
                        currPlayer = player2;
                    }
                    if(j == 0 || j == 7){//rook
                        piece = new Rook(currPlayer,board[i][j]);
                    }
                    if(j == 1 || j ==6){//knight
                        piece = new Knight(currPlayer,board[i][j]);
                    }
                    if(j == 2 || j == 5){//bishop
                        piece = new Bishop(currPlayer, board[i][j]);
                    }
                    if(j == 3){//queen
                        piece = new Queen(currPlayer, board[i][j]);
                    }
                    if(j == 4){//king
                        piece = new King(currPlayer, board[i][j]);
                    }
                }

                board[i][j].setPiece(piece);

            }
        }

    }

    /**
     * initialize custom board
     * @param player1
     * @param player2
     */
    private void initCustomBoard(Player player1, Player player2){
        initBlankBoard();
        initBoard(player1,player2);
        getSquare(1,7).setPiece(new Witch(player1, getSquare(1,7)));//setting up white wizard
        getSquare(1,0).setPiece(new Vampire(player1,getSquare(1,0)));//setting up white vampire

        getSquare(6,7).setPiece(new Witch(player2, getSquare(6,7)));//setting up black wizard
        getSquare(6,0).setPiece(new Vampire(player2,getSquare(6,0)));//setting up black vampire


    }

    private void initBlankBoard(){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                board[i][j] = new Square(null, i, j);
            }
        }
    }

    //check if square is out of bound
    public boolean doNotContain(Square square){

        if(square.getX() > 7 || square.getX() < 0 || square.getY() > 7 || square.getY() < 0){
            return true;
        }
        return false;
    }

    public Square getSquare(int x, int y){
        if(x > 7 || x < 0 || y > 7 || y < 0){
            return null;
        }
        return board[x][y];
    }

    //print board
    public void printBoard(){
        for(int i = 0; i < 8; i++){
            System.out.println();
            for (int j = 0; j < 8; j++){
                if(board[i][j].getPiece() == null){
                    System.out.print("blank  ");
                }
                else{
                    System.out.print(board[i][j].getPiece().getName()+"  ");
//                    System.out.print(board[i][j].getPiece().getPlayer().getColor() + " ");
                }

            }
        }
    }

    public boolean checkMate(Game game, Piece tryKillKingPiece, King king){

        //make a deep copy

        boolean checkMate = true;
        //find same color pieces and try to kill the piece that is checking king
        ArrayList<Piece> pieces = findSameColorPieces(king.getPlayer().getColor());

        //check if other piece can kill the piece that is trying to kill king
        for (Piece piece:
                pieces) {
            ArrayList<Square> pMoves = piece.generatePossibleMoves(game);
            //try to move
            for (Square dest:
                 pMoves) {

                if(dest.equals(tryKillKingPiece.getSquare())){//tryKillKingPiece can be killed
                    checkMate = false;
                    return checkMate;
                }

            }
        }
        //check if king can escape himself
        ArrayList<Square> kingMoves = king.generatePossibleMoves(game);
        for (Square dest:
             kingMoves) {
            //try to move here
            Piece killedPiece = dest.getPiece();
            Square start = king.getSquare();
            king.move(game, dest);
            if(checkKing(game)==null){
                //reverse move
                king.move(game, start);
                dest.setPiece(killedPiece);
                checkMate = false;
                return checkMate;
            }

        }
        return checkMate;
    }


    /**
     * find all same color pieces with the king that is being checked.
     * @param color
     * @return
     */
    public ArrayList<Piece> findSameColorPieces(String color){

        ArrayList<Piece> pieces = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8;j++){
                Piece piece = getSquare(i,j).getPiece();
                if(piece!=null && piece.getPlayer().getColor().equals(color)){
                    pieces.add(piece);
                }
            }

        }
        return pieces;
    }

    public King[] findKing(Game game){
        Board board = game.getBoard();
        King[] kings = new King[2];
        int count = 0;
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {//iterate every square
                Piece king=board.getSquare(i,j).getPiece();
                if(king!=null && king.getName().equals("king")) {//find king
                    System.out.println("find king ......");
                    kings[count] = (King) king;
                    count++;
                }
            }
        }
        return kings;
    }

    /**
     *
     * @param game
     * @return whether the king is being checked after enemy has moved
     */
    public King checkKing(Game game) {
        Board board = game.getBoard();

        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {//iterate every square
                Piece king=board.getSquare(i,j).getPiece();
                if(king!=null && king.getName().equals("king")) {//find king
                    System.out.println("find king ......");
                    Piece enemy = iterateEnemy(game, board, king);// find enemy who can kill king
                    if (enemy!=null){
                        King retKing = (King) king;
                        retKing.setCheckPiece(enemy);
                        return retKing;
                    }
                }
            }
        }
        return null;
    }

    /**
     * iterate through enemy
     * @param game
     * @param board
     * @param king
     * @return
     */
    private Piece iterateEnemy(Game game, Board board, Piece king) {
        for(int x=0;x<8;x++) {//iterate again
            for(int y=0;y<8;y++) {
                Piece enemy =board.getSquare(x,y).getPiece();
                if(enemy!=null&&!enemy.getPlayer().getColor().equals(king.getPlayer().getColor())){//check if piece is different color

                    ArrayList<Square> enemyMoves = enemy.generatePossibleMoves(game);
                    for (Square square:
                         enemyMoves) {
                        if(square.equals(king.getSquare())){
                            return enemy;
                        }

                    }
                }
            }
        }
        return null;
    }


}
