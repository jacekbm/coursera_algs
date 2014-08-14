public class Percolation {
    
    private Boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int mN;
    private int indexUp;
    private int indexDown;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        
        if (N <= 0)
        {
            throw new java.lang.IllegalArgumentException();
        }
        
        grid = new Boolean[N][N];
        
        for (int x = 0; x < N; x++)
            for (int y = 0; y < N; y++)
                grid[x][y] = false;

        mN = N;
        indexUp = N * N;
        indexDown = indexUp + 1;
       
        uf = new WeightedQuickUnionUF(N * N + 2);
    }

    // i : 1 .. N    x : 0 .. N-1
    // j : 1 .. N    y : 0 .. N-1
    // 
    // grid: x * N + y - (x:row, y:col)
    //       N*N   - up
    //       N*N+1 - down 
    
    private int getIndex(int x, int y) {
        
        return x * mN + y;        
    }
    
    // open site (row i, column j) if it is not already    
    public void open(int i, int j) {
        
        if (i < 1 || i > mN || j < 1 || j > mN)
            throw new IndexOutOfBoundsException();
            
        int x = i - 1;
        int y = j - 1;
        
        grid[x][y] = true;
        
        // up
        if (x > 0)
        {
            if (grid[x - 1][y])
            {
                uf.union(getIndex(x, y), getIndex(x - 1, y));
            }
        }
        else
        {
            // first row
            uf.union(getIndex(x, y), indexUp);
        }
            
        // down
        if (x < mN - 1)
        {
            if (grid[x + 1][y])
            {
                uf.union(getIndex(x, y), getIndex(x + 1, y));
            }
        }
        else
        {
            // last row
            uf.union(getIndex(x, y), indexDown);
        }

        // left
        if (y > 0)
        {
            if (grid[x][y - 1])
            {
                uf.union(getIndex(x, y), getIndex(x, y - 1));
            }
        }
            
        // right
        if (y < mN - 1)
        {
            if (grid[x][y + 1])
            {
                uf.union(getIndex(x, y), getIndex(x, y + 1));
            }
        }
    }
    
    // is site (row i, column j) open?    
    public boolean isOpen(int i, int j) {
        
        if (i < 1 || i > mN || j < 1 || j > mN)
            throw new IndexOutOfBoundsException();
            
        return grid[i - 1][j - 1];
    }
    
    // is site (row i, column j) full?    
    public boolean isFull(int i, int j) {
        
        if (i < 1 || i > mN || j < 1 || j > mN)
            throw new IndexOutOfBoundsException();
            
        return uf.connected(indexUp, getIndex(i - 1, j - 1));
    }
    
    // does the system percolate?
    public boolean percolates() {
        
        return uf.connected(indexUp, indexDown);
    }
}