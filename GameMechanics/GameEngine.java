package GameMechanics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.HashMap;


import GameObjects.*;
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
        HashMap<String, Integer> keyCodes = new HashMap<>();
        keyCodes.put("UP", Constants.UP_ARROW);
        keyCodes.put("DOWN", Constants.DOWN_ARROW);
        keyCodes.put("LEFT", Constants.LEFT_ARROW);
        keyCodes.put("RIGHT", Constants.RIGHT_ARROW);
        Player player1 = new Player(new Tuple<>(WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0), "Bob",
                                    Color.BLUE, Color.RED, gamePanel, keyCodes);
        gamePanel.addGameObject(player1);
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
        //testing
        if (gamePanel.getKeyStatus(Constants.LEFT_ARROW)) {
            System.out.println("Left Arrow Pressed!");
        }
        if (gamePanel.getKeyStatus(Constants.RIGHT_ARROW)) {
            System.out.println("Right Arrow Pressed!");
        }
        if (gamePanel.getKeyStatus(Constants.DOWN_ARROW)) {
            System.out.println("Down Arrow Pressed!");
        }
        if (gamePanel.getKeyStatus(Constants.UP_ARROW)) {
            System.out.println("Up Arrow Pressed!");
        }

    }
}
