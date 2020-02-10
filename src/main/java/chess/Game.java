package chess;

import controllers.ViewController;


public class Game {

    private Player player1, player2;
    private Board board;
    private String playerTurn; // Player.WHITE, Player.Black

    private ViewController vc;

    public Game(Player player1, Player player2, Board board){
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        setPlayerTurn(Player.BLACK);//black is first to act
    }

    //start game
    public static Game newGame(){
        return new Game(new Player(Player.WHITE), new Player(Player.BLACK), new Board(Board.START_BOARD));
    }
    //custom game
    public static Game customGame(){
        return new Game(new Player(Player.WHITE), new Player(Player.BLACK), new Board(Board.CUSTOM_BOARD));
    }

    public static String gameOver(Game game){
        King whiteKing = null;
        King blackKing = null;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8;j++){
                Piece piece = game.board.getSquare(i,j).getPiece();
                if(piece instanceof King){
                    if(piece.getPlayer().getColor().equals(Player.WHITE)){
                        whiteKing = (King) piece;
                    }
                    else {
                        blackKing = (King) piece;
                    }
                }
            }
        }
        if(blackKing == null){
            return  Player.WHITE;//black player lose
        }
        else if(whiteKing == null){
            return Player.BLACK;//white player lose
        }
        else{
            return null; //game has not ended
        }

    }


    public Board getBoard() {
        return board;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Game deepCopyGame(){
        Game game;
        Player player1 = getPlayer1();
        Player player2 = getPlayer2();

        Board board = new Board(Board.BLANK_BOARD);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.getBoard().getSquare(i,j).getPiece()!=null){
                    board.getSquare(i,j).setPiece(this.getBoard().getSquare(i,j).getPiece());

                }
                else{
                    board.getSquare(i,j).setNullPiece();
                }
            }

        }
        game = new Game(player1,player2,board);
        game.setPlayerTurn(this.getPlayerTurn());
        return game;
    }

    public ViewController getVc() {
        return vc;
    }

    public void setVc(ViewController vc) {
        this.vc = vc;
    }
}
