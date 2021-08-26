import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class breadth implements PathingStrategy{

    private PathMain.GridValues[][] grid;
    private List<Point> path;

    public breadth(PathMain.GridValues[][] grid, List<Point> path) {
        this.grid = grid;
        this.path = path;
    }
    public List<Point> getPath() {
        return this.path;
    }

    //BFS iterative
    public boolean computePath(Point pos)
    {

        boolean found = false;
        Node last = new Node(null, null); //destination node

        ArrayList<Node> nodeQueue = new ArrayList<>();

        nodeQueue.add(new Node(pos, null));

        while (!nodeQueue.isEmpty()) {
            Node current = nodeQueue.remove(0);

            if (this.grid[current.getPosition().getY()][current.getPosition().getX()] == PathMain.GridValues.GOAL)
            {
                last = current;
                found = true;
                break;
            }
            else {
                if (this.grid[current.getPosition().getY()][current.getPosition().getX()] != PathMain.GridValues.SEARCHED) //if the current has not already been searched
                {
                    this.grid[current.getPosition().getY()][current.getPosition().getX()] = PathMain.GridValues.SEARCHED;


                    for (Node neighbor: current.getNeighbors()) {
                        if (PathMain.withinBounds(neighbor.getPosition(), this.grid) &&
                                this.grid[neighbor.getPosition().getY()][neighbor.getPosition().getX()] != PathMain.GridValues.OBSTACLE &&
                                this.grid[neighbor.getPosition().getY()][neighbor.getPosition().getX()] != PathMain.GridValues.SEARCHED) {
                            nodeQueue.add(neighbor);
                        }
                    }
                }
            }
        }

        this.path = reconstruct_path(last);

        return found;

    }

    private static List<Point> reconstruct_path(Node end) {
        List<Point> path = new ArrayList<>();
        Node current = end;
        path.add(end.getPosition());
        while(current.getPriorNode() != null) {
            path.add(current.getPriorNode().getPosition());
            current = current.getPriorNode();
        }
        Collections.reverse(path);
        return path;
    }

}
