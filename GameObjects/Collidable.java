package GameObjects;

import Colliders.Collider;

public interface Collidable {
    boolean colliding(Collider collider);
    Collider getCollider();
}
