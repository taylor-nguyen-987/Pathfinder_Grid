import java.util.List;

public class depth implements PathingStrategy {

    private PathMain.GridValues[][] grid;
    private List<Point> path;

    public depth(PathMain.GridValues[][] grid, List<Point> path) {
        this.grid = grid;
        this.path = path;
    }

    public List<Point> getPath() {
        return this.path;
    }

    //DFS recursive
    public boolean computePath(Point pos) { //given the starting point as the parameter
        Point rightN = new Point(pos.getX() +1, pos.getY());
        Point bottomN = new Point(pos.getX(), pos.getY() + 1); //going down is positive!!!
        Point topN = new Point(pos.getX(), pos.getY() - 1); //going up is negative!!!
        Point leftN = new Point(pos.getX() - 1, pos.getY());
        boolean found = false;

        //test if this is a valid grid cell
        if (PathMain.withinBounds(pos, this.grid)  &&
                this.grid[pos.getY()][pos.getX()] != PathMain.GridValues.OBSTACLE &&
                this.grid[pos.getY()][pos.getX()] != PathMain.GridValues.SEARCHED) { //checks whether it is withinbounds and did not hit obstacles

            if (this.grid[pos.getY()][pos.getX()] == PathMain.GridValues.GOAL) { //if the current position is the goal
                this.path.add(pos);
                found = true;
            } else { //if it is not the goal
                //set this value as searched
                this.grid[pos.getY()][pos.getX()] = PathMain.GridValues.SEARCHED;
                found = this.computePath(rightN) || this.computePath(leftN) ||
                        this.computePath(bottomN) || this.computePath(topN);
                if (found == true) {
                    this.path.add(pos);
                }
            }
        }
        return found;
    }


}
