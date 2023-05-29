package Colliders;

import Utils.Vector2d;

public interface Collider {
    boolean colliding(Collider collider);
    Vector2d getCenter();
}
