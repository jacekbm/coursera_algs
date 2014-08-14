import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;         // array of items
    private int N;            // number of elements on stack
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
    }
    
    /**
     * is the queue empty?
     */
    public boolean isEmpty() {

        return N == 0;
    }
    
    /**
     * return the number of items on the queue
     */
    public int size() {

        return N;
    }
    
    // resize the underlying array holding the elements
    private void resize(int capacity) {

        assert capacity >= N;
        
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    
    
    /**
     * add the item
     */
    public void enqueue(Item item) {

        if (item == null)
        {
            throw new NullPointerException();
        }
        
        if (N == a.length)
        {
            resize(2 * a.length);    // double size of array if necessary
        }
        
        a[N++] = item;                            // add item
    }
    
    /**
     * delete and return a random item
     */
    public Item dequeue() {
        
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        
        int i = StdRandom.uniform(N);
        
        // swap with the last one
        Item item = a[i];
        a[i] = a[N - 1];
        a[--N] = null;
        
        // shrink size of array if necessary
        if (N > 0 && N == a.length / 4)
        {
            resize(a.length / 2);
        }
        
        // return
        return item;       
    }
    
    /**
     * return (but do not delete) a random item
     */
    public Item sample() {
        
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        
        return a[StdRandom.uniform(N)];        
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        
        return new RQIterator();
    }
    
    private class RQIterator implements Iterator<Item> {

        private int[] indexes;
        private int i = 0;

        public RQIterator() {
            
            indexes = new int[N];
            for (int j = 0; j < N; j++)
            {
                indexes[i] = j;
            }           
            StdRandom.shuffle(indexes);
        }

        public boolean hasNext() {
            
            return i < N;
        }

        public void remove() {
            
            throw new UnsupportedOperationException();
        }

        public Item next() {
            
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            return a[indexes[i++]];
        }
    }    
    
    /**
     * unit testing
     */
    public static void main(String[] args) {
        
    }
}