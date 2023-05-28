package Colliders;

import Utils.Vector2d;

public class TriangleCollider implements Collider {
    public Vector2d v1, v2, v3;
    public TriangleCollider(Vector2d v1, Vector2d v2, Vector2d v3) {
        this.v1 = v1.clone();
        this.v2 = v2.clone();
        this.v3 = v3.clone();
    }
    @Override
    public boolean colliding(Collider collider) {
        if (collider instanceof CircleCollider) {

        } else if (collider instanceof BoxCollider) {
            BoxCollider b = (BoxCollider) collider;
            return colliding(b.triangle1) || colliding(b.triangle2);
        } else if (collider instanceof TriangleCollider) {

        }
        return false;
    }
}
