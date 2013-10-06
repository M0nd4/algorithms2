package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TSP {
    static int numPoints;

    private static double eucDist(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    /**
     * Read in data: first line is number of vertices, Each subsequent line
     * describes a point (x y coordinates separated by a space)
     * 
     * @throws IOException
     */
    public static double[][] readIn(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader br = new BufferedReader(fileReader);
        String line = null;
        double[][] points = new double[1][1];
        int l = 0;
        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            // Process the first line
            if (l == 0) {
                numPoints = Integer.parseInt(parts[0]);
                points = new double[numPoints][2];
                l++;
            } else {

                points[i][0] = Double.parseDouble(parts[0]);
                points[i][1] = Double.parseDouble(parts[1]);
                i++;
            }
        }
        br.close();
        return points;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        double[][] points = readIn(args[0]);
        System.out.println("The number of points is " + numPoints);
        // for (int i = 0; i < numPoints; i++) {
        // System.out.println(points[i][0] + " " + points[i][1]);
        // }
        System.out.println(eucDist(points[0][0], points[0][1], points[1][0],
                points[1][1]));
    }
}
