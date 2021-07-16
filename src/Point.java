import java.util.Objects;

public class Point
{
    private int x;
    private int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public void setX(int x1)
    {
        this.x = x1;
    }
    public void setY(int y1)
    {
        this.y = y1;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!this.getClass().equals(other.getClass())) {
            return false;
        }
        Point o = (Point) other;
        return this.x == o.x && this.y == o.y;
    }

}
