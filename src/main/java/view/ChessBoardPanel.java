package view;

import chess.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ChessBoardPanel {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));//main container
    private JLabel[][] chessBoardSquares = new JLabel[8][8];
    public JButton[][] pieces;

    private JPanel chessBoard;//displaying chessboard
    private JPanel piecesBoard;

    private JPanel informationBoard;//displaying game and player's information
    private JPanel actionBoard;
    public JToolBar tools;// menu tools

    JButton newGameBtn;
    JButton undoBtn;
    JButton resignBtn;
    JButton customBtn;

    public JLabel whiteClock;
    public JLabel blackClock;

    Board board;

    private JLabel message = new JLabel(
            "Messages Displayed Here!");
    private JLabel playerTurn = new JLabel(
            "PlayerTurn Displayed Here");

    private JLabel gameStatus = new JLabel(
            "Game Status Displayed Here"
    );

    public ChessBoardPanel(Board board) {
        initializeGui(board);
    }

    public void initializeGui(Board board) {

        //backend start
        startNewGame(board);
        //set up the main GUI
        initToolBars();

        //setting up informationBoard
        initInformationPanel();

        //Initializing control panels
        initControlPanel();

    }

    private void initInformationPanel() {

        informationBoard = new JPanel();
        informationBoard.setLayout(new GridLayout(2,1));

        whiteClock = new JLabel("0");
        whiteClock.setPreferredSize(new Dimension(300,100));
        whiteClock.setFont(whiteClock.getFont().deriveFont(Font.BOLD,40.0f));
        whiteClock.setBorder(new TitledBorder(new LineBorder(Color.lightGray, 3),
                "white"));

        informationBoard.add(whiteClock,Component.CENTER_ALIGNMENT);

        blackClock = new JLabel("0");
        blackClock.setPreferredSize(new Dimension(300,200));
        blackClock.setFont(blackClock.getFont().deriveFont(Font.BOLD,40.0f));
        blackClock.setBorder(new TitledBorder(new LineBorder(Color.darkGray, 3),
                "black"));

        informationBoard.add(blackClock,Component.CENTER_ALIGNMENT);

        informationBoard.setPreferredSize(new Dimension(300,100));
        gui.add(informationBoard,BorderLayout.EAST);

    }

    /**
     * ImageIcon parser
     * @param i
     * @param j
     * @return an ImageIcon which is ready to be set
     */
    public ImageIcon getPieceImageIcon(int i, int j){

        if(board.getSquare(i,j)!=null && board.getSquare(i,j).getPiece()!=null){
            String pieceName = board.getSquare(i,j).getPiece().getName();
            String pieceColor = board.getSquare(i,j).getPiece().getPlayer().getColor();
            String parsedString = "res/" + pieceColor + "_"+pieceName + ".png";
            return new ImageIcon(parsedString);
        }
        return null;
    }

    /**
     * Return an imageicon by piece
     * @param piece
     * @return
     */
    public ImageIcon getImageByPiece(Piece piece){

        if(piece !=null){

            String pieceName = piece.getName();
            String pieceColor = piece.getPlayer().getColor();
            String parsedString = "res/" + pieceColor + "_"+pieceName + ".png";
            return new ImageIcon(parsedString);

        }
        return null;

    }

    public void initToolBars(){

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        tools = new JToolBar();
        tools.setFloatable(false);
        JButton exitBtn = new JButton("Exit");
        JButton gameBtn = new JButton("Game");
        JButton helpBtn = new JButton("Help");

        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(gameBtn);
        tools.add(exitBtn);
        tools.addSeparator();
        tools.add(helpBtn);
        tools.addSeparator();
        tools.add(message);

        //exit app
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gui);

                int reply = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?", "Quit", JOptionPane.YES_NO_OPTION);

                if (reply == JOptionPane.YES_OPTION) {
                    //JOptionPane.showMessageDialog(null, "HELLO");

                    //close application
                    JOptionPane.showMessageDialog(null, "BYE");
                    System.exit(0);
                }

            }
        });

        //help
        helpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URL("https://github.com/everbrightw/ChessHelp").toURI());
                } catch (Exception exception) {}
            }
        });
    }

    public void  initControlPanel(){
        //setting up control buttons
        actionBoard = new JPanel();
        actionBoard.setLayout(new BoxLayout(actionBoard,BoxLayout.Y_AXIS));//make BoxLayout Algin Vertically

        //four control btns
        newGameBtn = new JButton("New Game"){
            {
                setSize(150,75);
                setMaximumSize(getSize());
            }
        };
        undoBtn = new JButton("Undo"){
            {
                setSize(150,75);
                setMaximumSize(getSize());
            }
        };
        resignBtn = new JButton("Forfeit"){
            {
                setSize(150,75);
                setMaximumSize(getSize());
            }
        };
        customBtn = new JButton("Custom Game"){
            {
                setSize(150,75);
                setMaximumSize(getSize());
            }
        };

        actionBoard.add(newGameBtn);
        actionBoard.add(customBtn);
        actionBoard.add(undoBtn);
        actionBoard.add(resignBtn);

        actionBoard.setPreferredSize(new Dimension(150,100));

        gui.add(actionBoard,BorderLayout.WEST);

    }

    //add control buttons click listener: NewGame, SaveGame, Undo, Resign
    public void addControlButtonClickListener(ActionListener listenForBoardButtonClick){
        newGameBtn.addActionListener(listenForBoardButtonClick);
        customBtn.addActionListener(listenForBoardButtonClick);
        undoBtn.addActionListener(listenForBoardButtonClick);
        resignBtn.addActionListener(listenForBoardButtonClick);
    }

    public void addMoveListener(ActionListener moveListener){

        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                pieces[ii][jj].addActionListener(moveListener);

            }
        }
    }
    public ImageIcon setImageIcon(JButton b, ImageIcon icon){
        if(icon!=null){
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(40,40,Image.SCALE_SMOOTH);//scaling image icon to fit button
            icon = new ImageIcon(newimg);
            b.setIcon(icon);
        }
        return icon;


    }

    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }

    public JPanel getPiecesBoard() {
        return piecesBoard;
    }
    public Square findSquare(JButton j){
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                if(pieces[ii][jj].equals(j)){
                    //found the button, return square
                    System.out.println("x: "+jj+"y: "+ ii);
                    return board.getSquare(jj,ii);
                }
            }
        }
        return null;
    }
    public void startNewGame(Board board){

        this.board = board;
        piecesBoard = new JPanel(new GridLayout(0,8));
        pieces = new JButton[8][8];

        gui.add(piecesBoard);
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                JLabel j = new JLabel();//for the dragging event

                b.setMargin(buttonMargin);

                ImageIcon icon = getPieceImageIcon(ii,jj);
                if(icon!=null){
                    Image img = icon.getImage();
                    Image newimg = img.getScaledInstance(40,40,Image.SCALE_SMOOTH);//scaling image icon to fit button
                    icon = new ImageIcon(newimg);
                    b.setIcon(icon);
                }


                b.setPreferredSize(new Dimension(64,64));

                //setting chess board background to white and black
                if ((jj % 2 == 1 && ii % 2 == 1)
                        || (jj % 2 == 0 && ii % 2 == 0)) {

                    b.setBackground(Color.WHITE);
                    //make background visible
                    b.setContentAreaFilled(false);
                    b.setOpaque(true);
                    b.setBorderPainted(false);


                } else {

                    b.setBackground(Color.GRAY);
                    //make background visible
                    b.setContentAreaFilled(false);
                    b.setOpaque(true);
                    b.setBorderPainted(false);

                }

                pieces[jj][ii] = b;

            }
        }
        // add squares to chess Board
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                piecesBoard.add(pieces[jj][ii]);
            }
        }
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    //switch player turn, disable jbutton if its not this player's current turn
    public void switchPlayerTurn(String currPlayer){
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                Piece piece = findSquare(pieces[jj][ii]).getPiece();
                if(piece!=null){
                    pieces[jj][ii].setEnabled(piece.getPlayer().equals(currPlayer));
                }

            }
        }
    }
    //restore jbutton to its default color(white or black) after highlighting
    public Color getDefaultColor(JButton jButton){
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                if(pieces[jj][ii].equals(jButton)){
                    if((jj % 2 == 1 && ii % 2 == 1)
                            || (jj % 2 == 0 && ii % 2 == 0)){
                        return Color.WHITE;
                    }
                    else{
                        return Color.GRAY;
                    }
                }

            }
        }
        return null;
    }

    public void restoreHighlightedSquares(ArrayList<Square> squares){
        for (Square square:
             squares) {
                JButton jButton = pieces[square.getY()][square.getX()];
                jButton.setBackground(getDefaultColor(jButton));
        }
    }

    /**
     * set information board to reflect which player is in current turn
     * @param nextPlayer
     */
    public void setTurn(int nextPlayer) {
        String currPlayer;
        nextPlayer ++;
        if(nextPlayer % 2 != 0){
            currPlayer = Player.BLACK;
            blackClock.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 3),
                    "black"));
            whiteClock.setBorder(new TitledBorder(new LineBorder(Color.white, 3),
                    "white"));

        }else{
            currPlayer = Player.WHITE;
            whiteClock.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 3),
                    "white"));
            blackClock.setBorder(new TitledBorder(new LineBorder(Color.lightGray, 3),
                    "black"));
        }

    }

    public void updateBlackScore(int score){
        blackClock.setText(""+score);
    }

    public void updateWhiteScore(int score){
        whiteClock.setText("" + score);
    }
}

