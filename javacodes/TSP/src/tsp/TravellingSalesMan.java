package tsp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Returns the order to visit predefined products of certain store in minimal
 * distance which returns the sequence number to visit these products
 * 
 * @author Ayman Mahgoub
 * 
 */
public class TravellingSalesMan {
    private double[][]         distances;
    private short              minDistances[][], paths[][];
    private ArrayList<Integer> products_visit_order;
    private int                products_number_power, products_number;

    public HashMap<Product, Integer> getOrderForVisitingProducts(
            ArrayList<Product> products) {
        distances = calculateDistanceBetweenProducts(products);

        products_number = products.size();
        /*
         * you represent a subset with a bitmask, which is just an integer. If
         * location i is in the subset, bit number i is set in the integer. For
         * instance, the subset where location 3 and 4 are visited, is
         * represented by the number 24, because bit number 3 (2^3) + bit number
         * 4 (2^4) are set.
         * 
         * suppose if i take 4 vertices 1,2,3,4 then if i visit 1->2->3 then
         * (2^2)+(2^3) =12 d[1][12] will be the distance from 1->2->3
         * 
         * for 1->3->4 (2^3)+(2^4) =24 d[1][24] will be the distance from
         * 1->3->4
         * 
         * SO the matrix length is 2 ^ (product_number)
         */
        products_number_power = (int) Math.pow(2, products_number);
        minDistances = new short[products_number][products_number_power];
        paths = new short[products_number][products_number_power];

        for (int i = 0; i < products_number; i++) {
            for (int j = 0; j < products_number_power; j++) {
                minDistances[i][j] = -1;
                paths[i][j] = -1;
            }
        }

        // initialize based on distance matrix
        for (int i = 0; i < products_number; i++) {
            minDistances[i][0] = 0;
        }

        // get value of item[0][products_number_power - 2] which indicates that
        // we took all the other nodes
        getMinTraversingDistance(0, products_number_power - 2, 1);
        products_visit_order = new ArrayList<Integer>();

        getMinTraversingDistancePath(0, products_number_power - 2);

        HashMap<Product, Integer> productsOrder = new HashMap<Product, Integer>();

        int i;
        for (i = 1; i < products_number - checkout_points_numbers; i++) {
            productsOrder.put(products.get(products_visit_order.get(i - 1)), i);
        }

        minDistances = null;
        paths = null;
        return productsOrder;
    }

    private double getMinTraversingDistance(int start, int set, int level) {
        double temp = 0, result = -1;
        int masked, mask;
        if (minDistances[start][set] != -1) {
            return minDistances[start][set];
        } else {
            for (int x = 0; x < products_number; x++) {
                // minus Math.pow(2, x), means you removed this node 'x' from
                // my set to try other possibility
                mask = products_number_power - 1 - (int) Math.pow(2, x);
                masked = set & mask;
                if (masked != set) {
                    temp = distances[start][x]
                            + getMinTraversingDistance(x, masked, level + 1);
                    if (result == -1 || result > temp) {
                        result = temp;
                        paths[start][set] = (short) x;
                    }
                }
            }
            minDistances[start][set] = (short) result;
            return result;
        }
    }

    /**
     * Gets Visiting sequence by moving backwards from source node '0' to
     * destination node
     * 
     * @param start
     * @param set
     */
    private void getMinTraversingDistancePath(int start, int set) {
        if (paths[start][set] == -1) {
            return;
        }
        int x = paths[start][set];
        int mask = products_number_power - 1 - (int) Math.pow(2, x);
        int masked = set & mask;
        products_visit_order.add(x);
        getMinTraversingDistancePath(x, masked);
    }

    /**
     * Calculates all the distances (sum of horizontal and vertical distances)
     * between all nodes
     * 
     * @param products
     * @return
     */
    private double[][] calculateDistanceBetweenProducts(List<Product> products) {
        double[][] distances = new double[products.size()][products.size()];
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < products.size(); j++) {
                if (i != j) {
                    double distance = Math.abs(products.get(i)
                            .getAverageLocation().x
                            - products.get(j).getAverageLocation().x)
                            + Math.abs(products.get(i).getAverageLocation().y
                                    - products.get(j).getAverageLocation().y);
                    distances[i][j] = distance;
                }
            }
        }
        return distances;
    }
}