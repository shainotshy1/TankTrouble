import java.awt.Graphics2D;
import java.awt.Color;

public class Rectangle implements GameObject {
    public int x;
    public int y;
    public int w;
    public int h;
    public Color color;
    public Rectangle(int x, int y, int w, int h, Color color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    @Override
    public void display(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, w, h);
    }
}
