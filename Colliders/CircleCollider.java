package Colliders;

public class CircleCollider implements Collider {
    public double x_center, y_center, r;
    public CircleCollider(double x_center, double y_center, double r) {
        this.x_center = x_center;
        this.y_center = y_center;
        this.r = r;
    }
    @Override
    public boolean colliding(Collider collider) {
        if (collider instanceof CircleCollider) {
            CircleCollider c = (CircleCollider) collider;
            double dist2 = Math.pow(x_center - c.x_center, 2) + Math.pow(y_center - c.y_center, 2);
            return dist2 <= Math.pow(r + c.r, 2);
        }
        return collider.colliding(this);
    }
}
