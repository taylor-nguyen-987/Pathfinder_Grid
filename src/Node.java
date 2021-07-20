import java.util.List;
import java.util.ArrayList;

public class Node {
    private Point position;
    private Node PriorNode;
    private double gScore = Double.POSITIVE_INFINITY; //set node's gScore to be infinity
    private double hScore = Double.POSITIVE_INFINITY;
    private double fScore = Double.POSITIVE_INFINITY;

    public Node(Point position, Node PriorNode) {
        this.position=position;
        this.PriorNode=PriorNode;
    }

    public Point getPosition() {
        return this.position;
    }
    public Node getPriorNode() {
        return this.PriorNode;
    }

    public double getgScore() {return this.gScore;}
    public double gethScore() {
        return this.hScore;
    }
    public double getfScore() {
        return this.fScore;
    }
    public void setgScore(double gScore) {
        this.gScore = gScore;
    }
    public void sethScore(double hScore) {
        this.hScore = hScore;
    }
    public void setfScore(double fScore) {
        this.fScore = fScore;
    }


    public List<Node> getNeighbors() { //does not filter out obstacles or searched blocks
        Node rightN = new Node(new Point(this.position.getX() + 1, this.position.getY()), this);
        Node bottomN = new Node(new Point(this.position.getX(), this.position.getY() + 1), this);
        Node topN = new Node(new Point(this.position.getX(), this.position.getY() - 1), this);
        Node leftN = new Node(new Point(this.position.getX() - 1, this.position.getY()), this);

        ArrayList<Node> neighbors = new ArrayList<>();
        neighbors.add(rightN);
        neighbors.add(bottomN);
        neighbors.add(topN);
        neighbors.add(leftN);

        return neighbors;
    }


    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!this.getClass().equals(other.getClass())) {
            return false;
        }
        Node o = (Node) other;
        return this.position.equals(o.getPosition()) && this.PriorNode.equals(o.getPriorNode());
    }

}
