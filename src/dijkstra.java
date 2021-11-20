import java.util.*;

public class dijkstra implements PathingStrategy{

    private PathMain.GridValues[][] grid;
    private List<Point> path;

    public dijkstra(PathMain.GridValues[][] grid, List<Point> path) {
        this.grid = grid;
        this.path = path;
    }
    public List<Point> getPath() {
        return this.path;
    }
    public boolean computePath(Point pos) {

        LinkedList<Node> makeQueue = new LinkedList<>();
        boolean found = false;
        Node start = new Node(pos, null);
        start.setdist(0);
        makeQueue.add(start);
        while (makeQueue.size() != 0) {
            Node current = makeQueue.poll(); //removes and retrieves the node with the lowest
            if (this.grid[current.getPosition().getY()][current.getPosition().getX()] == PathMain.GridValues.GOAL) {
                //if goal is found
                for (Point p: reconstruct_path(current)) {
                    this.path.add(p);
                }
                found = true;
                break;
            }
            this.grid[current.getPosition().getY()][current.getPosition().getX()] = PathMain.GridValues.SEARCHED; //mark as searched

            for (Node neighbor: current.getNeighbors()) {

                if (PathMain.withinBounds(neighbor.getPosition(), this.grid) &&
                        this.grid[neighbor.getPosition().getY()][neighbor.getPosition().getX()] != PathMain.GridValues.OBSTACLE &&
                        this.grid[neighbor.getPosition().getY()][neighbor.getPosition().getX()] != PathMain.GridValues.SEARCHED) {
                    makeQueue.add(neighbor);

                    if (neighbor.getdist() > current.getdist() + 1) {
                        neighbor.setdist(current.getdist() + 1);
                        neighbor.setPriorNode(current);
                    }
                }
            }
            Comparator<Node> comp2 = (s1, s2) -> (int) (s1.getdist() - s2.getdist());
            Collections.sort(makeQueue, comp2); //sorts makeQueue by dist least to greatest
        }
        return found;
    }

    private static List<Point> reconstruct_path(Node last) {
        List<Point> path = new ArrayList<>();
        Node current = last;
        path.add(last.getPosition());
        while(current.getPriorNode() != null) {
            path.add(current.getPriorNode().getPosition());
            current = current.getPriorNode();
        }
        Collections.reverse(path);
        return path;
    }
}

