package GameObjects;

import java.awt.*;

public class Turret extends Mover implements GameObject{
    public Turret(Tuple<Double, Double> pos, Tuple<Double, Double> vel, Tuple<Double, Double> accel,
                  Tuple<Integer, Integer> dim, Color color) {
        super();
        this.width = dim.first;
        this.height = dim.second;
        this.color = color;
        this.pos = pos;
        this.vel = vel;
        this.accel = accel;
    }
    @Override
    public void display(Graphics2D g2) {
        g2.setColor(this.color);
        g2.fillRect((int) Math.round(this.pos.first), (int) Math.round(this.pos.second), this.width, this.height);
        this.updateVelocity(this.accel);
        this.updatePosition(this.vel);
    }
}
