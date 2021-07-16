import java.util.*;

public class astar implements PathingStrategy{

    private PathMain.GridValues[][] grid;
    private List<Point> path;

    public astar(PathMain.GridValues[][] grid, List<Point> path) {
        this.grid = grid;
        this.path = path;
    }

    public List<Point> getPath() {
        return this.path;
    }

    //A Star iterative
    public boolean computePath(Point pos) {


        //Comparator<Node> fcomp = (n1, n2) -> (int) ((n2.getfScore()) -
        //        (n1.getfScore()));

        //PriorityQueue<Node> openSet = new PriorityQueue<>(fcomp);


        LinkedList<Node> openSet = new LinkedList<>();
        LinkedList<Node> closedSet = new LinkedList<>();
        boolean found = false;

        Node start = new Node(pos, null);
        //Point end = new Point(14, 7);
        start.sethScore(manhattanDistance(pos, PathMain.end)); //heuristic distance
        start.setgScore(0); //set gScore to be 0

        openSet.add(start);


        while (openSet.size() != 0 && found == false) {

            Node current = openSet.poll(); //removes and retrieves the node with the lowest fScore from the openList

            //System.out.println(current.getPosition().hashCode());

            //System.out.println("x:  " + current.getPosition().getX() + " y: " + current.getPosition().getY());
            if (this.grid[current.getPosition().getY()][current.getPosition().getX()] == PathMain.GridValues.GOAL) {
                for (Point p: reconstruct_path(current)) {
                    this.path.add(p);
                }
                found = true;
            }

            this.grid[current.getPosition().getY()][current.getPosition().getX()] = PathMain.GridValues.SEARCHED; //mark as searched

            for (Node neighbor: current.getNeighbors()) {

                if (PathMain.withinBounds(neighbor.getPosition(), this.grid) &&
                        this.grid[neighbor.getPosition().getY()][neighbor.getPosition().getX()] != PathMain.GridValues.OBSTACLE &&
                        this.grid[neighbor.getPosition().getY()][neighbor.getPosition().getX()] != PathMain.GridValues.SEARCHED) { //check neighbors if valid

                    //Note that when the neighbor has been searched, it is in the closed list as well!

                    double new_gScore = current.getgScore() + 1; //distance from current node to start + distance from current node to adj node

                    if (new_gScore < neighbor.getgScore()) {
                        neighbor.setgScore(new_gScore);
                        double new_hScore = manhattanDistance(neighbor.getPosition(), PathMain.end); //heuristic
                        neighbor.sethScore(new_hScore);
                        double new_fScore = neighbor.getgScore() + neighbor.gethScore();
                        neighbor.setfScore(new_fScore);
                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
                    }

                }
            }
            closedSet.add(current); //move current node to closedset


            Comparator<Node> comp2 = (s1, s2) -> (int) (s1.getfScore() - s2.getfScore());
            Collections.sort(openSet, comp2); //sorts openSet from least to greatest fScore

        }

        return found;
    }


    private int manhattanDistance(Point p1, Point p2) {
        return Math.abs(p2.getX() - p1.getX()) + Math.abs(p2.getY() - p1.getY());
    }

    private static List<Point> reconstruct_path(Node last) {
        List<Point> path = new ArrayList<>();
        Node current = last; //might not include the last node
        path.add(last.getPosition());
        while(current.getPriorNode() != null) {
            path.add(current.getPriorNode().getPosition());
            current = current.getPriorNode();
        }
        Collections.reverse(path);
        return path;
    }

    //Debugging
    //          for (Node n: openSet) {
    //                if (current.getfScore() > n.getfScore()) {
    //                    System.out.println(current.getfScore());
    //                    System.out.println(n.getfScore());
    //                    System.out.println("ERROR: not the lowest fScore");
    //                }
    //            }

}
