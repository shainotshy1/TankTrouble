package GameObjects;

import java.awt.*;
import java.util.HashMap;
import Utils.*;

public class Player implements GameObject {
    public int score;
    public String name;
    private Tank tank;

    public Player(Tuple<Double, Double> tankPos, String name, Color bodyColor, Color turretColor, PlayerTypes playerType) {
        this.name = name;
        this.score = 0;
        this.tank = new Tank(tankPos, bodyColor, turretColor, this.makeCodeDirectionMap(playerType));
    }

    private HashMap<Integer, Double> makeCodeDirectionMap(PlayerTypes playerType) {
        HashMap<Integer, Double> codeToDirection = new HashMap<>(); // maps key code to a direction (angle)
        switch (playerType) {
            case PLAYER1 -> {
                codeToDirection.put(Constants.UP_ARROW, 0.0);
                codeToDirection.put(Constants.DOWN_ARROW, Math.PI);
                codeToDirection.put(Constants.LEFT_ARROW, -Math.PI/2);
                codeToDirection.put(Constants.RIGHT_ARROW, Math.PI/2);
            } case PLAYER2 -> {
                codeToDirection.put(Constants.W, 0.0);
                codeToDirection.put(Constants.S, Math.PI);
                codeToDirection.put(Constants.A, -Math.PI/2);
                codeToDirection.put(Constants.D, Math.PI/2);
            }
        }
        return codeToDirection;
    }

    @Override
    public void display(Graphics2D g2){
        this.tank.display(g2);
    }
}
