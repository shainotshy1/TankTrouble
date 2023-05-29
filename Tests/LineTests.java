package Tests;

import Utils.LineUtils;
import Utils.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class LineTests {
    @Test
    public void LineIntersectTest() {
        Vector2d line1 = new Vector2d(3,1);
        Vector2d line2 = new Vector2d(2,-7);
        Vector2d intersection = LineUtils.intersection(line1, line2);
        Vector2d expected = new Vector2d(-8,-23);
        assertEquals(intersection.toString(), expected.toString());
    }

    @Test
    public void LineSegIntersectTest() {
        Vector2d a1 = new Vector2d(0,1);
        Vector2d b1 = new Vector2d(5,16);
        Vector2d a2 = new Vector2d(-4,0);
        Vector2d b2 = new Vector2d(4,8);
        Vector2d a3 = new Vector2d(-1,9.7);
        Vector2d b3 = new Vector2d(4,11.2);
        List<Vector2d> seg1 = new ArrayList<>(List.of(a1, b1));
        List<Vector2d> seg2 = new ArrayList<>(List.of(a2, b2));
        List<Vector2d> seg3 = new ArrayList<>(List.of(a3, b3));
        assertTrue(LineUtils.lineSegmentsIntersect(seg1, seg2));
        assertTrue(LineUtils.lineSegmentsIntersect(seg1, seg3));
        assertFalse(LineUtils.lineSegmentsIntersect(seg2, seg3));
    }

    @Test
    public void OnLineTest() {
        Vector2d a = new Vector2d(0,1);
        Vector2d b = new Vector2d(5,16);
        List<Vector2d> seg = new ArrayList<>(List.of(a, b));
        Vector2d x1 = new Vector2d(1,4);
        Vector2d x2 = new Vector2d(6,19);
        Vector2d x3 = new Vector2d(2, 5);
        assertTrue(LineUtils.pointOnLineSegment(seg, x1));
        assertFalse(LineUtils.pointOnLineSegment(seg, x2));
        assertFalse(LineUtils.pointOnLineSegment(seg, x3));
    }
}
