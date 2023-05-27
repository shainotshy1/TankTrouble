package GameMechanics;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import GameObjects.GameObject;

public class GamePanel extends JPanel {
    private KeyHandler keyHandler;
    private List<GameObject> gameObjects;
    public GamePanel(int w, int h, Color bg) {
        setPreferredSize(new Dimension(w, h));
        setBackground(bg);
        setDoubleBuffered(true);

        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        gameObjects = new ArrayList<>();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void clearAllGameObjects() {
        gameObjects.clear();
    }

    public boolean getKeyStatus(int code) {
        return keyHandler.getKeyStatus(code);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (GameObject o : gameObjects) {
            o.display(g2);
        }
        g2.dispose();
    }

    public void display() {
        repaint();
    }
}
