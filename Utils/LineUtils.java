package Utils;

import java.util.List;

public class LineUtils {
    public static boolean isObtuse(Vector2d p1, Vector2d p2, Vector2d p3) {
        double side1 = p1.subNew(p2).normSquared();
        double side2 = p1.subNew(p3).normSquared();
        double side3 = p2.subNew(p3).normSquared();
        double c2 = Math.max(side1, Math.max(side2, side3));
        double a2_plus_b2 = side1 + side2 + side3 - c2;
        return a2_plus_b2 < c2;
    }
    public static double distSquareToSegment(Vector2d p1, Vector2d p2, Vector2d x) {
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
}
