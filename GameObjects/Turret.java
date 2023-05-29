package GameObjects;

import Utils.Tuple;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Turret extends Mover implements GameObject{
    ArrayList<Bullet> bullets;

    public Turret(Tuple<Double, Double> pos, Tuple<Double, Double> vel, Tuple<Double, Double> accel,
                  Tuple<Integer, Integer> dim, Color color) {
        super();
        this.width = dim.first;
        this.height = dim.second;
        this.color = color;
        this.pos = pos;
        this.vel = vel;
        this.accel = accel;
        this.bullets = new ArrayList<>();
    }
    public void shoot(BulletTypes type){
        this.addBullet(type);
    }

    private void addBullet(BulletTypes type){
        switch (type) {
            case BASIC -> this.bullets.add(new BasicBullet(new Tuple<>(0.0, (double)this.height), this.vel,
                                           new Tuple<>(this.width, this.width)));
            case SEEKER -> this.bullets.add(new SeekerBullet(new Tuple<>(0.0, (double)this.height), this.vel,
                                            new Tuple<>(this.width, this.width)));
            case MISSILE -> this.bullets.add(new MissileBullet(new Tuple<>(0.0, (double)this.height), this.vel,
                                             new Tuple<>(this.width, this.width)));
        }
    }

    @Override
    public void display(Graphics2D g2) {
        AffineTransform old = g2.getTransform();
        g2.rotate(-Math.PI / 2);
        g2.setColor(this.color);
        this.updateVelocity(this.accel);
        g2.fillRect(-this.width/2, 0, this.width, this.height);
        this.updatePosition(this.vel);
        for (Bullet bullet : this.bullets) {
            bullet.display(g2);
            bullet.updatePosition(bullet.vel);
        }
        g2.setTransform(old);
    }
}
