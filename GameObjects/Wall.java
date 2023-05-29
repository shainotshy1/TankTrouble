package GameObjects;

import Colliders.BoxCollider;
import Colliders.Collider;
import Utils.Vector2d;

import java.awt.*;

public class Wall implements GameObject {
    private double x;
    private double y; // coordinates of top/left corner
    private double w;
    private double h;
    private Rectangle rectangle;
    private BoxCollider collider;
    public Wall(double x, double y, double w, double h, Color color){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rectangle = new Rectangle((int) x, (int) y, (int) w, (int) h, color);
        Vector2d center = new Vector2d(x + w / 2, y + h / 2);
        collider = new BoxCollider(center, w, h, 0);
    }

    public boolean colliding(Collider other) {
        return collider.colliding(other);
    }

    @Override
    public void display(Graphics2D g2) {
        this.rectangle.display(g2);
    }
}