package chess;

public class Player {

    public static String WHITE = "white";
    public static String BLACK = "black";

    private String color;//white or black

    public Player(String color){

        this.color = color;

    }

    public String getColor() {
        return color;
    }


}
