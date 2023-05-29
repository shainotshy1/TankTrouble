package GameObjects;

import Utils.Tuple;

import java.awt.*;

public abstract class Bullet extends Mover implements GameObject{
    Tuple<Double, Double> pos;
    Tuple<Double, Double> vel;
    Color color;
    Shapes shape;
    Double direction;
}
