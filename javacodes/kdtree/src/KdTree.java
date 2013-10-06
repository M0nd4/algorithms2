
public class KdTree {
    private Node root;
    private int size;

    public KdTree() { // construct an empty set of points
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    } // is the set empty?

    public int size() {
        return size;
    } // number of points in the set

}