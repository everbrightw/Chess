package chess;

import java.util.logging.Level;

/**
 * Custom Chess Piece
 * Witch can jump over a chess piece which is right next to her to any direction
 * and if she capture a chess piece after moving, the reward would be transforming herself to queen
 */
public class Witch extends Piece implements Moveable{

    public static final String _NAME = "witch";

    public Witch(Player player, Square square){
        setPlayer(player);
        setSquare(square);
        setName(Witch._NAME);
        square.setPiece(this);
    }

    @Override
    public boolean move(Game game, Square dest){

        if (moveBasicCheck(game, dest)) return false;

        if(canMove(game.getBoard(),this.getSquare(), dest)){
            if(dest.hasChess()){
                //do the witch magic(upgrade itself)

                game.getVc().witchTransform(getSquare(),dest);
            }
            else{
                Square prevSquare = this.getSquare();
                this.setSquare(dest);
                dest.setPiece(this);
                prevSquare.setNullPiece();
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean canMove(Board board, Square start, Square dest) {

        if(!passBasicMoveRules(board, start, dest)){
            return false;
        }

        int dfX = Math.abs(start.getX() - dest.getX());
        int dfY = Math.abs(start.getY() - dest.getY());
        //the destination can only be within two square distance

        //witch moving rules
        //start examining if the move satisfies the jumping condition
        int x = start.getX();
        int y = start.getY();
        Direction direction = getDirection(start, dest);//get the moving direction
        //determine if the square that is right next to witch on the given direction has a chess piece
        if(direction == Direction.UP){
            if(dfY != 0 || dfX !=2){
                return false;
            }
            return board.getSquare(x+1, y).hasChess();
        }
        if(direction == Direction.DOWN){
            if(dfY != 0 || dfX !=2){
                return false;
            }
            return board.getSquare(x-1, y).hasChess();
        }
        if(direction == Direction.LEFT){
            if(dfX != 0 || dfY != 2){
                return false;
            }
            return board.getSquare(x, y+1).hasChess();
        }
        if(direction == Direction.RIGHT){
            if(dfX != 0 || dfY != 2){
                return false;
            }
            return board.getSquare(x, y-1).hasChess();
        }
        if(direction == Direction.UP_LEFT){
            if(dfX != 2 || dfY != 2){
                return false;
            }
            return board.getSquare(x+1,y+1).hasChess();
        }
        if(direction == Direction.UP_RIGHT){
            if(dfX != 2 || dfY != 2){
                return false;
            }
            return board.getSquare(x+1,y-1).hasChess();
        }
        if(direction == Direction.DOWN_RIGHT){
            if(dfX != 2 || dfY != 2){
                return false;
            }
            return board.getSquare(x-1,y+1).hasChess();
        }
        if(direction == Direction.DOWN_LEFT){
            if(dfX != 2 || dfY != 2){
                return false;
            }
            return board.getSquare(x-1,y-1).hasChess();
        }

        return false;

    }

    //witch  do not need to check piece on it's path since it is moving by jumping
    @Override
    public boolean hasPieceOnThePath(Board board, Square dest) {
        return false;
    }
}
