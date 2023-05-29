package Tests;

import Colliders.BoxCollider;
import Colliders.CircleCollider;

import Colliders.TriangleCollider;
import Utils.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ColliderTests {
    @Test
    public void CircleCircleTest() {
        Vector2d c1 = new Vector2d(1.5, 2.3);
        Vector2d c2 = new Vector2d(1.3, 10.2);
        Vector2d c3 = new Vector2d(50, 27);
        double r1 = 5.3;
        double r2 = 10.8;
        double r3 = 4.7;
        CircleCollider circle1 = new CircleCollider(c1, r1);
        CircleCollider circle2 = new CircleCollider(c2, r2);
        CircleCollider circle3 = new CircleCollider(c3, r3);
        assertTrue(circle1.colliding(circle2));
        assertFalse(circle1.colliding(circle3));
    }

    @Test
    public void CircleTriangleTest() {
        Vector2d c = new Vector2d(1.5, 2.3);
        double r = 5.3;
        Vector2d a1 = new Vector2d(3, 5);
        Vector2d b1 = new Vector2d(4, 2);
        Vector2d c1 = new Vector2d(10, 16);
        Vector2d a2 = new Vector2d(10,20);
        Vector2d b2 = new Vector2d(20,20);
        Vector2d c2 = new Vector2d(10,10);
        CircleCollider circle = new CircleCollider(c, r);
        TriangleCollider triangle1 = new TriangleCollider(a1, b1, c1);
        TriangleCollider triangle2 = new TriangleCollider(a2, b2, c2);
        assertTrue(circle.colliding(triangle1));
        assertFalse(circle.colliding(triangle2));
    }

    @Test
    public void CircleBoxTest() {
        Vector2d c = new Vector2d(1.5, 2.3);
        double r = 5.3;
        Vector2d c1 = new Vector2d(10,0);
        Vector2d dim1 = new Vector2d(30, 40);
        double angle1 = Math.PI / 4;
        Vector2d c2 = new Vector2d(15,15);
        Vector2d dim2 = new Vector2d(10, 10);
        double angle2 = 0;
        CircleCollider circle = new CircleCollider(c, r);
        BoxCollider box1 = new BoxCollider(c1, dim1.x, dim1.y, angle1);
        BoxCollider box2 = new BoxCollider(c2, dim2.x, dim2.y, angle2);
        assertTrue(circle.colliding(box1));
        assertFalse(circle.colliding(box2));
    }

    @Test
    public void TriangleTriangleTest() {
        Vector2d a1 = new Vector2d(3, 5);
        Vector2d b1 = new Vector2d(4, 2);
        Vector2d c1 = new Vector2d(10, 16);
        Vector2d a2 = new Vector2d(5,0);
        Vector2d b2 = new Vector2d(4,4);
        Vector2d c2 = new Vector2d(10,0);
        Vector2d a3 = new Vector2d(10,20);
        Vector2d b3 = new Vector2d(20,20);
        Vector2d c3 = new Vector2d(10,10);
        TriangleCollider triangle1 = new TriangleCollider(a1, b1, c1);
        TriangleCollider triangle2 = new TriangleCollider(a2, b2, c2);
        TriangleCollider triangle3 = new TriangleCollider(a3, b3, c3);
        assertTrue(triangle1.colliding(triangle2));
        assertFalse(triangle1.colliding(triangle3));
    }

    @Test
    public void TriangleRectangleTest() {
        Vector2d a1 = new Vector2d(3, 5);
        Vector2d b1 = new Vector2d(4, 2);
        Vector2d c1 = new Vector2d(9, 16);
        Vector2d center1 = new Vector2d(0,0);
        Vector2d dim1 = new Vector2d(12.5, 4);
        double angle1 = Math.PI / 3;
        Vector2d center2 = new Vector2d(15,15);
        Vector2d dim2 = new Vector2d(10, 10);
        double angle2 = 0;
        TriangleCollider triangle = new TriangleCollider(a1, b1, c1);
        BoxCollider box1 = new BoxCollider(center1, dim1.x, dim1.y, angle1);
        BoxCollider box2 = new BoxCollider(center2, dim2.x, dim2.y, angle2);
        assertTrue(triangle.colliding(box1));
        assertFalse(triangle.colliding(box2));
    }

    @Test
    public void RectangleRectangleTest() {
        Vector2d center1 = new Vector2d(0,0);
        Vector2d dim1 = new Vector2d(12.5, 4);
        double angle1 = Math.PI / 3;
        Vector2d center2 = new Vector2d(5,5);
        Vector2d dim2 = new Vector2d(10, 10);
        double angle2 = 0;
        Vector2d center3 = new Vector2d(15,15);
        Vector2d dim3 = new Vector2d(10, 10);
        double angle3 = 0;
        BoxCollider box1 = new BoxCollider(center1, dim1.x, dim1.y, angle1);
        BoxCollider box2 = new BoxCollider(center2, dim2.x, dim2.y, angle2);
        BoxCollider box3 = new BoxCollider(center3, dim3.x, dim3.y, angle3);
        assertTrue(box1.colliding(box2));
        assertFalse(box1.colliding(box3));
    }
}
