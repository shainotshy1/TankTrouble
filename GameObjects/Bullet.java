package GameObjects;

import Utils.Tuple;

import java.awt.*;

public abstract class Bullet extends Mover implements GameObject{
    Tuple<Double, Double> pos;
    Tuple<Double, Double> vel;
    Tuple<Integer, Integer> dim;
    Color color;
    Double direction;

}
