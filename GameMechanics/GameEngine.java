package GameMechanics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import Utils.Constants;

public class GameEngine implements Runnable{
    private static final int TILE_SIZE = 48;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    JFrame frame;
    GamePanel gamePanel;
    Thread gameThread;
    World world;
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
        int wallWidth = 10;
        int w = WINDOW_WIDTH - wallWidth * 2;
        int h = WINDOW_HEIGHT - wallWidth * 2;
        if (aspectRatio > 1) {
            cols = w / TILE_SIZE;
            rows = (int)Math.ceil(cols / aspectRatio);
            topLeftX = (w % TILE_SIZE) / 2;
            topLeftY = (h - rows * TILE_SIZE) / 2;
        } else {
            rows = h / TILE_SIZE;
            cols = (int)Math.ceil(rows * aspectRatio);
            topLeftY = (h % TILE_SIZE) / 2;
            topLeftX = (w - cols * TILE_SIZE) / 2;
        }
        world = new World(gamePanel, topLeftX + wallWidth, topLeftY + wallWidth, rows, cols, TILE_SIZE, wallWidth);
        gamePanel.addGameObject(world);
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
