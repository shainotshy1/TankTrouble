package GameObjects;

import Colliders.CircleCollider;
import Colliders.Collider;
import Utils.Vector2d;

import java.awt.*;

public class Ball implements GameObject, Collidable {
    private Vector2d pos;
    private Vector2d vel;
    private double r;
    private Color color;
    private CircleCollider collider;
    public Ball(Vector2d pos, Vector2d vel, double r, Color color){
        this.pos = pos.clone();
        this.vel = vel.clone();
        this.r = r;
        this.color = color;
        collider = new CircleCollider(pos, r);
    }

    //input frameDelta where it is the previous frame time
    public void update(double frameDelta) {
        pos.add(vel.mulNew(frameDelta));
        resetColliderCenter();
    }

    public void setPosition(Vector2d pos) {
        this.pos = pos.clone();
        resetColliderCenter();
    }

    public void setVelocity(Vector2d vel) {
        this.vel = vel.clone();
    }

    public Vector2d getVelocity() {
        return vel.clone();
    }

    private void resetColliderCenter() {
        collider.center = pos.clone();
    }

    @Override
    public void display(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int) pos.x, (int) pos.y, (int) r, (int) r);
    }

    @Override
    public boolean colliding(Collider collider) {
        return this.collider.colliding(collider);
    }

    @Override
    public Collider getCollider() {
        return collider;
    }
}
