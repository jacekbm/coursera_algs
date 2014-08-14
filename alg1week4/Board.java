import java.util.ArrayList;

public class Board {
    
    private int[][] blocks;
    private int N;
    private int blankRow;
    private int blankCol;
    
    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        
        N = blocks.length;

        // copy data and find blank
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                this.blocks[i][j] = blocks[i][j];
                
                if (blocks[i][j] == 0)
                {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    /**
     * board dimension N
     */
    public int dimension() {
    
        return N;
    }
    
    /**
     * number of blocks out of place
     */
    public int hamming() {

        int h = 0;
        int block = 1;
        
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (block++ != blocks[i][j])
                {
                    if (blocks[i][j] != 0)
                    {
                        h++;
                    }
                }
            }
        }

        return h;
    }

    private int manhattan(int block, int i, int j)
    {
        int row = (block - 1) / N;
        int col = (block - 1) % N;
        
        return Math.abs(row - i) + Math.abs(col - j);
    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {

        int m = 0;
        int block = 1;
        
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                if (blocks[i][j] != 0)
                {
                    m += manhattan(blocks[i][j], i, j);
                }
            }
        }
        
        return m;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {

        int block = 1;
        
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                if (block != blocks[i][j])
                {
                    return false;
                }
                
                if (++block == N * N)
                {
                    return true;
                }
            }
        }
        
        assert false; // it shouldn't be here
        return true;
    }

    private Board swap(int i1, int j1, int i2, int j2)
    {
        Board b = new Board(blocks);
        
        // swap blocks
        b.blocks[i1][j1] = blocks[i2][j2];
        b.blocks[i2][j2] = blocks[i1][j1];
        
        // swap blank
        if (b.blankRow == i1 && b.blankCol == j1)
        {
            b.blankRow = i2;
            b.blankCol = j2;
        }
        else if (b.blankRow == i2 && b.blankCol == j2)
        {
            b.blankRow = i1;
            b.blankCol = j1;
        }
        
        return b;
    }
    
    /**
     * a board obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin() {

        // boundary case
        if (N == 1)
        {
            return swap(0, 0, 0, 0);
        }
        
        if (blankRow == 0)
        {
            return swap(1, 0, 1, 1);
        }
        else
        {
            return swap(0, 0, 0, 1);
        }
    }

    /**
     * does this board equal o?
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Board)) return false;
        Board b = (Board) o;

        if (N != b.N)
        {
            return false;
        }
        
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (blocks[i][j] != b.blocks[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        
        ArrayList<Board> a = new ArrayList<Board>();

        if (blankRow > 0)
        {
            a.add(swap(blankRow, blankCol, blankRow - 1, blankCol));
        }
        
        if (blankRow < N - 1)
        {
            a.add(swap(blankRow, blankCol, blankRow + 1, blankCol));
        }

        if (blankCol > 0)
        {
            a.add(swap(blankRow, blankCol, blankRow, blankCol - 1));
        }
        
        if (blankCol < N - 1)
        {
            a.add(swap(blankRow, blankCol, blankRow, blankCol + 1));
        }
        
        return a;
    }

    
    /**
     * string representation of the board (in the output format specified below)
     */
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}