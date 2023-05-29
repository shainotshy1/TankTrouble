package GameMechanics;

import GameObjects.Ball;
import Utils.Vector2d;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

public class GameEngine implements Runnable{
    private static final int WALL_WIDTH = 5;
    private static final int TILE_SIZE = 48;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private JFrame frame;
    private GamePanel gamePanel;
    private Thread gameThread;
    private World world;
    private Ball testBall;
    private int rows, cols, topLeftX, topLeftY;
    private double last_time;

    public GameEngine() {
        frame = new JFrame();
        gamePanel = new GamePanel(WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLACK);
        setupFrame(gamePanel);

        double test_aspect_ratio = 2.5;
        generateWorld(test_aspect_ratio, WALL_WIDTH);
        last_time = 0.0;
    }

    public void generateWorld(double aspectRatio, int wallWidth) {
        int w = WINDOW_WIDTH;
        int h = WINDOW_HEIGHT;
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
        world = new World(gamePanel, topLeftX, topLeftY, rows, cols, TILE_SIZE, wallWidth, 1);
        Vector2d center = new Vector2d(topLeftX + TILE_SIZE / 2, topLeftY + TILE_SIZE / 2);
        Vector2d velocity = new Vector2d(100, 100);
        testBall = new Ball(center, velocity, TILE_SIZE / 5, Color.WHITE);
        gamePanel.addGameObject(world);
        gamePanel.addGameObject(testBall);
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
        if (last_time == 0.0) {
            last_time = System.nanoTime();
            return;
        }
        double present_time = System.nanoTime();
        double frameDelta = (present_time - last_time) / 1000000000; //seconds
        testBall.update(frameDelta);

        last_time = present_time;
    }
}
