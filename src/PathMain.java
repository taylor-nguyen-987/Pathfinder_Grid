import java.util.*;

import processing.core.*;

public class PathMain extends PApplet{

    private PImage obstacle;
    private PImage goal;
    private PImage start;
    public List<Point> path;

    private static final int TILE_SIZE = 32;

    public enum GridValues {BEGIN, BACKGROUND, OBSTACLE, GOAL, SEARCHED };

    private GridValues[][] grid;
    private static final int ROWS = 15;
    private static final int COLS = 20;

    private boolean drawPath = false;
    private int count = 0;
    private int mouse;

    private Point begin;
    public static Point end;

    public void settings() {
        size(640,480);
    }

    public void setup()
    {
        path = new LinkedList<>();
        //begin = new Point(3, 7);
        //end = new Point(14, 7);

        start = loadImage("images/bird.png");
        obstacle = loadImage("images/oak.png");
        goal = loadImage("images/nest.png");

        grid = new GridValues[ROWS][COLS];
        initialize_grid(grid);
        frameRate(20);

    }

    private static void initialize_grid(GridValues[][] grid)
    {


        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[row].length; col++)
            {
                grid[row][col] = GridValues.BACKGROUND;
            }
        }

        //set up some obstacles


        for (int col = 2; col < 9; col++)
        {
            grid[8][col] = GridValues.OBSTACLE;
        }


        for (int row = 2; row < 8; row++) //side hallway1
        {
            grid[row][row + 5] = GridValues.OBSTACLE;
        }

        for (int row = 3; row<=8; row++) //side hallway 2
        {
            grid[row][row+2] = GridValues.OBSTACLE;
        }

        for (int col = 7; col <= 17; col++)
        {
            grid[2][col]=GridValues.OBSTACLE;
        }

        for (int row = 2; row<=5;row++) {
            grid[row][17] = GridValues.OBSTACLE;
        }

        for (int row = 8; row <= 12; row++)
        {
            grid[row][17] = GridValues.OBSTACLE;
        }

        for (int row = 8; row < 12; row++)
        {
            grid[row][19 - row] = GridValues.OBSTACLE;
        }

        for (int col=13; col<= 15; col++)
        {
            grid[5][col]=GridValues.OBSTACLE;
        }
        grid[8][16] = GridValues.OBSTACLE;
        grid[6][15] = GridValues.OBSTACLE;
        grid[7][15] = GridValues.OBSTACLE;

        for (int col = 0; col < 8; col++)
        {
            grid[11][col] = GridValues.OBSTACLE;
        }
        for (int col = 14; col<=16; col++)
        {
            grid[12][col]=GridValues.OBSTACLE;
        }

        grid[13][9] = GridValues.OBSTACLE;
        grid[14][9] = GridValues.OBSTACLE;
        for (int col = 10; col <= 12; col++)
        {
            grid[13][col]=GridValues.OBSTACLE;
        }


        if (end != null) {
            grid[end.getY()][end.getX()] = GridValues.GOAL;
        }
    }

    /* runs repeatedly */
    public void draw()
    {
        draw_grid();

        draw_path(this.path);
    }

    private void draw_grid()
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[row].length; col++)
            {
                draw_tile(row, col);
            }
        }
    }

    private void draw_path(List<Point> path)
    {
        if (drawPath) {

            if (count != path.size()-1) { //animating the path step by step

                for (int i=0; i<=count; i++) {
                    fill(255, 215, 0);
                    rect(path.get(i).getX() * TILE_SIZE + TILE_SIZE * 3 / 8,
                            path.get(i).getY() * TILE_SIZE + TILE_SIZE * 3 / 8,
                            TILE_SIZE / 4, TILE_SIZE / 4);
                }
                count++;
            } else { //the whole path is animated
                for (Point p : path) {
                    fill(255, 215, 0);
                    rect(p.getX() * TILE_SIZE + TILE_SIZE * 3 / 8,
                            p.getY() * TILE_SIZE + TILE_SIZE * 3 / 8,
                            TILE_SIZE / 4, TILE_SIZE / 4);
                }
            }
        }

    }

    private void draw_tile(int row, int col)
    {
        switch (grid[row][col])
        {
            case BEGIN:
                image(start, col * TILE_SIZE, row * TILE_SIZE);
                break;
            case BACKGROUND:
                fill(211, 211, 211);
                rect(col * TILE_SIZE,
                        row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                break;
            case OBSTACLE:
                image(obstacle, col * TILE_SIZE, row * TILE_SIZE);
                break;
            case SEARCHED:
                fill(47, 79, 79);
                rect(col * TILE_SIZE + TILE_SIZE / 4,
                        row * TILE_SIZE + TILE_SIZE / 4,
                        TILE_SIZE / 2, TILE_SIZE / 2);
                break;
            case GOAL:
                image(goal, col * TILE_SIZE, row * TILE_SIZE);
                break;
        }
    }

    public void mousePressed()
    {
        Point pressed = mouseToPoint(mouseX, mouseY);

        if (grid[pressed.getY()][pressed.getX()] == GridValues.BACKGROUND && (mouse == 0) && begin == null) {
            grid[pressed.getY()][pressed.getX()] = GridValues.BEGIN;
            mouse++;
            begin = new Point(pressed.getX(), pressed.getY());
        }
        else if (grid[pressed.getY()][pressed.getX()] == GridValues.BACKGROUND && (mouse == 1) && end == null) {
            grid[pressed.getY()][pressed.getX()] = GridValues.GOAL;
            mouse=0;
            end = new Point(pressed.getX(), pressed.getY());
        }

        redraw();

    }

    private Point mouseToPoint(int x, int y)
    {
        return new Point(mouseX/TILE_SIZE, mouseY/TILE_SIZE);
    }

    public void keyPressed()
    {
        if (key == 'd' && begin != null && end != null) //DFS
        {
            drawPath = false; //switches off drawPath
            count = 0; // resets counter

            //clear out prior path and re-initialize grid

            path.clear();
            initialize_grid(grid);

            depth searching = new depth(grid, path);
            searching.computePath(begin);

            this.path = searching.getPath();
            Collections.reverse(this.path); //this path is originally backwards, so we need to reverse it

        }

        else if (key == 'b' && begin != null && end != null) //BFS
        {
            drawPath = false; //switches off drawPath
            count = 0;

            path.clear();
            initialize_grid(grid);

            breadth searching = new breadth(grid, path);
            searching.computePath(begin);

            this.path = searching.getPath();

        }

        else if (key == 'a' && begin != null && end != null) //A*
        {
            drawPath = false; //switches off drawPath
            count = 0;

            path.clear();
            initialize_grid(grid);

            astar searching = new astar(grid, path);
            searching.computePath(begin);

            this.path = searching.getPath();
        }

        else if (key == 'p') //path
        {
            if (this.path.size() > 0) {
                drawPath ^= true;
            }
        }

        else if (key == 'c') //clear
        {
            drawPath = false;
            count = 0; // reset counter

            begin = null;
            end = null;

            path.clear(); //clears Path
            initialize_grid(grid);
        }

    }

    public static boolean withinBounds(Point p, PathMain.GridValues[][] grid) //use an interface
    {
        return p.getY() >= 0 && p.getY() < grid.length &&
                p.getX() >= 0 && p.getX() < grid[0].length;
    }

    public static void main(String args[])
    {
        PApplet.main("PathMain");
    }

}

//<div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
