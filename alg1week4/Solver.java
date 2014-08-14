import java.util.ArrayList;
import java.util.Comparator;

public class Solver {

    private GameNode path = null;
    
    /**
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board b) {
        
        MinPQ<GameNode> pq = new MinPQ<GameNode>(20, new ManhattanComparator());
        MinPQ<GameNode> pqTwin = new MinPQ<GameNode>(20, new ManhattanComparator());
       
        // add first
        GameNode g = new GameNode(b, null);
        pq.insert(g);
        
        GameNode gTwin = new GameNode(b.twin(), null);
        pqTwin.insert(gTwin);

        // loop
        while (true)
        {
            GameNode gc = pq.delMin();
            GameNode gcTwin = pqTwin.delMin();
            
            if (gc == null || gcTwin == null || gcTwin.board().isGoal())
            {
                // path doesn't exist
                return;
            }

            if (gc.board().isGoal())
            {
                // path found
                path = gc;
                return;
            }
            
            // search neighbours
            for (Board bn : gc.board().neighbors())
            {
                GameNode gn = new GameNode(bn, gc);
                
                if (!gn.equals(gc.prev))
                {
                    pq.insert(gn);
                }
            }
        
            // search neighbours - Twin
            for (Board bnTwin : gcTwin.board().neighbors())
            {
                GameNode gnTwin = new GameNode(bnTwin, gcTwin);
                
                if (!gnTwin.equals(gcTwin.prev))
                {
                    pqTwin.insert(gnTwin);
                }
            }
        }
    }
    
    /**
     * 
     */
    public boolean isSolvable() {
        
        return path != null;
    }
    
    /**
     * 
     */
    public int moves() {
        
       if (path == null)
       {
           return -1;
       }
       else
       {
           return path.moves();
       }
    }
    
    /**
     * 
     */
    public Iterable<Board> solution() {
        
        if (path == null)
        {
            return null;
        }
        else
        {
            return path.getPath();
        }
    }
    
    private class ManhattanComparator implements Comparator<GameNode>
    {
        @Override
        public int compare(GameNode n1, GameNode n2)
        {
            return Integer.compare(n1.f, n2.f);
        }
    };

    private class GameNode {

        private int hash;
        private Board b;
        private GameNode prev = null;
        
        private int g;
        private int h;
        private int f;
        
        public GameNode(Board b, GameNode prev)
        {
            this.b = b;
            this.prev = prev;
            
            // g, h, f
            if (prev == null) g = 0;
            else g = prev.g + 1;
            
            h = b.manhattan();

            f = g + h;
        }
        
        public Board board()
        {
            return b;
        }
        
        public int moves()
        {
            return g;
        }

        public ArrayList<Board> getPath()
        {
            ArrayList<Board> a = new ArrayList<Board>(g);
            getPath(a);
            return a;
        }
        
        private void getPath(ArrayList<Board> a)
        {
            if (prev != null)
            {
                prev.getPath(a);
            }
            a.add(b);
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%d (g = %d, h = %d)\n", f, g, h));
            sb.append(b);
            return sb.toString();
        }
        
        @Override
        public int hashCode()
        {
            if (hash == 0)
            {
                hash = b.toString().hashCode();
            }
            
            return hash;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (o == this) return true;
            if (!(o instanceof GameNode)) return false;
            GameNode gm = (GameNode) o;

            return b.equals(gm.b);
        }
    }
    
    /**
     * 
     */
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
       
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
