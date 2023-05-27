package GameObjects;

import java.awt.*;

public class Body extends Mover implements GameObject {
    Color color;
    int width;
    int height;
    public Body(int width, int height){
        super();
        this.accel = new Tuple<>(0.0, 0.05);
        this.vel = new Tuple<>(1.0, -2.0);
        this.pos = new Tuple<>(0.0, 200.0);
        this.color = Color.RED;
        this.width = width;
        this.height = height;
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
