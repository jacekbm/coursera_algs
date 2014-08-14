public class Subset {
    
    public static void main(String[] args) {
        
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        
        // read data
        while (!StdIn.isEmpty())
        {
            // read next string
            String s = StdIn.readString();

            // add a string
            rq.enqueue(s);
        }

        for (int i = 0; i < k; i++)
        {
            StdOut.println(rq.dequeue());        
        }       
    }
}