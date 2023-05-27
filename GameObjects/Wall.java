package GameObjects;

public class Wall {
    int x = 0;
    int y = 0; // coordinates of top/left corner
    int length = 10;
    String orientation = "x"; // x: horizontal, y: vertical
    public Wall(int x, int y, int length, String orientation){
        this.x = x;
        this.y = y;
        this.length = length;
        this.orientation = orientation;
    }
}