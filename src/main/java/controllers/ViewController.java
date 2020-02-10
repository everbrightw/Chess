package controllers;



import chess.*;
import view.ChessBoardPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewController {

    private Game game;
    private ChessBoardPanel chessBoardPanel;
    public int nextPlayer=0;
    private boolean firstMove = true;

    private int blackScore = 0;
    private int whiteScore = 0;

    public String currPlayer = Player.BLACK;//black player first to act

    public ViewController(Game game, ChessBoardPanel chessBoardPanel){
        this.game = game;
        this.chessBoardPanel = chessBoardPanel;

        this.chessBoardPanel.addControlButtonClickListener(new ControlButtonClickListener());//listen for control buttons click
        this.chessBoardPanel.addMoveListener(new MoveListener());//listen for pieces' movement

    }



    /**
     * Listen for control panel click
     */
    private class ControlButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equals("New Game")){

                int reply = JOptionPane.showConfirmDialog(null,
                        "Create New Game?", "New Game", JOptionPane.YES_NO_OPTION);

                if (reply == JOptionPane.YES_OPTION) {

                    CommandManager.createNewGame(game, chessBoardPanel,Board.START_BOARD);
                    //restart a new game
                    firstMove = true;
                }
                currPlayer = Player.BLACK;

            }
            else if(e.getActionCommand().equals("Undo")){

                //undo last move
                Command undoCommand = CommandManager.popCommand();//last command
                CommandManager.executeUndo(game.getVc(), undoCommand);

            }
            else if(e.getActionCommand().equals("Forfeit")){
                //Forfeit Game
                int reply = JOptionPane.showConfirmDialog(null,
                        currPlayer.toUpperCase() + " Are you sure you want to forfeit?",
                        "Forfeit", JOptionPane.YES_NO_OPTION);

                if (reply == JOptionPane.YES_OPTION) {
                    //create custom game

                    String winningPlayer;
                    if(currPlayer.equals(Player.BLACK)){
                        winningPlayer = Player.WHITE;
                    }
                    else{
                        winningPlayer = Player.BLACK;
                    }

                    JOptionPane.showMessageDialog(null,currPlayer.toUpperCase()+ " Forfeit game.\n"
                            + winningPlayer.toUpperCase()+ " Win!");

                    updateScore(winningPlayer);
                    CommandManager.createNewGame(game, chessBoardPanel,Board.START_BOARD);
                    firstMove = true;
                }


            }
            else if(e.getActionCommand().equals("Custom Game")){
                int reply = JOptionPane.showConfirmDialog(null,
                        "Create New Custom Game?", "Custom Game", JOptionPane.YES_NO_OPTION);

                if (reply == JOptionPane.YES_OPTION) {
                    //create custom game
                    CommandManager.createNewGame(game, chessBoardPanel,Board.CUSTOM_BOARD);
                    firstMove = true;
                }
                currPlayer = Player.BLACK;

            }

        }
    }


    /**
     * Listen for piece board jButton click
     */
    private class MoveListener implements ActionListener {

        JButton pieceToMoveButton = null;//refer to the piece that is going to be moved

        ArrayList<Square> highlightedSquares = new ArrayList<>();//square need to be highlighted for possible moves

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton currButton = (JButton) e.getSource();
            Piece selectedPiece = chessBoardPanel.findSquare(currButton).getPiece();
            if(selectedPiece!=null
                    && !selectedPiece.getPlayer().getColor().equals(currPlayer)
                    && pieceToMoveButton==null){
                JOptionPane.showMessageDialog(null, "Not Your Turn");
                return;
            }

            if(firstMove){//for creating new game
                //player have not select a piece
                pieceToMoveButton = null;
                firstMove = false;
            }
            if(pieceToMoveButton == null){
                //selected button
                //set current button to pieceToMoveButton and ready to listen for next button click
                pieceToMoveButton = currButton;

                //highlight current button
                pieceToMoveButton.setBackground(new Color(153,204,255));
                //highlight possible moves
                highlightPossibleMoves();
            }
            else{//player has selected a destination and we try to move this piece to destination

                chessBoardPanel.restoreHighlightedSquares(highlightedSquares);//restore default board color for last highlight
                highlightedSquares.removeAll(highlightedSquares);//possible moves highlight

                Square start = chessBoardPanel.findSquare(pieceToMoveButton);
                if(!start.hasChess()){
                    //illegal selection reselect
                    pieceToMoveButton.setBackground(chessBoardPanel.getDefaultColor(pieceToMoveButton));
                    pieceToMoveButton = null;
                }
                else{//try to move piece

                    Square dest = chessBoardPanel.findSquare(currButton);

                    //get potential move piece and killed piece and set for undo command
                    Piece movedPiece = start.getPiece();
                    Piece killedPiece = dest.getPiece();

                    //try to move piece in data model
                    boolean moved = start.getPiece().move(game, dest);

                    if(moved){
                        //move successful

                        Command command = updateGUI(currButton, start, dest, movedPiece, killedPiece);

                        //remove highlight
                        pieceToMoveButton.setBackground(chessBoardPanel.getDefaultColor(pieceToMoveButton));

                        //alternate player turn and
                        updatePlayerTurn(nextPlayer);

                        //push command in to stack, undo stack
                        CommandManager.undos.push(new Command(movedPiece, start, dest, killedPiece,
                                pieceToMoveButton, currButton,command.getStartImage(), command.getDestImage()));

                        //next iteration
                        pieceToMoveButton = null;
                        chessBoardPanel.setTurn(nextPlayer);
                    }
                    else{//move failed

                        //reset jButton color to default color
                        pieceToMoveButton.setBackground(chessBoardPanel.getDefaultColor(pieceToMoveButton));
                        pieceToMoveButton = null;
                    }

                    //check king
                    King checkedKing = game.getBoard().checkKing(game);
                    if(checkedKing!=null){
                        //alert player
                        showCheckMateDialog(checkedKing);
                    }

                    //check game end condition
                    String end = Game.gameOver(game);
                    if(end != null){
                        //game ended
                        updateScoreBoard(end);

                    }

                    firstMove = false;
                }

            }

        }

        private Command updateGUI(JButton currButton, Square start, Square dest, Piece movedPiece, Piece killedPiece) {
            Command command = new Command();
            //get image icon ready to set for undo command

            if(movedPiece instanceof Vampire){//vampire special case
                //set startImage
                if(killedPiece == null){
                    chessBoardPanel.setImageIcon(currButton, (ImageIcon) pieceToMoveButton.getIcon());
                    pieceToMoveButton.setIcon(null);
                }
                else{//deal with vampire ui setting after killed one piece
                    JButton startButton = chessBoardPanel.pieces[start.getY()][start.getX()];//get vampire's original button
                    chessBoardPanel.setImageIcon(startButton, chessBoardPanel.getImageByPiece(killedPiece));

                    JButton destButton = chessBoardPanel.pieces[dest.getY()][dest.getX()];
                    chessBoardPanel.setImageIcon(destButton,chessBoardPanel.getImageByPiece(movedPiece));
                }

                command.setDestImage( chessBoardPanel.getImageByPiece(movedPiece) );
                command.setStartImage( chessBoardPanel.getImageByPiece(killedPiece));
            }
            else if(movedPiece instanceof Witch){//witch special case

                if(killedPiece == null){
                    chessBoardPanel.setImageIcon(currButton, (ImageIcon) pieceToMoveButton.getIcon());
                    pieceToMoveButton.setIcon(null);
                }
                else{//deal with witch ui setting after killed one piece
                    JButton startButton = chessBoardPanel.pieces[dest.getY()][dest.getX()];//get vampire's original button
                    chessBoardPanel.setImageIcon(startButton, chessBoardPanel.getImageByPiece(dest.getPiece()));
                    System.out.println(dest.getPiece().getName());
                    pieceToMoveButton.setIcon(null);
                }

                command.setDestImage( chessBoardPanel.getImageByPiece(movedPiece) );
                command.setStartImage( chessBoardPanel.getImageByPiece(killedPiece));
            }
            else{//normal piece situation
                command.setDestImage((ImageIcon) currButton.getIcon());
                command.setStartImage(chessBoardPanel.setImageIcon(currButton,
                        (ImageIcon) pieceToMoveButton.getIcon()));
                pieceToMoveButton.setIcon(null);
            }
            return command;
        }


        /**
         * highlight selected chess blue background on board
         */
        private void highlightPossibleMoves() {
            ArrayList<Square> possibleMoves = chessBoardPanel.findSquare(pieceToMoveButton).getPiece().generatePossibleMoves(game);
            for (Square square:
                    possibleMoves) {
                chessBoardPanel.pieces[square.getY()][square.getX()].setBackground(new Color(153,204,255));
                highlightedSquares.add(square);
            }
        }

        /**
         * switch player turn according to number of turns
         * @param numTurn
         */
        public void updatePlayerTurn(int numTurn){

            nextPlayer ++;
            if(nextPlayer % 2 == 0){
                currPlayer = Player.BLACK;
            }else{
                currPlayer = Player.WHITE;
            }

            //alternate turn and determine if it is check mate
            game.setPlayerTurn(currPlayer);
        }
    }

    private void showCheckMateDialog(King checkedKing) {
        JOptionPane.showMessageDialog(null, "Check");
        //check for check mate
        if(game.getBoard().checkMate(game, checkedKing.getCheckPiece(), checkedKing) && checkedKing.getPlayer().getColor().equals(currPlayer)){
            JOptionPane.showMessageDialog(null, "CheckMate");
        }
    }

    private void updateScoreBoard(String end) {
        JOptionPane.showMessageDialog(null, "Game Over "+end.toUpperCase() + " Win!");
        int reply = JOptionPane.showConfirmDialog(null,
                "Do you want to start a new game? ", "Game Over", JOptionPane.YES_NO_OPTION);

        if (reply == JOptionPane.YES_OPTION) {
            //create new game
            CommandManager.createNewGame(game, chessBoardPanel, Board.START_BOARD);
        }

        //set score
        updateScore(end);
    }

    private void updateScore(String end) {
        if(end == Player.BLACK){
            blackScore+=1;

            chessBoardPanel.updateBlackScore(blackScore);
        }
        else {
            whiteScore+=1;
            chessBoardPanel.updateWhiteScore(whiteScore);
        }
    }

    /**
     * Vampire Transform Method
     * @param start
     * @param dest
     */
    public void vampireTransform(Square start, Square dest) {
        Vampire vampire = (Vampire) start.getPiece();
        Piece enemy = dest.getPiece();

        start.setPiece(enemy);
        enemy.setSquare(start);
        enemy.setPlayer(vampire.getPlayer());

        dest.setPiece(vampire);
        vampire.setSquare(dest);

        game.getBoard().printBoard();

    }

    public void witchTransform(Square start, Square dest) {

        //update game model
        dest.setPiece(new Queen(start.getPiece().getPlayer(),dest));
        start.setPiece(null);//removing current piece

        game.getBoard().printBoard();

    }


    public ChessBoardPanel getChessBoardPanel() {
        return chessBoardPanel;
    }

    public void setChessBoardPanel(ChessBoardPanel chessBoardPanel) {
        this.chessBoardPanel = chessBoardPanel;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


}
