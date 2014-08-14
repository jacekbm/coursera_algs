import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();
        
    private class SlopeComparator implements Comparator<Point>
    {
        @Override
        public int compare(Point p1, Point p2)
        {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);
            return Double.compare(s1, s2);
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point p) {
        StdDraw.line(this.x, this.y, p.x, p.y);
    }

    // slope between this point and that point
    public double slopeTo(Point p) {
        if (x == p.x)
        {
            if (y == p.y)
            {
                return Double.NEGATIVE_INFINITY;
            }
            else
            {
                return Double.POSITIVE_INFINITY;
            }
        }
        
        if (y == p.y)
        {
            return 0.0; // positive
        }
        else
        {
            return (double) (p.y - y) / (double) (p.x - x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point p) {
        if (y != p.y) return y - p.y;
        if (x != p.x) return x - p.x;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    // unit test
    public static void main(String[] args) {
    }
}