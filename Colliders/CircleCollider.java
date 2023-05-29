package Colliders;

import Utils.Vector2d;

public class CircleCollider implements Collider {
    public Vector2d center;
    public double r;
    public CircleCollider(Vector2d center, double r) {
        this.center = center.clone();
        this.r = r;
    }
    @Override
    public boolean colliding(Collider collider) {
        if (collider instanceof CircleCollider) {
            CircleCollider c = (CircleCollider) collider;
            Vector2d diff = center.subNew(c.center);
            return diff.normSquared() <= Math.pow(r + c.r, 2);
        }
        return collider.colliding(this);
    }

    @Override
    public Vector2d getCenter() {
        return center.clone();
    }
}
