package Colliders;

import Utils.Vector2d;
import Utils.Vector3d;

public class TriangleCollider implements Collider {
    public Vector2d v1, v2, v3;
    public TriangleCollider(Vector2d v1, Vector2d v2, Vector2d v3) {
        this.v1 = v1.clone();
        this.v2 = v2.clone();
        this.v3 = v3.clone();
    }
    private boolean isObtuse(Vector2d p1, Vector2d p2, Vector2d p3) {
        double side1 = p1.subNew(p2).normSquared();
        double side2 = p1.subNew(p3).normSquared();
        double side3 = p2.subNew(p3).normSquared();
        double c2 = Math.max(side1, Math.max(side2, side3));
        double a2_plus_b2 = side1 + side2 + side3 - c2;
        return a2_plus_b2 < c2;
    }
    private double distSquareToSegment(Vector2d p1, Vector2d p2, Vector2d x) {
        double lineMag2 = p1.subNew(p2).normSquared();
        if (lineMag2 == 0) { //if line segment is a point
            return p1.subNew(x).normSquared();
        }
        if (isObtuse(p1, p2, x)) {
            return Math.min(p1.subNew(x).normSquared(), p2.subNew(x).normSquared());
        }
        Vector2d a = p2.subNew(p1);
        Vector2d b = x.subNew(p1);
        Vector2d proj = a.mulNew(b.dot(a) / a.normSquared());
        return proj.normSquared();
    }
    public boolean pointInside(Vector2d p) {
        Vector3d bary = findBarycentric(p);
        double alpha = bary.x;
        double beta = bary.y;
        double gamma = bary.z;
        return alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1 && gamma >= 0 && gamma <= 1;
    }
    private Vector3d findBarycentric(Vector2d p) {
        Vector2d bc = v3.subNew(v2);
        Vector2d ac = v3.subNew(v1);
        Vector2d ap = p.subNew(v1);
        Vector2d bp = p.subNew(v2);
        double apc = Math.abs(ac.cross(ap));
        double bpc = Math.abs(bc.cross(bp));
        double abc = Math.abs(bc.cross(ac));
        double alpha = bpc / abc;
        double beta = apc / abc;
        double gamma = 1 - alpha - beta;
        Vector3d res = new Vector3d(alpha, beta, gamma);
        return res;
    }
    @Override
    public boolean colliding(Collider collider) {
        if (collider instanceof CircleCollider) {
            CircleCollider c = (CircleCollider) collider;
            Vector2d p = c.center;
            double rad2 = Math.pow(c.r, 2);
            if (pointInside(p)) {
                return true;
            }
            if (distSquareToSegment(v1, v2, p) <= rad2) {
                return true;
            }
            if (distSquareToSegment(v1, v3, p) <= rad2) {
                return true;
            }
            if (distSquareToSegment(v2, v3, p) <= rad2) {
                return true;
            }
            return false;
        } else if (collider instanceof BoxCollider) {
            BoxCollider b = (BoxCollider) collider;
            return colliding(b.triangle1) || colliding(b.triangle2);
        } else if (collider instanceof TriangleCollider) {
            //check bounding boxes
            //check intersecting line segments
        }
        return false; //return if unhandled collider
    }
}
