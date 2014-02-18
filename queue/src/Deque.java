import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    
    private int N;               // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue
    
    private class Node {
        private Item item;
        private Node next;
        private Node precede;
    }
    
    public Deque() {
        // construct an empty deque
        N = 0;
        first = null;
        last = null;
        
    }
    public boolean isEmpty() {
        // is the deque empty?
        return N == 0;
    }
    public int size() {
        // return the number of items on the deque
        return N;
    }
    public void addFirst(Item item) {
        // insert the item at the front
        if (item == null) throw new NullPointerException("attempts to add a null item");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.precede = null;
        if (isEmpty()) {
            first.next = null;
            last = first;
        }
        else {
            first.next = oldfirst;
            oldfirst.precede = first;
        }
        N++;
    }
    public void addLast(Item item) {
        // insert the item at the end
        if (item == null) throw new NullPointerException("attempts to add a null item");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            last.precede = null;
            first = last;
        }
        else {
            last.precede = oldlast;
            oldlast.next = last;
        }
        N++;
    }
    public Item removeFirst() {
        // delete and return the item at the front
        if (isEmpty()) throw new NoSuchElementException("attempts to remove an item from an empty deque");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        N--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.precede = null;
        }
        return item;                   // return the saved item
    }
    public Item removeLast()
    {
        // delete and return the item at the end
        if (isEmpty()) throw new NoSuchElementException("attempts to remove an item from an empty deque");
        Item item = last.item;        // save item to return
        last = last.precede;            // delete first node
        N--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;                   // return the saved item
    }
    @Override
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

    public static void main(String[] args) {
        // unit testing

    }

}
