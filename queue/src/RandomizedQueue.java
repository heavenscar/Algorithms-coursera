import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a; // queue elements
    private int N; // number of elements on queue

    public RandomizedQueue() {
        // construct an empty randomized queue
        a = (Item[]) new Object[2];
        N = 0;
        // StdRandom.setSeed(1);
    }

    public boolean isEmpty() {
        // is the queue empty?
        return N == 0;
    }

    public int size() {
        // return the number of items on the queue
        return N;
    }

    // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null)
            throw new NullPointerException("attempts to add a null item");
        if (N == a.length)
            resize(2 * a.length); // double size of array if necessary
        a[N++] = item; // add item
    }

    public Item dequeue() {
        // delete and return a random item
        if (isEmpty())
            throw new NoSuchElementException("attempts to dequeue an item from an empty randomized queue");
        int num = StdRandom.uniform(N);
        Item item = a[num];
        a[num] = a[N - 1];
        a[N - 1] = null;
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length / 4)
            resize(a.length / 2);
        return item;
    }

    public Item sample() {
        // return (but do not delete) a random item
        if (isEmpty())
            throw new NoSuchElementException("attempts to sample an item from an empty randomized queue");
        return a[StdRandom.uniform(N)];
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
//        if (!isEmpty()) StdRandom.shuffle(a, 0, N-1);
        return new RandomArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] q;
        
        public RandomArrayIterator() {
             q = new int[N];
             for (int j = 0; j < N; j++) {
                 q[j] = j;
             }
             StdRandom.shuffle(q);
        }

        public boolean hasNext() {
            return i < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = a[q[i]];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        // unit testing   

    }

}
