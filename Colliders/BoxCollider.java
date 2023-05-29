package Colliders;

import Utils.Vector2d;

public class BoxCollider implements Collider {
    private Vector2d center;
    private double w, h, angle;
    public TriangleCollider triangle1;
    public TriangleCollider triangle2;
    public BoxCollider(Vector2d center, double w, double h, double angle) {
        this.center = center.clone();
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
        triangle2.v1.add(delta);
        triangle2.v2.add(delta);
        triangle2.v3.add(delta);
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
        Vector2d a1, b1, c1, a2, b2, c2;
        double half_w = w / 2;
        double half_h = h / 2;
        a1 = new Vector2d(-half_w, half_h);
        b1 = new Vector2d(half_w, half_h);
        c1 = new Vector2d(-half_w, -half_h);
        a2 = b1.clone();
        b2 = c1.clone();
        c2 = new Vector2d(half_w, -half_h);
        a1.rotate(angle);
        b1.rotate(angle);
        c1.rotate(angle);
        a2.rotate(angle);
        b2.rotate(angle);
        c2.rotate(angle);
        triangle1 = new TriangleCollider(a1, b1, c1);
        triangle2 = new TriangleCollider(a2, b2, c2);
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

    @Override
    public Vector2d getCenter() {
        return center.clone();
    }
}
