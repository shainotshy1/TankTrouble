package GameObjects;

public class Ball {
    int x = 0; //position x
    int y = 0; // position y
    int vx = 0; // velocity x
    int vy = 0; // velocity y
    String style = "bullet";
    public Ball(int x,int y, int vx, int vy, String style){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.style = style;
    }
}
