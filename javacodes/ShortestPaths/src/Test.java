
public class Test {
    int i = 0;
    EdgeWeightedDigraph G = null;
    while ((sCurrentLine = br.readLine()) != null) {
        String[] parts = sCurrentLine.split(" ");
        if (i == 0) {
            numVertices = Integer.valueOf(parts[0]);
            numEdges = Integer.valueOf(parts[1]);
            G = new EdgeWeightedDigraph(numVertices);
            i++;
        } else {
            int v = Integer.valueOf(parts[0]);
            int w = Integer.valueOf(parts[1]);
            int weight = Integer.valueOf(parts[2]);
            DirectedEdge e = new DirectedEdge(v, w, weight);
            G.addEdge(e);
        }
    }
    br.close();
    return G;
}


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
