package GameObjects;

import java.awt.*;
import java.util.HashMap;

import GameMechanics.GamePanel;
import Utils.*;

public class Player implements GameObject {
    public int score;
    public String name;
    private Double angle;
    private Tank tank;
    private Tuple<Double, Double> tankPos;
    public GamePanel gamePanel;
    private HashMap<String, Integer> keyCodes;

    public Player(Tuple<Double, Double> tankPos, String name, Color bodyColor, Color turretColor, GamePanel gamePanel,
                  HashMap<String, Integer> keyCodes) {
        this.name = name;
        this.score = 0;
        this.gamePanel = gamePanel;
        this.keyCodes = keyCodes;
        this.tankPos = tankPos;
        this.angle = 0.0;
        this.tank = new Tank(tankPos, bodyColor, turretColor);
    }

    @Override
    public void display(Graphics2D g2){
        g2.rotate(this.angle, this.tankPos.first, this.tankPos.second);
        this.tank.display(g2);
        if (this.gamePanel.getKeyStatus(keyCodes.get("UP"))) {
            this.tank.updatePosition(new Tuple<>(0.0, -1.0));
        }
        if (this.gamePanel.getKeyStatus(keyCodes.get("DOWN"))) {
            this.tank.updatePosition(new Tuple<>(0.0, 1.0));
        }
        if (this.gamePanel.getKeyStatus(keyCodes.get("LEFT"))) {
            this.angle -= Math.PI/100;
        }
        if (this.gamePanel.getKeyStatus(keyCodes.get("RIGHT"))) {
            this.angle += Math.PI/100;
        }
    }
}
