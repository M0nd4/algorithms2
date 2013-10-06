import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The <tt>Deque</tt> class represents a linked list of generic items. This data
 * type supports pushing/poping at the front and back in constant time, and has
 * methods to return list length, to test if the list is empty, and to create an
 * iterator for iteration front to back (LIFO).
 * <p>
 * All stack operations except iteration are constant time.
 * <p>
 */

public class Deque<Item> implements Iterable<Item> {
    private int N; // size of the stack
    private Node first; // top of stack
    private Node last; // back of stack

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    /**
     * Create an empty deque.
     */
    public Deque() {
        first = null;
        last = null;
        N = 0;
        assert check();
    }

    /**
     * Is the deque empty?
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * 
     * Number of items in the deque
     */
    public int size() {
        return N;
    }

    /**
     * insert item at front of deque. If first insertion, also creates a pointer
     * from last.
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        if (N == 0) {
            last = first;
        } else {
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        N++;
        assert check();
    }

    /**
     * insert item at back of deque. If first insertion, also creates a pointer
     * from first.
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        if (N == 0) {
            first = last;
        } else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        N++;
        assert check();
    }

    /**
     * Pop the item at the front of the deque. (i.e. Deletes and returns the
     * item)
     * 
     * @throws java.util.NoSuchElementException
     *             if stack is empty.
     */
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque underflow");
        Node n = first;
        Item item = first.item; // save item to return
        if (N != 1) {
            first = first.next;
            first.previous = null;
        } else {
            first = null;
            last = null;
        }
        N--;
        n = null;
        assert check();
        return item; // return the saved item
    }

    /**
     * Pop the item at the back of the deque. (i.e. Deletes and returns the
     * item)
     * 
     * @throws java.util.NoSuchElementException
     *             if stack is empty.
     */
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque underflow");
        Item item = last.item; // save item to return
        Node n = last;
        if (N != 1) {
            last = last.previous; // delete last node
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        N--;
        n = null;
        assert check();
        return item; // return the saved item
    }

    /**
     * Return an iterator to the deque that iterates through the items from
     * first to last.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null)
                return false;
        } else if (N == 1) {
            if (first == null)
                return false;
            if (first.next != null)
                return false;
        } else {
            if (first.next == null)
                return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodes = 0;
        for (Node x = first; x != null; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != N)
            return false;

        return true;
    }

    /**
     * A test client.
     */
    public static void main(String[] args) {
        Deque<String> p = new Deque<String>();
        p.addFirst("Hi");
        p.addFirst("Hello");
        p.addLast("Noah");
        StdOut.println(p.removeFirst());
        StdOut.println(p.removeLast() + p.removeLast());
    }
}
