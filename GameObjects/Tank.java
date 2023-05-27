package GameObjects;

import java.awt.*;

public class Tank implements GameObject{
    Body body;
    Turret turret;

    public Tank(){

    }

    @Override
    public void display(Graphics2D g2) {
        this.body.display(g2);
        this.turret.display(g2);
    }
}
