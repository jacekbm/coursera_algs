
public class Brute {
    
    private static Point[] points;
    
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
        for (int i = 0; i < points.length; i++)
        {
            for (int j = i + 1; j < points.length; j++)
            {
                double slopeij = points[i].slopeTo(points[j]);
                
                for (int k = j + 1; k < points.length; k++)
                {
                    double slopeik = points[i].slopeTo(points[k]);
                    
                    if (equals(slopeij, slopeik))
                    {
                        for (int l = k + 1; l < points.length; l++)
                        {
                            double slopeil = points[i].slopeTo(points[l]);
                            
                            if (equals(slopeij, slopeil))
                            {
                                PointArray pa = new PointArray();
                                pa.add(points[i]);
                                pa.add(points[j]);
                                pa.add(points[k]);
                                pa.add(points[l]);
                                pa.sort();
                                pa.print();
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    public static void main(String[] args)
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
       
        readPoints(args[0]);
        
        findCollinear();
        
    }
}