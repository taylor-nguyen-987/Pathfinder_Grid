import java.util.List;

public interface PathingStrategy {

    List<Point> getPath();
    boolean computePath(Point pos);

}
