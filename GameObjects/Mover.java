package GameObjects;

import Utils.Tuple;

import java.awt.*;

public abstract class Mover {
    public Tuple<Double, Double> pos;
    public Tuple<Double, Double> vel;
    public Tuple<Double, Double> accel;
    public int width;
    public int height;
    public Color color;

    public void updatePosition(Tuple<Double, Double> vel) {
        this.pos.first += vel.first;
        this.pos.second += vel.second;
    }

    public void updateVelocity(Tuple<Double, Double> accel) {
        this.vel.first += accel.first;
        this.vel.second += accel.second;
    }

    public void updateAccel(Tuple<Double, Double> dA) {
        this.accel.first += dA.first;
        this.accel.second += dA.second;
    }

}

