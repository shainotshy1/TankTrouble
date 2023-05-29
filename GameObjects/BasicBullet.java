package GameObjects;

import Utils.Tuple;

import java.awt.*;

public class BasicBullet extends Bullet {
    public BasicBullet(Tuple<Double, Double> pos, Tuple<Double, Double> vel, Shapes shape) {
        super();
        this.color = Color.BLACK;
        this.pos = pos;
        this.vel = vel;
        this.shape = shape;
        this.direction = 0.0;
    }

    @Override
    public void display(Graphics2D g2) {

    }

}
