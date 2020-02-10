package chess;

import javax.swing.*;
import java.util.ArrayList;
import java.util.logging.Level;


/**
 * Ever chess piece's general functionality
 */
public abstract class Piece implements Moveable{


    private Player player;// white or black
    private Square square;
    private String name;//piece string name

    JButton jbutton;

    // overridden by other child pieces class.
    public abstract boolean canMove(Board board, Square start, Square dest);//check if this chess can move from a square to another one

    //constructors;
    public Piece() {
    }

    public Piece(Square square, Player player) {

        this.player = player;
        this.square = square;

    }

    public Piece(Player player) {

        this.player = player;

    }

    /**
     * moves pieces from it's current square to the destination square after checking child piece's moving rules
     * @param game game model
     * @param dest destination square
     * @return true if move was successful
     */
    public boolean move(Game game, Square dest) {

        if (moveBasicCheck(game, dest)) return false;

        if(this.canMove(game.getBoard(),this.getSquare(),dest)){

            Square prevSquare = this.getSquare();
            this.setSquare(dest);
            dest.setPiece(this);
            prevSquare.setNullPiece();
            return true;
        }


        return false;
    }

    public boolean hasSameColorWith(Piece piece){
        return (this.getPlayer().getColor().
                equals(piece.getPlayer().getColor()));
    }

    public boolean passBasicMoveRules(Board board, Square start, Square dest){
        if(dest.hasChess()){
            if(start!=null && start.getPiece().hasSameColorWith(dest.getPiece())){
                Main.LOGGER.log(Level.INFO, "You Can Not Kill Your Own Chess Piece");
                return false;
            }

        }
        //check if dest is out of bound
        if(board.doNotContain(dest)){
            Main.LOGGER.log(Level.INFO, "destination is out of board");
            return false;
        }
        return true;

    }

    public ArrayList<Square> generatePossibleMoves(Game game) {
        ArrayList<Square> possibleMoves = new ArrayList<>();
        Board board = game.getBoard();
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                Square square = board.getSquare(i,j);
                if(canMove(board,this.getSquare(),square)){
                    possibleMoves.add(square);
                }
            }

        }
        return possibleMoves;
    }


    /**
     *
     * @param start start square
     * @param dest  destination square
     * @return chess pieces's moving direction
     */
    public Direction getDirection(Square start, Square dest){

        if(start.getX() < dest.getX()  && start.getY() == dest.getY()){
            return Direction.UP;
        }else if(dest.getX() < start.getX()  && start.getY() == dest.getY()){
            return Direction.DOWN;
        }else if(start.getX() < dest.getX()  && start.getY() < dest.getY()){
            return Direction.UP_LEFT;
        }else if(start.getX() > dest.getX() && start.getY() > dest.getY() ){
            return Direction.DOWN_LEFT;
        }else if(dest.getX() > start.getX() && start.getY() > dest.getY()){
            return Direction.UP_RIGHT;
        }else if((dest.getX() < start.getX()) && (start.getY() < dest.getY())){
            return Direction.DOWN_RIGHT;
        }else if(start.getX() == dest.getX() && start.getY() > dest.getY()){
            return Direction.RIGHT;
        }else if(start.getX() == dest.getX() && start.getY() < dest.getY()){
            return Direction.LEFT;
        }
        return null;
    }// return direction of moving




    //Logging Warning
    public void printCanNotMoveToWarning(Square dest){
        Main.LOGGER.log(Level.INFO, this.getName() + "can not move from square" + "(" + this.square.getX() + ", "
                + this.square.getY() + ")" + "to" + "(" + dest.getX() + ", " + dest.getY() + ")");
    }




    //getters and setters
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  static void excuteMove(Square start, Square end) {
        end.setHasChess(true);
        start.getPiece().setSquare(end);//update coordinates
        end.setPiece(start.getPiece());
        start.setHasChess(false);
        start.setPiece(null);
    }

    public int getX(){
        return this.getSquare().getX();
    }
    public int getY(){
        return this.getSquare().getY();
    }

    public JButton getJbutton() {
        return jbutton;
    }

    public void setJbutton(JButton jbutton) {
        this.jbutton = jbutton;
    }

    public boolean checkStraight(Board board, Square dest, int x, int y, int destX,int destY){
        System.out.println("193");
        if(getDirection(getSquare(),dest) == Direction.UP){
            System.out.println("47");
            for(int i = 1; i < Math.abs(x-destX); i ++){
                if(board.getSquare(x+i, y) == null){
                    System.out.println("198");
                    return false;
                }
                if(board.getSquare(x+i, y).hasChess()){
                    return true;
                }
            }
        }
        else if(getDirection(getSquare(),dest) == Direction.DOWN){
            System.out.println("54");
            for(int i =1; i < Math.abs(x - destX);i++){
                if(board.getSquare(x - i, y) == null){
                    System.out.println("198");
                    return false;
                }
                if(board.getSquare(x-i, y).hasChess()){
                    return true;
                }
            }
        }
        else if(getDirection(getSquare(), dest) == Direction.LEFT){
            System.out.println("63");
            for(int i = 1; i < Math.abs(destY - y); i++){
                if(board.getSquare(x, y + i) == null){
                    return false;
                }
                if(board.getSquare(x, y+i).hasChess()){
                    return true;
                }
            }
        }
        else if(getDirection(getSquare(),dest) == Direction.RIGHT){
            System.out.println("71");

            for(int i = 1; i < Math.abs(destY - y); i++){
                if(board.getSquare(x, y - i) == null){
                    System.out.println("232");
                    return false;
                }
                if(board.getSquare(x, y - i).hasChess()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkDiagonal(Board board, Square dest, int x, int y, int destX){
        if(getDirection(getSquare(),dest) == Direction.DOWN_RIGHT){//down left
            for(int i =1; i < Math.abs(destX - x); i++){
                if(board.getSquare(x-i, y+i)==null){//out of bound
                    return false;
                }
                if(board.getSquare(x-i, y+i).hasChess()){
                    return true;
                }
            }
        }
        else if(getDirection(getSquare(),dest) == Direction.DOWN_LEFT){//up left
            for(int i = 1; i <Math.abs(destX - x); i++){
                if(board.getSquare(x-i, y-i) ==null){//out of bound
                    return false;
                }
                if(board.getSquare(x-i, y-i).hasChess()){
                    return true;
                }
            }
        }
        else if(getDirection(getSquare(),dest) == Direction.UP_RIGHT){//down right
            for(int i=1;i<Math.abs(x-destX);i++){
                if(board.getSquare(x+i, y-i) ==null){//out of bound
                    return false;
                }
                if(board.getSquare(x+i, y-i).hasChess()){
                    return true;
                }
            }
        }
        else if(getDirection(getSquare(),dest) == Direction.UP_LEFT){//up left
            for(int i=1;i<Math.abs(x-destX);i++){
                if(board.getSquare(x+i, y+i) ==null){//out of bound
                    return false;
                }
                if(board.getSquare(x+i, y+i).hasChess()){
                    return true;
                }
            }
        }
        return false;

    }
    public boolean moveBasicCheck(Game game, Square dest) {
        if(dest == null || game.getBoard().getSquare(dest.getX(), dest.getY()) == null){
            Main.LOGGER.log(Level.INFO, "Destination Out Of Bound");
            return true;
        }

        if(!this.getPlayer().getColor().equals(game.getPlayerTurn())){
            System.out.println("Not Your Turn");
            return true;
        }
        return false;
    }


}
