import java.util.Comparator;
import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> set;
    
    private static class XYOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.x() < q.x()) return -1;
            if (p.x() > q.x()) return +1;
            if (p.y() < q.y()) return -1;
            if (p.y() > q.y()) return +1;
            return 0;
        }
    }
    
    /**
     * construct an empty set of points
     */
    public PointSET() {

        set = new TreeSet<Point2D>(new XYOrder()); // Point2D.X_ORDER
    }
    
    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        
        return set.isEmpty();
    }
    
    /**
     * number of points in the set
     */
    public int size() {
        
        return set.size();
    }
    
    /**
     * add the point p to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        
        set.add(p);
    }
    
    /**
     * does the set contain the point p?
     */
    public boolean contains(Point2D p) {
        
        return set.contains(p);
    }
    
    /**
     * draw all of the points to standard draw
     */
    public void draw() {

        throw new UnsupportedOperationException();
    }

    /**
     * all points in the set that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {

        ArrayList<Point2D> a = new ArrayList<Point2D>();
        
        for (Point2D p : set)
        {
            if (rect.contains(p))
            {
                a.add(p);
            }
        }

        return a;
    }
    
    /**
     * a nearest neighbor in the set to p; null if set is empty
     */
    public Point2D nearest(Point2D q) {

        Point2D pmin = null;
        double dmin = Double.POSITIVE_INFINITY;
        
        for (Point2D p : set)
        {
            double d = p.distanceTo(q);
            
            if (dmin > d)
            {
                dmin = d;
                pmin = p;
            }
        }
        
        return pmin;
    }
}