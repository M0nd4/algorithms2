/**
 * Brute force algorithm
 */

public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        set.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D i : set) {
            StdDraw.point(i.x(), i.y());
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> points = new SET<Point2D>();
        for (Point2D point : set) {
            if (rect.contains(point))
                points.add(point);
        }
        return points;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;
        double closest = Double.POSITIVE_INFINITY;
        Point2D neighbor = null;
        for (Point2D i : set) {
            if (i.distanceTo(p) <= closest) {
                closest = i.distanceTo(p);
                neighbor = i;
            }
        }
        return neighbor;
    }

    public static void main(String[] args) {
        PointSET test = new PointSET();
        Point2D a = new Point2D(1, 1);
        Point2D b = new Point2D(2, 2);
        test.insert(a);
        test.insert(b);
        RectHV rect = new RectHV(0, 0, 3, 3);
        System.out.println(rect.toString());
        for (Point2D i : test.range(rect)) {
            System.out.println(i.toString());
        }
        System.out.println(a.x());
        System.out.println(test.nearest(new Point2D(3, 3)).toString());
    }
}
