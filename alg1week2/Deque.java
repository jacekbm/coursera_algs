import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int N;               // number of elements on queue
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    
    // double linked list class
    private class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;
        
        public Node(Item i, Node<Item> p, Node<Item> n)
        {
            item = i;
            prev = p;
            next = n;                
        }                          
    }
    
    /**
     * construct an empty deque
     */
    public Deque() {
        first = null;
        last  = null;
        N = 0;
    }
   
    /**
     * is the deque empty?
     */
    public boolean isEmpty() {

        return first == null;
    }
    
    /**
     * return the number of items on the deque
     */
    public int size() {
        
        return N;     
    }
    
    /**
     * insert the item at the front
     */
    public void addFirst(Item item) {

        if (item == null)
        {
            throw new NullPointerException();
        }
        
        Node<Item> node = new Node<Item>(item, null, first);

        if (first != null)
        {
            first.prev = node;
        }
        else
        {
            last = node;
        }
            
        first = node;
        
        N++;
    }
    
    /**
     * insert the item at the end
     */
    public void addLast(Item item) {

        if (item == null)
        {
            throw new NullPointerException();
        }
        
        Node<Item> node = new Node<Item>(item, last, null);

        if (last != null)
        {
            last.next = node;
        }
        else
        {
            first = node;
        }
            
        last = node;
        
        N++;
    }
    
    /**
     * delete and return the item at the front
     */
    public Item removeFirst() {
        
        if (isEmpty())
        {        
            throw new NoSuchElementException();
        }
        
        Node<Item> node = first;
        
        first = node.next;
        
        if (first != null)
        {
            first.prev = null;
        }
        else
        {
            last = null;
        }
        
        N--;
        return node.item;
    }
    
    /**
     * delete and return the item at the end
     */
    public Item removeLast() {

        if (isEmpty())
        {        
            throw new NoSuchElementException();
        }
        
        Node<Item> node = last;
        
        last = node.prev;
        
        if (last != null)
        {
            last.next = null;
        }
        else
        {
            first = null;
        }
        
        N--;
        return node.item;
    }
    
    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    }
    
    private class DequeIterator<Item> implements Iterator<Item> {
        
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
       
            current = first;
        }
        
        public boolean hasNext() {

            return current != null;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
     /**
     * unit testing
     */
    public static void main(String[] args)
    {
    }
}