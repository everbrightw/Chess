package chess;

import controllers.ViewController;
import view.ChessBoardPanel;

import javax.swing.*;
import java.util.logging.Logger;

public class Main {
    /**
     * Program entrance
     */
    public final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//used for debugging

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                Game game = Game.newGame();

                Board board = game.getBoard();
                ViewController vc = new ViewController(game, new ChessBoardPanel(board));
                game.setVc(vc);
                ChessBoardPanel cb =  vc.getChessBoardPanel();

                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);



            }
        };
        SwingUtilities.invokeLater(r);
    }


}
