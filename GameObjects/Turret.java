package GameObjects;

import java.awt.*;

public class Turret extends Mover implements GameObject{
    int width;
    int height;
    Color color;
    public Turret(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        this.color = Color.GREEN;
        this.accel = new Tuple<>(0.0, 0.05);
        this.vel = new Tuple<>(1.0, -2.0);
        this.pos = new Tuple<>(0.0, 200.0);
    }
    @Override
    public void display(Graphics2D g2) {
        g2.setColor(this.color);
        g2.fillRect((int) (this.pos.first + this.width/2), (int)(this.pos.second + this.width/2),
                this.width, this.height);
        this.updateVelocity(this.accel);
        this.updatePosition(this.vel);
    }
}
