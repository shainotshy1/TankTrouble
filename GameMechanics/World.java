package GameMechanics;

import GameObjects.GameObject;
import GameObjects.Wall;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class World implements GameObject {
    private class GridBlock implements GameObject {
        public boolean bottom = true;
        public boolean right = true;
        private Wall bottomWall;
        private Wall rightWall;
        public GridBlock(double x, double y, double wallWidth, double tileSize, Color color) {
            bottomWall = new Wall(x, y + tileSize - wallWidth, tileSize, wallWidth, color);
            rightWall = new Wall(x + tileSize - wallWidth, y, wallWidth, tileSize, color);
        }

        @Override
        public void display(Graphics2D g2) {
            if (bottom) {
                bottomWall.display(g2);
            }
            if (right) {
                rightWall.display(g2);
            }
        }
    }
    List<GridBlock> blocks;
    List<Wall> borders;
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
        blocks = new ArrayList<>();
        borders = new ArrayList<>();
        generateMap();
    }

    private void generateBorders(Color color) {
        double shift = wallWidth / 2;
        double x_c = x - shift;
        double y_c = y - shift;
        Wall wall1 = new Wall(x_c, y_c, worldWidth + shift * 2, wallWidth, color); //top
        Wall wall2 = new Wall(x_c, y_c + worldHeight, worldWidth + shift, wallWidth, color); //bottom
        Wall wall3 = new Wall(x_c, y_c, wallWidth, worldHeight + shift * 2, color); //left
        Wall wall4 = new Wall(x_c + worldWidth, y_c, wallWidth, worldHeight, color); //right
        borders.add(wall1);
        borders.add(wall2);
        borders.add(wall3);
        borders.add(wall4);
    }

    private void generateObstacles(Color color) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                double xPos = x + i * tileSize + wallWidth / 2;
                double yPos = y + j * tileSize + wallWidth / 2;
                GridBlock block = new GridBlock(xPos, yPos, wallWidth, tileSize, color);
                blocks.add(block);
            }
        }
    }

    private void generateMap() {
        borders.clear();
        blocks.clear();
        Color color = Color.WHITE;
        generateBorders(color);
        generateObstacles(color);
    }

    @Override
    public void display(Graphics2D g2) {
        for (GridBlock block : blocks) {
            block.display(g2);
        }
        for (Wall wall : borders) {
            wall.display(g2);
        }
    }
}
