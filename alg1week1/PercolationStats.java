public class PercolationStats {
    
    private int mN, mT;
    private double count;
    private double[] stats;
    
    private double coeff;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        
        if (N <= 0 || T <= 0)
        {
            throw new java.lang.IllegalArgumentException();
        }
        
        mN = N;
        mT = T;
        count = mN * mN;
        stats = new double[T];
        
        coeff = 1.96 / Math.sqrt(mT);

        performSimulation();
    }
  
    private void performSimulation()
    {
        for (int t = 0; t < mT; t++)
        {
            int n = simulate(mN);
            stats[t] = (double) n / count;
        }
    }

    private static int simulate(int N)
    {
        // new Percolation
        Percolation p = new Percolation(N);
        
        // random array
        int[] a = new int[N * N];
        for (int i = 0; i < N * N; i++)
            a[i] = i;
        StdRandom.shuffle(a);
            
        // go through array
        for (int n = 0; n < a.length; n++)
        {
            // get i, j
            int i = a[n] / N + 1;
            int j = a[n] % N + 1;

            // open
            p.open(i, j);
                
            // check if percolates
            if (p.percolates())
            {
                return n;
            }
        }
        
        return -1;
    }
        
    // sample mean of percolation threshold
    public double mean() {
        
        return StdStats.mean(stats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        
        return StdStats.stddev(stats);
    }
    
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        
        return mean() - coeff * stddev();
    }
   
    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        
        return mean() + coeff * stddev();
    }
   
    // test client, described below
    public static void main(String[] args) {
        
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats ps = new PercolationStats(N, T);
        
        System.out.print("mean                    = ");
        System.out.println(ps.mean());
        System.out.print("stddev                  = ");
        System.out.println(ps.stddev());
        System.out.print("95% confidence interval = ");
        System.out.print(ps.confidenceLo());
        System.out.print(", ");
        System.out.println(ps.confidenceHi());
    }
}