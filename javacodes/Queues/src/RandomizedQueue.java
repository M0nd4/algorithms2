import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class RandomizedQueue creates a resizing array of generic items from
 * which elements are returned uniformly at random. It has methods to tell if
 * empty, current size, enqueue, dequeue, sample (returns but doesn't delete
 * random item), and a method to create an iterator that iterates over the items
 * in random order. All the randomized queue operations run in constant
 * amortized time.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a; // array of items
    private int N; // number of elements on stack

    /**
     * Create an empty RandomizedQueue
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
    }

    /**
     * Is the queue empty?
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items on the queue
     */
    public int size() {
        return N;
    }

    /**
     * resize the underlying array holding the elements
     */
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    /**
     * Add a new item to the queue
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (N == a.length)
            resize(2 * a.length); // double size of array if necessary
        a[N++] = item; // add item
    }

    /**
     * Return a random index that isn't null.
     */
    private int index() {
        if (a.length != 1) {
            int size = a.length;
            int ind = StdRandom.uniform(size);
            while (a[ind] == null) {
                ind = StdRandom.uniform(size);
            }
            return ind;
        } else {
            return 0;
        }
    }

    /**
     * Delete and return a random item from queue Also, swaps the chosen item
     * with last in list to keep track of null entries.
     */
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue's Empty");
        exchange(a, StdRandom.uniform(N), --N);
        Item item = a[N];
        a[N] = null;
        // shrink size of array if necessary
        if (N > 0 && N == a.length / 4)
            resize(a.length / 2);
        return item;
    }

    // Swap entries i & j in the array a.
    private void exchange(Item[] a, int i, int j) {
        if (i == j)
            return;
        Item swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /**
     * Return, but not delete, a random item from queue.
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack Underflow");
        }
        return a[StdRandom.uniform(N)];
    }

    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int count = N;
        private int[] indices;

        public RandomizedQueueIterator() {
            indices = new int[count];
            for (int i = 0; i < count; i++)
                indices[i] = i;
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return count > 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return a[indices[--count]];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is Not Supported");
        }
    }

    /**
     * Test client
     */
    public static void main(String[] args) {
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                s.enqueue(item);
            else if (!s.isEmpty())
                StdOut.print(s.sample() + " ");
        }
        StdOut.println("(" + s.size() + " left on stack)");
    }
}
