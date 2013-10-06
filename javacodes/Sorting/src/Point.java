/**
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 */

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;
    private final int x; // x coordinate
    private final int y; // y coordinate

    /**
     * compare points by the slopes they make with the invoking point (x0, y0).
     * Formally, the point (x1, y1) is less than the point (x2, y2) if and only
     * if the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2
     * − x0). Treat horizontal, vertical, and degenerate line segments as in the
     * slopeTo() method.
     */
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double diff = slopeTo(a) - slopeTo(b);
            if (diff < 0)
                return -1;
            if (diff > 0)
                return 1;
            return 0;
        }
    }

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new SlopeOrder();
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    // (y1 − y0) / (x1 − x0). Treat the slope of a horizontal line segment as
    // positive zero [added 7/29]; treat the slope of a vertical line segment as
    // positive infinity; treat the slope of a degenerate line segment (between
    // a point and itself) as negative infinity.
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x == that.x) {
            if (this.y == that.y)
                return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y)
            return 0.0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y == that.y)
            return this.x - that.x;
        return this.y - that.y;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point a, b, c, d;
        a = new Point(1, 1);
        b = new Point(5, 4);
        c = new Point(1, 4);
        d = new Point(1, 1);
        StdOut.println(a.slopeTo(b));
        StdOut.println(b.compareTo(a));
        StdOut.println(a.slopeTo(d));
        StdOut.println(c.slopeTo(d));
        a.drawTo(b);
        StdOut.println(a.toString());
        System.out.print(a);
        StdOut.println(a.SLOPE_ORDER);
    }
}
