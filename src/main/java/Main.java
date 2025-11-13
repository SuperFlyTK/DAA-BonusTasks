import graph.Edge;
import graph.Graph;
import mst.KruskalMST;
import mst.MSTReplacer;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("MST Edge Replacement Demo");

        Graph graph = Graph.createSampleGraph();

        System.out.println("Building MST...");
        KruskalMST kruskal = new KruskalMST(graph);
        kruskal.displayMST();

        if (!kruskal.isValid(graph.getVertices())) {
            System.out.println("Invalid MST");
            return;
        }

        List<Edge> mstEdges = kruskal.getMSTEdges();
        Edge edgeToRemove = selectEdgeToRemove(mstEdges);

        MSTReplacer replacer = new MSTReplacer(graph, mstEdges);
        Edge replacementEdge = replacer.removeAndReplace(edgeToRemove);

        System.out.println("Final Result:");
        replacer.displayCurrentMST();

        printSummary(edgeToRemove, replacementEdge, kruskal.getTotalWeight(),
                Graph.calculateTotalWeight(replacer.getCurrentMSTEdges()));
    }

    private static Edge selectEdgeToRemove(List<Edge> mstEdges) {
        int index = mstEdges.size() / 2;
        Edge edge = mstEdges.get(index);
        System.out.println("Removing: " + edge.getSource() + " -- " +
                edge.getDestination() + " (weight: " + edge.getWeight() + ")");
        return edge;
    }

    private static void printSummary(Edge removed, Edge replacement,
                                     int originalWeight, int newWeight) {
        System.out.println("=== SUMMARY ===");
        System.out.println("Original Weight: " + originalWeight);
        System.out.println("Removed: " + removed.getSource() + " -- " +
                removed.getDestination() + " (weight: " + removed.getWeight() + ")");

        if (replacement != null) {
            System.out.println("Replacement: " + replacement.getSource() + " -- " +
                    replacement.getDestination() + " (weight: " + replacement.getWeight() + ")");
            System.out.println("New Weight: " + newWeight);
            System.out.println("Weight Change: " + (newWeight - originalWeight));
        } else {
            System.out.println("No replacement found");
        }
    }
}