package GameObjects;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player implements GameObject {
    public int score;
    public String name;
    private Tank tank;
    private HashMap<Enum<KeyNames>, Integer> keysToCodes;
    private HashMap<Integer, Tuple<Double, Double>> keyMap; // maps key code to a direction

    public Player(Tuple<Double, Double> tankPos, String name, Color bodyColor, Color turretColor,
                  ArrayList<Enum<KeyNames>> tankKeys) {
        this.makeKeysToCodes();
        this.makeKeyMap();
        this.name = name;
        this.score = 0;
        this.tank = new Tank(tankPos, bodyColor, turretColor, this.tankMap(tankKeys));
    }

    private void makeKeyMap() {
        this.keyMap = new HashMap<>();

        this.keyMap.put(this.keysToCodes.get(KeyNames.UP), new Tuple<>(0.0, -1.0));
        this.keyMap.put(this.keysToCodes.get(KeyNames.DOWN), new Tuple<>(0.0, 1.0));
        this.keyMap.put(this.keysToCodes.get(KeyNames.LEFT), new Tuple<>(-1.0, 0.0));
        this.keyMap.put(this.keysToCodes.get(KeyNames.RIGHT), new Tuple<>(1.0, 0.0));

        this.keyMap.put(this.keysToCodes.get(KeyNames.W), new Tuple<>(0.0, -1.0));
        this.keyMap.put(this.keysToCodes.get(KeyNames.S), new Tuple<>(0.0, 1.0));
        this.keyMap.put(this.keysToCodes.get(KeyNames.A), new Tuple<>(-1.0, 0.0));
        this.keyMap.put(this.keysToCodes.get(KeyNames.D), new Tuple<>(1.0, 0.0));
    }

    private HashMap<Integer, Tuple<Double, Double>> tankMap(ArrayList<Enum<KeyNames>> keyList) {
        HashMap<Integer, Tuple<Double, Double>> returnMap = new HashMap<>();
        for (Enum<KeyNames> key : keyList) {
            int code = this.keysToCodes.get(key);
            returnMap.put(code, this.keyMap.get(code));
        }
        return returnMap;
    }

    private void makeKeysToCodes() {
        this.keysToCodes = new HashMap<>();
        this.keysToCodes.put(KeyNames.UP, 38);
        this.keysToCodes.put(KeyNames.DOWN, 40);
        this.keysToCodes.put(KeyNames.LEFT, 37);
        this.keysToCodes.put(KeyNames.RIGHT, 39);
        this.keysToCodes.put(KeyNames.W, 87);
        this.keysToCodes.put(KeyNames.A, 65);
        this.keysToCodes.put(KeyNames.S, 83);
        this.keysToCodes.put(KeyNames.D, 68);

    }

    @Override
    public void display(Graphics2D g2){
        this.tank.display(g2);
    }
}
