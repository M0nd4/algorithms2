import java.util.Arrays;

/**
 * Brute.java examines 4 points at a time and checks whether they all lie on the
 * same line segment, printing out any such line segments to standard output and
 * drawing them using standard drawing. To check whether the 4 points p, q, r,
 * and s are collinear, check whether the slopes between p and q, between p and
 * r, and between p and s are all equal.
 */
public class Brute {

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

    /*
     * Check for linearity, input array of points
     */
    private static boolean inLine(Point[] points) {
        assert points.length > 1;
        if (points.length == 2)
            return true;
        double slope = points[0].slopeTo(points[1]);
        for (int i = 2; i < points.length; i++) {
            if (points[0].slopeTo(points[i]) == slope)
                continue;
            return false;
        }
        return true;
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
     * Draw a line segment between points
     */

    /**
     * Implement a quadruple for loop to check all combinations of 4 points for
     * linearity, print and plot a combination if it is in line.
     */
    public static void main(String[] args) {
        // set up scaling
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        Point[] points = readIn(args[0]);
        // draw last three points
        points[points.length - 3].draw();
        points[points.length - 2].draw();
        points[points.length - 1].draw();
        for (int i = 0; i < points.length - 3; i++) {
            points[i].draw(); // draw every point
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point[] current = new Point[] { points[i], points[j],
                                points[k], points[l] };
                        if (inLine(current)) {
                            Arrays.sort(current);
                            printCoords(current);
                            current[0].drawTo(current[3]);
                        }
                    }
                }
            }
        }
    }
}
