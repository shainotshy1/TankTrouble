package Colliders;

import Utils.Vector2d;

public class BoxCollider implements Collider {
    private Vector2d center;
    private double w, h, angle;
    public TriangleCollider triangle1;
    public TriangleCollider triangle2;
    public BoxCollider(double x_center, double y_center, double w, double h, double angle) {
        center = new Vector2d(x_center, y_center);
        this.w = w;
        this.h = h;
        this.angle = angle;
        calculateVertices();
    }

    public void updateAngle(double angle) {
        this.angle = angle;
        calculateVertices();
    }

    public void shift(Vector2d delta) {
        triangle1.v1.add(delta);
        triangle1.v2.add(delta);
        triangle1.v3.add(delta);
    }

    public void updateCenter(Vector2d center) {
        Vector2d delta = this.center.subNew(center);
        shift(delta);
    }

    public void updateDimensions(double w, double h) {
        this.w = w;
        this.h = h;
        calculateVertices();
    }

    private void calculateVertices() {
        Vector2d v1, v2, v3, v4;
        double half_w = w / 2;
        double half_h = h / 2;
        v1 = new Vector2d(-half_w, half_h);
        v2 = new Vector2d(half_w, half_h);
        v3 = new Vector2d(-half_w, -half_h);
        v4 = new Vector2d(half_w, -half_h);
        v1.rotate(angle);
        v2.rotate(angle);
        v3.rotate(angle);
        v4.rotate(angle);
        triangle1 = new TriangleCollider(v1, v2, v3);
        triangle2 = new TriangleCollider(v1, v2, v4);
        shift(center);
    }

    @Override
    public boolean colliding(Collider collider) {
        if (collider instanceof CircleCollider) {
            return triangle1.colliding(collider) || triangle2.colliding(collider);
        } else if (collider instanceof BoxCollider) {
            BoxCollider b = (BoxCollider) collider;
            return colliding(b.triangle1) || colliding(b.triangle2);
        } else if (collider instanceof TriangleCollider) {
            return triangle1.colliding(collider) || triangle1.colliding(collider);
        }
        return false;
    }
}
