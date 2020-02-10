
# Chess

A Chess game implemented with Java Swing UI.

## Install

You can install it by clone this repository and run it on intellij/Eclipse   
Or build it to a runnable jar file by(intellij)
```
File -> Project Structure -> Project Settings -> Artifacts -> Click green plus sign -> Jar -> From modules with dependencies...
```
And run the jar file.

## Game User Interface

![Alt text](screenshots/boardScreenShot.png?raw=true "GUI")

### How To Play
Simply click New Game button and the game will start.
The score board on the right side will highlight which player's current turn is.
And each play alternatively move their piece on the board by clicking the piece.

![Alt text](screenshots/playerTurnExample.png?raw=true "GUI")

## Highlight Possible Move Feature
Possible moves for selected piece will be highlighted on the board.
![Alt text](screenshots/highlightExample.png?raw=true "GUI")
![Alt text](screenshots/highlightExample2.png?raw=true "GUI")

##Undo Feature
It allows players to undo their moves.

### Custom Piece

We also have two custom pieces that can add to the chess board!
Simply click Custom Game.


### Custom Piece: Vampire

Move rules:  
1.Move like a knight  
2.Move two-square vertically or horizontally  

Special skill:   
After killing enemy's piece, vampire can convert the killed piece to
its own color piece and place it at the vampire's
last location on board

Sample:
![Alt text](screenshots/vampire.png?raw=true "GUI")
![Alt text](screenshots/vampire1.png?raw=true "GUI")



### Custom Piece: Witch

Move rules:  
1.It can only jump over a piece.(just like the board game Checkers)

Special skill:   
After killing enemy's piece, witch automatically
convert herself to a queen.  

Sample:
![Alt text](screenshots/witch.png?raw=true "GUI")
![Alt text](screenshots/witch1.png?raw=true "GUI")
