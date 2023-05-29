package GameObjects;

import Utils.Tuple;

import java.awt.*;

public class MissileBullet extends Bullet {
    public MissileBullet(Tuple<Double, Double> pos, Tuple<Double, Double> vel, Tuple<Integer, Integer> dim) {
        super();
        this.color = Color.RED;
        this.pos = pos;
        this.vel = vel;
        this.dim = dim;
        this.direction = 0.0;
    }

    @Override
    public void display(Graphics2D g2) {
        g2.setColor(this.color);
        g2.fillRect((int)Math.round(this.pos.first), (int)Math.round(this.pos.second), this.dim.first, this.dim.second);
    }
}
