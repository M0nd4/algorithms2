import java.util.Arrays;

/**
 * Faster Algorithm to find collinear points
 * 
 * Think of p as the origin.
 * 
 * For each other point q, determine the slope it makes with p.
 * 
 * Sort the points according to the slopes they makes with p.
 * 
 * Check if any 3 (or more) adjacent points in the sorted order have equal
 * slopes with respect to p. If so, these points, together with p, are
 * collinear.
 */
public class Fast {

    /**
     * Read input file
     * 
     * First line is single int specifying number of points. Then, each line has
     * two ints, the x and y coords. Output and array of points.
     */
    private static Point[] readIn(String filename) {
        In input = new In(filename);
        int n = input.readInt(); // the length of array
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(input.readInt(), input.readInt());
        }
        return points;
    }

    /**
     * Print line segment coords
     */
    private static void printCoords(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            System.out.print(points[i] + " -> ");
        }
        System.out.println(points[points.length - 1]);
    }

    /**
     * Two for loops, in the first calculate the slope between target point and
     * the rest of the points, then sort the resulting slopes, loop through and
     * look for adjacent points with equal slopes
     */
    public static void main(String[] args) {
        // set up scaling
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        Point[] points = readIn(args[0]);
        Arrays.sort(points);
        // draw last three points
        points[points.length - 3].draw();
        points[points.length - 2].draw();
        points[points.length - 1].draw();

        for (int i = 0; i < points.length - 3; i++) {
            points[i].draw(); // draw points
            Point[] slopes = new Point[points.length - i];
            for (int j = i; j < points.length; j++)
                slopes[j - i] = points[j];
            Arrays.sort(slopes, points[i].SLOPE_ORDER);
            // search through slopes for 0
            double[] slopes = new double[points.length - i];
            for (int j = i + 1; j < points.length; j++) {
                slopes[j] = points[i].slopeTo(points[j]);
            }
        }
    }
}
