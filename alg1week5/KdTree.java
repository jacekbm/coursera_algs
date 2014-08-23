import java.util.ArrayList;

public class KdTree {

    private Node2D root;
    private int count;
    
    private class Node2D {
        
        private Point2D p;    // point
        private boolean xcut; // true if veritcal cut - by x
        private Node2D l;     // lower value - left or bottom
        private Node2D h;     // higher value - right or top
        
        public Node2D(Point2D p, boolean xcut) {
            this.p = p;
            this.xcut = xcut;
        }
        
        private int insertL(Point2D q)
        {
            if (this.l != null)
            {
                return this.l.insert(q);
            }
            else
            {
                this.l = new Node2D(q, !this.xcut);
                return 1;
            }                
        }
        
        private int insertH(Point2D q)
        {
            if (this.h != null)
            {
                return this.h.insert(q);
            }
            else
            {
                this.h = new Node2D(q, !this.xcut);
                return 1;
            }                
        }
        
        public int insert(Point2D q) {

            if (this.p.equals(q))
            {
                return 0;
            }
            
            if (this.xcut && q.x() < this.p.x())
            {
                return insertL(q);
            }
            else if (!this.xcut && q.y() < this.p.y())
            {
                return insertL(q);                
            }
            else
            {
                return insertH(q);
            }
        }
        
        private boolean containsL(Point2D q)
        {
            if (this.l != null)
            {
                return this.l.contains(q);
            }
            else
            {
                return false;
            }
        }
        
        private boolean containsH(Point2D q)
        {
            if (this.h != null)
            {
                return this.h.contains(q);
            }
            else
            {
                return false;
            }
        }
        
        public boolean contains(Point2D q)
        {
            if (this.p.equals(q))
            {
                return true;
            }
            
            if (this.xcut && q.x() < this.p.x())
            {
                return containsL(q);
            }
            else if (!this.xcut && q.y() < this.p.y())
            {
                return containsL(q);              
            }
            else
            {
                return containsH(q);
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
        
        public void range(ArrayList<Point2D> a, RectHV r)
        {
            if (r.contains(this.p))
            {
                a.add(p);
            }
            
            if (this.xcut)
            {
                if (this.p.x() >= r.xmin() && this.l != null)
                {
                    this.l.range(a, r);
                }
                if (this.p.x() <= r.xmax() && this.h != null)
                {
                    this.h.range(a, r);
                }
            }
            else
            {
                if (this.p.y() >= r.ymin() && this.l != null)
                {
                    this.l.range(a, r);
                }
                if (this.p.y() <= r.ymax() && this.h != null)
                {
                    this.h.range(a, r);
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
        
        private void nearest(Point2D q, PointDist best)
        {
            best.update(this.p, this.p.distanceTo(q));

            if (this.xcut)
            {
                if (q.x() - this.p.x() < best.d() && this.l != null)
                {
                    this.l.nearest(q, best);
                }
                if (this.p.x() - q.x() < best.d() && this.h != null)
                {
                    this.h.nearest(q, best);
                }
            }
            else
            {
                if (q.y() - this.p.y() < best.d() && this.l != null)
                {
                    this.l.nearest(q, best);
                }
                if (this.p.y() - q.y() < best.d() && this.h != null)
                {
                    this.h.nearest(q, best);
                }
            }
        }
        
        public Point2D nearest(Point2D q) {
            
            PointDist pd = new PointDist(this.p, this.p.distanceTo(q));
            nearest(q, pd);
            return pd.p();
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
            root = new Node2D(p, true);
            count = 1;
        }
        else
        {
            count += root.insert(p);
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
            return root.contains(p);
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
            root.range(a, rect);
        }

        return a;
    }
    
    /**
     * a nearest neighbor in the set to p; null if set is empty
     */
    public Point2D nearest(Point2D q) {

        if (root != null)
        {
            return root.nearest(q);
        }
        else
        {
            return null;
        }
    }
}