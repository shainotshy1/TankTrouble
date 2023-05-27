package GameObjects;

import java.awt.*;

public class Wall implements GameObject {
    private double x;
    private double y; // coordinates of top/left corner
    private double w;
    private double h;
    private Rectangle rectangle;
    public Wall(double x, double y, double w, double h, Color color){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rectangle = new Rectangle((int) x, (int) y, (int) w, (int) h, color);
    }

    @Override
    public void display(Graphics2D g2) {
        this.rectangle.display(g2);
    }
}