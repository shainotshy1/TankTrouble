package Colliders;

import Utils.Constants;
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

    public static boolean lineSegmentsIntersect(List<Vector2d> seg1, List<Vector2d> seg2) {
        Vector2d line1 = getLine(seg1.get(0), seg1.get(1));
        Vector2d line2 = getLine(seg2.get(0), seg2.get(1));
        Vector2d intersect;
        try {
            intersect = intersection(line1, line2);
        } catch (ArithmeticException e) {
            //handle if line are parallel
            return false;
        }
        return pointOnLineSegment(seg1, intersect) && pointOnLineSegment(seg2, intersect);
    }

    //returns equation y = ax + b in form of vector (a, b)
    public static Vector2d getLine(Vector2d p1, Vector2d p2) {
        double a = (p1.y - p2.y) / (p1.x - p2.x);
        double b = p1.y - a * p1.x;
        return new Vector2d(a, b);
    }

    //takes in 2 lines in form (a1, b1), (a2, b2) where y = a1 x + b1, y = a2 x + b2
    public static Vector2d intersection(Vector2d line1, Vector2d line2) throws ArithmeticException {
        double a1 = line1.x;
        double b1 = line1.y;
        double a2 = line2.x;
        double b2 = line2.y;
        double x = (b1 - b2) / (a2 - a1); //can throw arithmetic exception if parallel lines
        double y = a1 * x + b1;
        return new Vector2d(x, y);
    }

    //lineSeg is length two list with the elements being the two vertices of the line
    public static boolean pointOnLineSegment(List<Vector2d> lineSeg, Vector2d x) {
        Vector2d p1 = lineSeg.get(0);
        Vector2d p2 = lineSeg.get(1);
        Vector2d diff1 = x.subNew(p1);
        Vector2d diff2 = p2.subNew(p1);
        double slope1 = diff2.y / diff2.x;
        double slope2 = diff1.y / diff1.x;
        if (Math.abs(slope2 - slope1) > Constants.EPSILON) { //not same slope so not on the line segment
            return false;
        }
        if (diff1.dot(diff2) < 0) { //pointing in other direction
            return false;
        }
        return diff1.normSquared() < diff2.normSquared(); //off the line
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
                    if (lineSegmentsIntersect(seg1, seg2)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false; //return if unhandled collider
    }
}
