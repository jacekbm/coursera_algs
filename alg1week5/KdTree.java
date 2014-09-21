import java.util.ArrayList;

public class KdTree {

    private Node2D root;
    private int count;
    
    private class Node2D {
        
        private Point2D p;    // point
        private Node2D l;     // lower value - left or bottom
        private Node2D h;     // higher value - right or top
        
        public Node2D(Point2D p) {
            this.p = p;
            this.l = null;
            this.h = null;
        }
        
        public int insert(Point2D q, boolean xcut) {

            if (this.p.equals(q))
            {
                return 0;
            }
            
            if ((xcut && q.x() < this.p.x())
                    || (!xcut && q.y() < this.p.y()))
            {
                if (this.l != null)
                {
                    return this.l.insert(q, !xcut);
                }
                else
                {
                    this.l = new Node2D(q);
                    return 1;
                }                
            }
            else
            {
                if (this.h != null)
                {
                    return this.h.insert(q, !xcut);
                }
                else
                {
                    this.h = new Node2D(q);
                    return 1;
                }
            }
        }
        
        public boolean contains(Point2D q, boolean xcut)
        {
            if (this.p.equals(q))
            {
                return true;
            }
            
            if ((xcut && q.x() < this.p.x())
                    || (!xcut && q.y() < this.p.y()))
            {
                if (this.l != null)
                {
                    return this.l.contains(q, !xcut);
                }
                else
                {
                    return false;
                }
            }
            else
            {
                if (this.h != null)
                {
                    return this.h.contains(q, !xcut);
                }
                else
                {
                    return false;
                }
            }
        }

        public void draw()
        {
            StdDraw.point(p.x(), p.y());
            
            if (this.l != null)
            {
                this.l.draw();
            }
            if (this.h != null)
            {
                this.h.draw();
            }
        }
        
        public void range(ArrayList<Point2D> a, RectHV r, boolean xcut)
        {
            if (r.contains(this.p))
            {
                a.add(p);
            }
            
            if (xcut)
            {
                if (this.p.x() >= r.xmin() && this.l != null)
                {
                    this.l.range(a, r, !xcut);
                }
                if (this.p.x() <= r.xmax() && this.h != null)
                {
                    this.h.range(a, r, !xcut);
                }
            }
            else
            {
                if (this.p.y() >= r.ymin() && this.l != null)
                {
                    this.l.range(a, r, !xcut);
                }
                if (this.p.y() <= r.ymax() && this.h != null)
                {
                    this.h.range(a, r, !xcut);
                }
            }
        }
        
        private void nearest(Point2D q, PointDist best, RectHV bbox, boolean xcut)
        {
            best.update(this.p, this.p.distanceSquaredTo(q));

            if (xcut)
            {
                if (this.l != null)
                {
                    RectHV left = new RectHV(bbox.xmin(), bbox.ymin(), this.p.x(), bbox.ymax());
                    if (left.distanceSquaredTo(q) < best.d())
                    {
                        this.l.nearest(q, best, left, !xcut);
                    }
                }
                if (this.h != null)
                {
                    RectHV right = new RectHV(this.p.x(), bbox.ymin(), bbox.xmax(), bbox.ymax());
                    if (right.distanceSquaredTo(q) < best.d())
                    {
                        this.h.nearest(q, best, right, !xcut);
                    }
                }
            }
            else
            {
                if (this.l != null)
                {
                    RectHV bottom = new RectHV(bbox.xmin(), bbox.ymin(), bbox.xmax(), this.p.y());
                    if (bottom.distanceSquaredTo(q) < best.d())
                    {
                        this.l.nearest(q, best, bottom, !xcut);
                    }
                }
                if (this.h != null)
                {
                    RectHV top = new RectHV(bbox.xmin(), this.p.y(), bbox.xmax(), bbox.ymax());
                    if (top.distanceSquaredTo(q) < best.d())
                    {
                        this.h.nearest(q, best, top, !xcut);
                    }
                }
            }
        }
    }
    
    private class PointDist {
        
        private Point2D p;
        private double d;
        
        public PointDist(Point2D p, double d) {
            this.p = p;
            this.d = d;
        }
        
        public Point2D p() { return p; }
        public double d() { return d; }
        
        public void update(Point2D q, double dist)
        {
            if (this.d > dist)
            {
                this.d = dist;
                this.p = q;
            }
        }
    }   
    
    /**
     * construct an empty set of points
     */
    public KdTree() {
        
        root = null;
        count = 0;
    }
    
    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        
        return count == 0;
    }
    
    /**
     * number of points in the set
     */
    public int size() {

        return count;
    }
    
    /**
     * add the point p to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        
        if (root == null)
        {
            root = new Node2D(p);
            count = 1;
        }
        else
        {
            count += root.insert(p, true);
        }
    }
    
    /**
     * does the set contain the point p?
     */
    public boolean contains(Point2D p) {

        if (root == null)
        {
            return false;
        }
        else
        {
            return root.contains(p, true);
        }
    }
    
    /**
     * draw all of the points to standard draw
     */
    public void draw() {

        if (root != null)
        {
            root.draw();
        }
    }

    /**
     * all points in the set that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {

        ArrayList<Point2D> a = new ArrayList<Point2D>();
        
        if (root != null)
        {
            root.range(a, rect, true);
        }

        return a;
    }
    
    /**
     * a nearest neighbor in the set to p; null if set is empty
     */
    public Point2D nearest(Point2D q) {

        if (root != null)
        {
            PointDist pd = new PointDist(null, Double.POSITIVE_INFINITY);
            root.nearest(q, pd, new RectHV(0.0, 0.0, 1.0, 1.0), true);
            return pd.p();
        }                   
        else
        {
            return null;
        }
    }
}