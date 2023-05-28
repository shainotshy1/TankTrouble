package GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

import GameMechanics.GamePanel;
import Utils.*;

public class Player implements GameObject {
    public int score;
    public String name;
    private Tank tank;
    public GamePanel gamePanel;
    private HashMap<String, Integer> keyCodes;

    public Player(Tuple<Double, Double> tankPos, String name, Color bodyColor, Color turretColor, GamePanel gamePanel,
                  HashMap<String, Integer> keyCodes) {
        this.name = name;
        this.score = 0;
        this.gamePanel = gamePanel;
        this.keyCodes = keyCodes;
        this.tank = new Tank(tankPos, bodyColor, turretColor);
    }

    public void update(Graphics2D g2) {
        this.tank.update(g2, this.gamePanel, this.keyCodes);
    }

    @Override
    public void display(Graphics2D g2){
        this.update(g2);
        this.tank.display(g2);
    }
}
