package GameMechanics;

import GameObjects.GameObject;
import GameObjects.Wall;

import java.awt.*;
import java.util.*;
import java.util.List;

public class World implements GameObject {
    private class GridBlock implements GameObject {
        int row;
        int col;
        public boolean bottom = true;
        public boolean right = true;
        public boolean marked = false;
        private Wall bottomWall;
        private Wall rightWall;
        public GridBlock(int row, int col, double x, double y, double wallWidth, double tileSize, Color color) {
            this.row = row;
            this.col = col;
            bottomWall = new Wall(x - wallWidth, y + tileSize - wallWidth, tileSize + wallWidth, wallWidth, color);
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
    GridBlock[][] blocks;
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
    int seed;
    Random random;
    public World(GamePanel gamePanel, int x, int y, int rows, int cols, int tileSize, double wallWidth, int seed) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.rows = rows;
        this.cols = cols;
        this.tileSize = tileSize;
        this.wallWidth = wallWidth;
        this.seed = seed;
        this.random = new Random(seed);
        worldWidth = cols * tileSize;
        worldHeight = rows * tileSize;
        blocks = new GridBlock[rows][cols];
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
        if (rows == 0 || cols == 0) {
            return; //failsafe is grid is size 0
        }
        //initialize full grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double xPos = x + j * tileSize + wallWidth / 2;
                double yPos = y + i * tileSize + wallWidth / 2;
                GridBlock block = new GridBlock(i, j, xPos, yPos, wallWidth, tileSize, color);
                blocks[i][j] = block;
            }
        }
        GridBlock block = blocks[0][0];
        GridBlock prev = null;
        Stack<GridBlock> fringe = new Stack<>();
        List<GridBlock> spread = new ArrayList<>();
        fringe.add(block);
        while (fringe.size() != 0) {
            block = fringe.pop();
            block.marked = true;

            if (prev != null) {
                if (prev.row < block.row) { //top
                    prev.bottom = false;

                } else if (prev.row > block.row) { //bottom
                    block.bottom = false;

                } else if (prev.col < block.col) { //left
                    prev.right = false;
                } else if (prev.col > block.col) { //right
                    block.right = false;
                }
            }

            spread.clear();
            int left_row = block.row;
            int left_col = block.col - 1;
            int right_row = block.row;
            int right_col = block.col + 1;
            int top_row = block.row - 1;
            int top_col = block.col;
            int bot_row = block.row + 1;
            int bot_col = block.col;
            if (inBounds(left_row, left_col) && !blocks[left_row][left_col].marked) {
                spread.add(blocks[left_row][left_col]);
            }
            if (inBounds(right_row, right_col) && !blocks[right_row][right_col].marked) {
                spread.add(blocks[right_row][right_col]);
            }
            if (inBounds(top_row, top_col) && !blocks[top_row][top_col].marked) {
                spread.add(blocks[top_row][top_col]);
            }
            if (inBounds(bot_row, bot_col) && !blocks[bot_row][bot_col].marked) {
                spread.add(blocks[bot_row][bot_col]);
            }
            Collections.shuffle(spread, random);
            for (GridBlock neighbor : spread) {
                fringe.add(neighbor);
            }
            prev = block;
        }
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private void generateMap() {
        borders.clear();
        Color color = Color.WHITE;
        generateBorders(color);
        generateObstacles(color);
    }

    @Override
    public void display(Graphics2D g2) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                blocks[i][j].display(g2);
            }
        }
        for (Wall wall : borders) {
            wall.display(g2);
        }
    }
}