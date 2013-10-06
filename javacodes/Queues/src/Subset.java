/**
 * Takes a command-line integer k, reads in a sequence of N strings from
 * standard input and prints out exactly k of them, uniformly at random. Each
 * item from the sequence can be printed out at most once (no repeats).
 */
public class Subset {

    /**
     * Client should use only constant space plus one object of type Deque or
     * RandomizedQueue. Use generics to avoid casting and compiler warnings. It
     * should use time and space proportional to at most N in the worst case,
     * where N is the number of strings in the input.
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            s.enqueue(item);
        }
        for (int i = 0; i < k; i++) {
            String item = s.dequeue();
            StdOut.println(item);
        }
    }
}
