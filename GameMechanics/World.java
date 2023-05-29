package GameMechanics;

import Colliders.Collider;
import GameObjects.Collidable;
import GameObjects.GameObject;
import GameObjects.Wall;
import Utils.Vector2d;

import java.awt.*;
import java.util.*;
import java.util.List;

public class World implements GameObject {
    private static final double SPARSITY = 0.55;
    private class GridBlock implements GameObject {
        public int row, col;
        public boolean bottom = true;
        public boolean right = true;
        public boolean marked = false;
        public GridBlock prev;
        private Wall bottomWall;
        private Wall rightWall;
        private List<GameObject> objects;
        public GridBlock(int row, int col, double x, double y, double wallWidth, double tileSize, Color color) {
            this.row = row;
            this.col = col;
            bottomWall = new Wall(x - wallWidth, y + tileSize - wallWidth, tileSize + wallWidth, wallWidth, color);
            rightWall = new Wall(x + tileSize - wallWidth, y, wallWidth, tileSize, color);
            objects = new ArrayList<>(List.of(bottomWall, rightWall));
        }

        public List<GameObject> getObjects() {
            List<GameObject> res = new ArrayList<>();
            for (GameObject obj : objects) {
                res.add(obj);
            }
            return res;
        }

        public boolean addObject(GameObject obj) {
            return objects.add(obj);
        }

        public boolean removeObject(GameObject obj) {
            return objects.remove(obj);
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
    int x, y, rows, cols, tileSize, worldWidth, worldHeight;
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

    public boolean addObject(GameObject obj, int row, int col) {
        if (inBounds(row, col)) {
            return blocks[row][col].addObject(obj);
        }
        return false;
    }

    public boolean removeObject(GameObject obj, int row, int col) {
        if(inBounds(row, col)) {
            return blocks[row][col].removeObject(obj);
        }
        return false;
    }

    //returns the objects that the collider is colliding with
    public List<GameObject> getCollidingObjects(Collider collider) {
        List<GameObject> res = new ArrayList<>();
        Vector2d center = collider.getCenter();
        int row = (int) Math.floor((center.x - x) / tileSize);
        int col = (int) Math.floor((center.y - y) / tileSize);
        List<GameObject> neighbors = getBlockObjects(row, col);
        for (GameObject neighbor : neighbors) {
            if (neighbor instanceof Collidable) {
                Collidable c = (Collidable) neighbor;
                if (c.colliding(collider)) {
                    res.add(neighbor);
                }
            }
        }
        return res;
    }

    //gets objects within a block and from the block above and to the left since blocks have only right and bottom walls
    private List<GameObject> getBlockObjects(int row, int col) {
        List<GameObject> res = new ArrayList<>();
        if (inBounds(row, col)) { //grid block
            res.addAll(blocks[row][col].getObjects());
        }
        if (inBounds(row - 1, col)) { //top grid block
            res.addAll(blocks[row - 1][col].getObjects());
        }
        if (inBounds(row, col - 1)) { //left grid block
            res.addAll(blocks[row][col - 1].getObjects());
        }
        return res;
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

        //DFS maze generation
        GridBlock block = blocks[0][0];
        Stack<GridBlock> fringe = new Stack<>();
        List<GridBlock> spread = new ArrayList<>();
        fringe.add(block);
        while (fringe.size() != 0) {
            block = fringe.pop();
            block.marked = true;

            if (block.prev != null) {
                if (block.prev.row < block.row) { //top
                    block.prev.bottom = false;
                } else if (block.prev.row > block.row) { //bottom
                    block.bottom = false;
                } else if (block.prev.col < block.col) { //left
                    block.prev.right = false;
                } else if (block.prev.col > block.col) { //right
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
            //prev incorrect
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
                neighbor.prev = block;
                fringe.add(neighbor);
            }
        }

        //reduce number of obstacle walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (random.nextDouble() < SPARSITY) {
                    blocks[i][j].bottom = false;
                    blocks[i][j].right = false;
                }
            }
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
