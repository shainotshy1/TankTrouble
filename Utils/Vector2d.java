package Utils;


public class Vector2d {
    double x, y;
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dot(Vector2d other) {
        return x * other.x + y * other.y;
    }

    public double norm() {
        return Math.sqrt(normSquared());
    }

    public double normSquared() {
        return dot(this);
    }

    public void add(Vector2d other) {
        x += other.x;
        y += other.y;
    }

    public void sub(Vector2d other) {
        x -= other.x;
        y -= other.y;
    }

    public void mul(double alpha) {
        x *= alpha;
        y *= alpha;
    }

    public void rotate(double angle) {
        double tempX = x;
        double tempY = y;
        x = Math.cos(angle) * tempX - Math.sin(angle) * tempY;
        y = Math.sin(angle) * tempX + Math.cos(angle) * tempY;
    }

    public Vector2d addNew(Vector2d other) {
        Vector2d res = new Vector2d(x, y);
        res.add(other);
        return res;
    }

    public Vector2d subNew(Vector2d other) {
        Vector2d res = new Vector2d(x, y);
        res.sub(other);
        return res;
    }

    public Vector2d mulNew(double alpha) {
        Vector2d res = new Vector2d(x, y);
        res.mul(alpha);
        return res;
    }

    public Vector2d rotateNew(double alpha) {
        Vector2d res = new Vector2d(x, y);
        res.rotate(alpha);
        return res;
    }

    public Vector2d clone() {
        Vector2d res = new Vector2d(x, y);
        return res;
    }
}
