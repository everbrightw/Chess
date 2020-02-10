package chess;

import javax.swing.*;

public class Command {

    private Square start;
    private Square dest;
    private Piece movedPiece;
    private Piece killedPiece;
    private JButton startButton;
    private JButton destButton;

    private ImageIcon startImage;
    private ImageIcon destImage;

    public Command(){
    };

    public Command(Piece movedPiece, Square start, Square dest, Piece killedPiece,
                   JButton startButton, JButton destButton, ImageIcon startImage, ImageIcon destImage){
        this.movedPiece = movedPiece;
        this.start = start;
        this.dest = dest;
        this.killedPiece = killedPiece;
        this.startButton = startButton;
        this.destButton = destButton;
        this.startImage = startImage;
        this.destImage = destImage;

    }

    public Piece getKilledPiece() {
        return killedPiece;
    }

    public void setDest(Square dest) {
        this.dest = dest;
    }

    public Square getDest() {
        return dest;
    }

    public void setKilledPiece(Piece killedPiece) {
        this.killedPiece = killedPiece;
    }

    public Square getStart() {
        return start;
    }

    public void setStart(Square start) {
        this.start = start;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public void setMovedPiece(Piece movedPiece) {
        this.movedPiece = movedPiece;
    }

    public JButton getDestButton() {
        return destButton;
    }

    public void setDestButton(JButton destButton) {
        this.destButton = destButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public ImageIcon getDestImage() {
        return destImage;
    }

    public ImageIcon getStartImage() {
        return startImage;
    }

    public void setDestImage(ImageIcon destImage) {
        this.destImage = destImage;
    }

    public void setStartImage(ImageIcon startImage) {
        this.startImage = startImage;
    }
}

