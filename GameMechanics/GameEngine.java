package GameMechanics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.HashMap;


import GameObjects.*;
import GameObjects.Rectangle;
import Utils.Constants;
import Utils.Tuple;

public class GameEngine implements Runnable{
    private static final int TILE_SIZE = 48;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    JFrame frame;
    GamePanel gamePanel;
    Thread gameThread;
    int rows;
    int cols;
    int topLeftX;
    int topLeftY;
    public GameEngine() {
        frame = new JFrame();
        gamePanel = new GamePanel(WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLACK);
        setupFrame(gamePanel);

        double test_aspect_ratio = 3;
        generateWorld(test_aspect_ratio);
    }

    public void generateWorld(double aspectRatio) {
        if (aspectRatio > 1) {
            cols = (WINDOW_WIDTH + TILE_SIZE - 1) / TILE_SIZE;
            rows = (int)Math.ceil(cols / aspectRatio);
            topLeftX = 0;
            topLeftY = (WINDOW_HEIGHT - rows * TILE_SIZE) / 2;
        } else {
            rows = (WINDOW_HEIGHT + TILE_SIZE - 1) / TILE_SIZE;
            cols = (int)Math.ceil(rows * aspectRatio);
            topLeftY = 0;
            topLeftX = (WINDOW_WIDTH - cols * TILE_SIZE) / 2;
        }

        //Demonstration of how to use the game panel
        Rectangle rectangle = new Rectangle(topLeftX, topLeftY, cols * TILE_SIZE, rows * TILE_SIZE, Color.WHITE);
        gamePanel.addGameObject(rectangle);
        HashMap<String, Integer> keyCodes1 = new HashMap<>();
        keyCodes1.put("UP", Constants.UP_ARROW);
        keyCodes1.put("DOWN", Constants.DOWN_ARROW);
        keyCodes1.put("LEFT", Constants.LEFT_ARROW);
        keyCodes1.put("RIGHT", Constants.RIGHT_ARROW);
        Player player1 = new Player(new Tuple<>(WINDOW_WIDTH*2.0/3.0, WINDOW_HEIGHT/2.0), "Bob",
                                    Color.BLUE, Color.RED, gamePanel, keyCodes1);
        gamePanel.addGameObject(player1);

        HashMap<String, Integer> keyCodes2 = new HashMap<>();
        keyCodes2.put("UP", Constants.W);
        keyCodes2.put("LEFT", Constants.A);
        keyCodes2.put("DOWN", Constants.S);
        keyCodes2.put("RIGHT", Constants.D);
        Player player2 = new Player(new Tuple<>(WINDOW_WIDTH/3.0, WINDOW_HEIGHT/2.0), "Joe",
                Color.MAGENTA, Color.GREEN, gamePanel, keyCodes2);
        gamePanel.addGameObject(player2);
    }

    private void setupFrame(JPanel panel) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Tank Trouble");
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            gamePanel.display();
        }
    }

    public void update() {

    }
}
