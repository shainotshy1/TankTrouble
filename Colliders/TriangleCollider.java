package Colliders;

import Utils.LineUtils;
import Utils.Vector2d;
import Utils.Vector3d;
import java.util.List;
import java.util.ArrayList;

public class TriangleCollider implements Collider {
    public Vector2d v1, v2, v3;

    public TriangleCollider(Vector2d v1, Vector2d v2, Vector2d v3) {
        this.v1 = v1.clone();
        this.v2 = v2.clone();
        this.v3 = v3.clone();
    }

    //Cramer's rule implementation
    private Vector3d findBarycentric(Vector2d p) {
        Vector2d p0 = v2.subNew(v1);
        Vector2d p1 = v3.subNew(v1);
        Vector2d p2 = p.subNew(v1);
        double d00 = p0.dot(p0);
        double d01 = p0.dot(p1);
        double d11 = p1.dot(p1);
        double d20 = p2.dot(p0);
        double d21 = p2.dot(p1);
        double denominator = d00 * d11 - d01 * d01;
        double alpha = (d11 * d20 - d01 * d21) / denominator;
        double beta = (d00 * d21 - d01 * d20) / denominator;
        double gamma = 1 - alpha - beta;
        Vector3d res = new Vector3d(alpha, beta, gamma);
        return res;
    }

    public boolean pointInside(Vector2d p) {
        Vector3d bary = findBarycentric(p);
        double alpha = bary.x;
        double beta = bary.y;
        double gamma = bary.z;
        return alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1 && gamma >= 0 && gamma <= 1;
    }

    //returns bounding box in order: top left, top right, bottom left, bottom right
    private List<Vector2d> calcBoundingBox(Vector2d p1, Vector2d p2, Vector2d p3) {
        List<Vector2d> box = new ArrayList<>();
        double x_l = Math.min(p1.x, Math.min(p2.x, p3.x));
        double x_r = Math.max(p1.x, Math.max(p2.x, p3.x));
        double y_b = Math.min(p1.y, Math.min(p2.y, p3.y));
        double y_t = Math.max(p1.y, Math.max(p2.y, p3.y));
        box.add(new Vector2d(x_l, y_t)); //top left
        box.add(new Vector2d(x_r, y_t)); //top right
        box.add(new Vector2d(x_l, y_b)); //bottom left
        box.add(new Vector2d(x_r, y_b)); //bottom right
        return box;
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
            if (LineUtils.distSquareToSegment(v1, v2, p) <= rad2) {
                return true;
            }
            if (LineUtils.distSquareToSegment(v1, v3, p) <= rad2) {
                return true;
            }
            if (LineUtils.distSquareToSegment(v2, v3, p) <= rad2) {
                return true;
            }
            return false;
        } else if (collider instanceof BoxCollider) {
            BoxCollider b = (BoxCollider) collider;
            return colliding(b.triangle1) || colliding(b.triangle2);
        } else if (collider instanceof TriangleCollider) {
            TriangleCollider t = (TriangleCollider) collider;

            //check if bounding boxes intersect
            List<Vector2d> box1 = calcBoundingBox(v1, v2, v3);
            List<Vector2d> box2 = calcBoundingBox(t.v1, t.v2, t.v3);
            boolean xDiffSubZero = false;
            boolean yDiffSubZero = false;
            for (int i = 0; i < box1.size(); i++) {
                Vector2d diff = box1.get(i).subNew(box2.get(i));
                if (i == 0) {
                    xDiffSubZero = diff.x <= 0;
                    yDiffSubZero = diff.y <= 0;
                } else if(diff.x <= 0 != xDiffSubZero || diff.y <= 0 != yDiffSubZero) {
                    return true;
                }
            }

            //check intersecting line segments
            List<List<Vector2d>> lineSegments1 = new ArrayList<>();
            List<List<Vector2d>> lineSegments2 = new ArrayList<>();

            lineSegments1.add(new ArrayList<>(List.of(v1, v2)));
            lineSegments1.add(new ArrayList<>(List.of(v1, v3)));
            lineSegments1.add(new ArrayList<>(List.of(v2, v3)));

            lineSegments2.add(new ArrayList<>(List.of(t.v1, t.v2)));
            lineSegments2.add(new ArrayList<>(List.of(t.v1, t.v3)));
            lineSegments2.add(new ArrayList<>(List.of(t.v2, t.v3)));

            for (List<Vector2d> seg1 : lineSegments1) {
                for (List<Vector2d> seg2 : lineSegments2) {
                    if (LineUtils.lineSegmentsIntersect(seg1, seg2)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false; //return if unhandled collider
    }

    @Override
    public Vector2d getCenter() {
        return v1.addNew(v2).addNew(v3).mulNew(1 / 3);
    }
}
