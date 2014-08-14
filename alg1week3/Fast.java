import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;


public class Fast {
    private static Point[] points;
    private static Set<PointArray> collinears;
    
    private static final double E = 0.00000000001;
    private static boolean equals(double a, double b)
    {
        if (a == Double.POSITIVE_INFINITY && b == Double.POSITIVE_INFINITY)
        {
            return true;
        }
        
        return Math.abs(a - b) < E;
    }
    
    private static class PointArray {

        private int hash = 0;
        private Point[] a;
        private int N = 0;

        public PointArray() {
            a = new Point[4];
        }

        public int size() {
            return N;
        }

        private void resize(int capacity) {
            
            if (a.length == capacity) return;
            
            Point[] temp = new Point[capacity];
            for (int i = 0; i < N; i++)
                temp[i] = a[i];
            a = temp;
        }

        public void add(Point item) {
            if (N == a.length) resize(2 * a.length);
            a[N++] = item;
        }

        public void sort()
        {
            resize(N); // align an array to be able to invoke sort method
            Insertion.sort(a);
        }
        
        public void print()
        {
            // print
            for (int i = 0; i < N; i++)
            {
                if (i != 0)
                {
                    StdOut.print(" -> ");
                }
           
                StdOut.print(a[i]);
            }
            StdOut.println();
            
            // draw
            if (N > 1)
            {
                a[0].drawTo(a[N - 1]);
            }
        }
        
        @Override
        public int hashCode()
        {
            if (hash == 0)
            {
                hash = 1;
                for (int i = 0; i < a.length; i++)
                {
                    hash = 31 * hash + a[i].toString().hashCode();
                }
            }
            
            return hash;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (o == this) return true;
            if (!(o instanceof PointArray)) return false;
            PointArray pa = (PointArray) o;

            if (a.length != pa.a.length) return false;
            for (int i = 0; i < a.length; i++)
            {
                if (a[i] != pa.a[i]) return false;
            }
            return true;
        }        
    }

    private static void readPoints(String file)
    {
        In in = new In(file);
        
        int count = in.readInt();
        points = new Point[count];
        
        for (int i = 0; i < count; i++)
        {
            int x = in.readInt();
            int y = in.readInt();
            
            points[i] = new Point(x, y);            
            points[i].draw();
        }
    }
    
    private static void findCollinear()
    {
        collinears = new HashSet<PointArray>();
        
        for (int i = 0; i < points.length; i++)
        {
            // temporary points -> without i-point
            Point[] spoints = new Point[points.length - 1];
            int k = 0;
            for (int j = 0; j < points.length; j++)
            {
                if (j != i)
                {
                    spoints[k++] = points[j];
                }
            }
            
            // sort by slope
            Arrays.sort(spoints, points[i].SLOPE_ORDER);
                
            // find sequence with the same slope
            PointArray pa = new PointArray();
            double slopePrev = Double.NaN;
            for (int j = 0; j < spoints.length; j++)
            {
                double slope = points[i].slopeTo(spoints[j]);
                
                if (equals(slopePrev, slope)) // continue adding
                {
                    pa.add(spoints[j]);
                }
                else // just finish previous sequence
                {
                    if (pa.size() >= 3) // find only 4+
                    {
                        pa.add(points[i]);
                        pa.sort();
                        collinears.add(pa);
                    }
                    
                    pa = null;
                }

                if (pa == null) // new sequence
                {
                    pa = new PointArray();
                    pa.add(spoints[j]);
                }
                
                slopePrev = slope;                
            }

            if (pa.size() >= 3) // find only 4+
            {
                pa.add(points[i]);
                pa.sort();
                collinears.add(pa);
            }
        }
    }
    

    private static void printCollinear()
    {
        for (PointArray pa : collinears)
        {
            pa.print();
        }
    }
    
    
    public static void main(String[] args)
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
       
        readPoints(args[0]);

        findCollinear();
        printCollinear();
    }
}