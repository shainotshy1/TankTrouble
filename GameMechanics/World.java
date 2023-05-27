package GameMechanics;

import GameObjects.GameObject;
import GameObjects.Wall;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class World implements GameObject {
    List<Wall> walls;
    GamePanel gamePanel;
    int x;
    int y;
    int rows;
    int cols;
    int tileSize;
    int worldWidth;
    int worldHeight;
    double wallWidth;
    public World(GamePanel gamePanel, int x, int y, int rows, int cols, int tileSize, double wallWidth) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.rows = rows;
        this.cols = cols;
        this.tileSize = tileSize;
        this.wallWidth = wallWidth;
        worldWidth = cols * tileSize;
        worldHeight = rows * tileSize;
        walls = new ArrayList<>();
        generateMap();
    }

    private void generateBorders(Color color, double size) {
        Wall wall1 = new Wall(x - size, y - size, worldWidth + 2 * size, size, color); //top
        Wall wall2 = new Wall(x - size, y + worldHeight, worldWidth + 2 * size, size, color); //bottom
        Wall wall3 = new Wall(x - size, y - size, size, worldHeight + 2 * size, color); //left
        Wall wall4 = new Wall(x + worldWidth, y - size, size, worldHeight + 2 * size, color); //right
        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
    }

    private void generateObstacles(Color color, double size) {
        for (int j = 1; j < rows; j++) {
            for (int i = 0; i < cols; i+=2) {
                Wall wall = new Wall(x + (j % 2 + i) * tileSize, y + j * tileSize, tileSize, size, color);
                walls.add(wall);
            }
        }
    }

    private void generateMap() {
        walls.clear();
        Color color = Color.WHITE;
        generateBorders(color, wallWidth);
        generateObstacles(color, wallWidth);
    }

    @Override
    public void display(Graphics2D g2) {
        for (Wall wall : walls) {
            wall.display(g2);
        }
    }
}
