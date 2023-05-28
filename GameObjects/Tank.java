package GameObjects;

import java.awt.*;
import java.util.HashMap;

import GameMechanics.GamePanel;
import GameMechanics.KeyHandler;
import Utils.Tuple;

public class Tank implements GameObject{
    private Body body;
    private Turret turret;
    private Tuple<Integer, Integer> bodyDim; // (width, height)
    private Tuple<Integer, Integer> turretDim; // (width, height)
    private Tuple<Double, Double> centerPos; // (x, y)
    private Tuple<Double, Double> bodyPos; // (x, y)
    private Tuple<Double, Double> turretPos; // (x, y)
    private Tuple<Double, Double> vel; // (vx, vy)
    private Tuple<Double, Double> accel; // (ax, ay)
    private Double direction; // angle in radians
    private Color bodyColor;
    private Color turretColor;
    private KeyHandler keyHandler;
    public Tank(Tuple<Double, Double> centerPos, Color bodyColor, Color turretColor){
        this.keyHandler = new KeyHandler();
        this.direction = 0.0;
        this.accel = new Tuple<>(0.0, 0.0);
        this.vel = new Tuple<>(0.0, 0.0);
        this.centerPos = centerPos;
        this.bodyColor = bodyColor;
        this.turretColor = turretColor;
        this.bodyDim = new Tuple<>(50, 50);
        this.turretDim = new Tuple<>(this.bodyDim.first/5, this.bodyDim.first*4/5);

        this.bodyPos = new Tuple<>(this.centerPos.first - this.bodyDim.first/2,
                                   this.centerPos.second - this.bodyDim.second/2);

        this.turretPos = new Tuple<>(this.bodyPos.first + this.bodyDim.first/2 - this.turretDim.first/2,
                                     this.bodyPos.second - this.bodyDim.second*2/5);

        this.body = new Body(this.bodyPos, this.vel, this.accel, this.bodyDim, this.bodyColor);
        this.turret = new Turret(this.turretPos, this.vel, this.accel, this.turretDim, this.turretColor);

    }

    private void updateMotion() {
        this.updatePosition(this.vel);
        this.updateVelocity(this.accel);
    }

    public void updateVelocity(Tuple<Double, Double> accel) {
        this.body.updateVelocity(accel);
        this.turret.updateVelocity(accel);
    }

    public void updatePosition(Tuple<Double, Double> vel) {
        this.body.updatePosition(vel);
        this.turret.updatePosition(vel);
    }

    @Override
    public void display(Graphics2D g2) {
        this.updateMotion();

        this.body.display(g2);
        this.turret.display(g2);
//        g2.rotate(-this.direction, this.centerPos.first, this.centerPos.second);
    }
}