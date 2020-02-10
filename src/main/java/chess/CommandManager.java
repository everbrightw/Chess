package chess;

import controllers.ViewController;
import view.ChessBoardPanel;

import javax.swing.*;
import java.util.Stack;

/**
 * Class for manging undo command
 */
public class CommandManager {

    public static Stack<Command> undos = new Stack<Command>();//undo stack


    public static void printStack(){

        while(!undos.empty()){
            Command command = undos.pop();
            int x = command.getStart().getX();
            int y = command.getStart().getY();

            int _x = command.getDest().getX();
            int _y = command.getDest().getY();

            String movedpieceName = command.getMovedPiece().getName();
            String killedPiece;
            if(command.getKilledPiece()!=null){
                killedPiece = command.getKilledPiece().getName();

            }else{
                killedPiece = "blank";
            }
            System.out.println("MovedName: "+movedpieceName + "(" + x + ", " + y + ")" + killedPiece + "(" + _x + ", " + _y + ")");
        }
    }

    public static Command popCommand(){
        if(canUndo()){
            return undos.pop();
        }
        return null;
    }

    public static boolean canUndo(){
        return !undos.empty();
    }

    /**
     * excute undo command
     * @param viewController
     * @param undoCommand
     * @return True if undo executed
     */
    public static boolean executeUndo(ViewController viewController, Command undoCommand){
        //restore
        if(undoCommand!=null){

            //set start square
            undoCommand.getStart().setPiece(undoCommand.getMovedPiece());

            //set destination square
            undoCommand.getDest().setPiece(undoCommand.getKilledPiece());

            //set square
            undoCommand.getMovedPiece().setSquare(undoCommand.getStart());

            if(undoCommand.getKilledPiece() != null){
                undoCommand.getKilledPiece().setSquare(undoCommand.getDest());
            }

            //set image icon
            undoCommand.getStartButton().setIcon(undoCommand.getStartImage());
            undoCommand.getDestButton().setIcon(undoCommand.getDestImage());

            //set player turn
            if(viewController.getGame().getPlayerTurn() == Player.BLACK){
                viewController.getGame().setPlayerTurn(Player.WHITE);
            }
            else{
                viewController.getGame().setPlayerTurn(Player.BLACK);
            }
            //special case for pawn's undo
            pawnFirstStepAdjust(viewController, undoCommand);

        }
        else{
            JOptionPane.showMessageDialog(null, "Can't Undo");

        }
        return false;
    }

    /**
     * adjust pawn's first step
     * @param viewController
     * @param undoCommand
     */
    private static void pawnFirstStepAdjust(ViewController viewController, Command undoCommand) {
        Piece piece = undoCommand.getMovedPiece();
        if(piece instanceof Pawn && ((Pawn) piece).getMoveDistance() == 1){//if pawn was moving his first step
                                                                            // set his first step to true again
            ((Pawn) undoCommand.getMovedPiece()).setFirstStep(true);
            ((Pawn) piece).setMoveDistance(((Pawn) piece).getMoveDistance()-1);//decrease pawn's move distance for undo®
        }

        viewController.nextPlayer--;
        viewController.getChessBoardPanel().setTurn(viewController.nextPlayer);
    }

    /**
     * Create new game
     * @param game
     * @param chessBoardPanel
     */
    public static void createNewGame(Game game, ChessBoardPanel chessBoardPanel,String gameType){

        //creating new games object and set it to view controller
        game = linkGame(game, gameType, chessBoardPanel);

        //repainting game user interface here
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

                if(game.getBoard().getSquare(i,j).hasChess()){
                    chessBoardPanel.setImageIcon(chessBoardPanel.pieces[j][i], chessBoardPanel.getPieceImageIcon(i,j));
                }else{
                    chessBoardPanel.pieces[j][i].setIcon(null);
                }
            }

        }
        //initialize player turn
        game.getVc().nextPlayer = 0;
        game.getVc().currPlayer = Player.BLACK;
        chessBoardPanel.setTurn(0);

        undos.removeAll(undos);//remove undo commands

    }


    /**
     * set new game and chessBoardPanel to ViewController to start a game
     * @param game
     * @param gameType
     * @param chessBoardPanel
     * @return current new game
     */
    private static Game linkGame(Game game, String gameType, ChessBoardPanel chessBoardPanel) {

        ViewController vc = game.getVc();
        //create custom or normal new game
        if(gameType.equals(Board.CUSTOM_BOARD)){
            game = Game.customGame();
        }
        else{
            game = Game.newGame();
        }

        vc.setGame(game);
        game.setVc(vc);
        chessBoardPanel.setBoard(game.getBoard());
        return game;
    }


}
