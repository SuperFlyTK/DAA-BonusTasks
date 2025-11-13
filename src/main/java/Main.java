import graph.Graph;
import graph.Edge;
import mst.KruskalMST;
import mst.MSTReplacer;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        printHeader();

        // Create results directory
        try {
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("results"));
        } catch (java.io.IOException e) {
            System.out.println("Warning: Could not create results directory");
        }

        System.out.println("Step 1: Creating and displaying the original graph...");
        Graph graph = Graph.createSampleGraph();
        printOriginalGraph(graph);

        waitForUser();

        System.out.println("Step 2: Building Minimum Spanning Tree...");
        KruskalMST kruskal = new KruskalMST(graph);
        kruskal.displayMST();

        if (!kruskal.isValid(graph.getVertices())) {
            System.out.println("Error: MST is not valid!");
            return;
        }

        waitForUser();

        List<Edge> mstEdges = kruskal.getMSTEdges();
        Edge edgeToRemove = selectEdgeToRemove(mstEdges);

        System.out.println("Step 3: Removing edge and finding replacement...");
        MSTReplacer replacer = new MSTReplacer(graph, mstEdges);
        Edge replacementEdge = replacer.removeAndReplace(edgeToRemove);

        waitForUser();

        System.out.println("Step 4: Final Result...");
        replacer.displayCurrentMST();

        printSummary(edgeToRemove, replacementEdge, kruskal.getTotalWeight(),
                Graph.calculateTotalWeight(replacer.getCurrentMSTEdges()));

        System.out.println("Step 5: Generating performance report...");
        CSVGenerator.generatePerformanceCSV();

        System.out.println("Program completed successfully!");
    }

    private static void printOriginalGraph(Graph graph) {
        System.out.println("=== ORIGINAL GRAPH ===");
        System.out.println("Vertices: " + graph.getVertices());
        System.out.println("Edges: " + graph.getEdges().size());
        System.out.println("\nAll edges in the graph:");
        for (int i = 0; i < graph.getEdges().size(); i++) {
            Edge edge = graph.getEdges().get(i);
            System.out.println((i + 1) + ". " + edge.getSource() + " -- " +
                    edge.getDestination() + " (weight: " + edge.getWeight() + ")");
        }
        System.out.println("Total graph weight: " + Graph.calculateTotalWeight(graph.getEdges()));
        System.out.println("======================");
    }

    private static Edge selectEdgeToRemove(List<Edge> mstEdges) {
        if (mstEdges.isEmpty()) {
            throw new IllegalStateException("MST has no edges!");
        }

        int index = mstEdges.size() / 2;
        Edge edge = mstEdges.get(index);

        System.out.println("Selected edge for removal: " +
                edge.getSource() + " -- " + edge.getDestination() +
                " (weight: " + edge.getWeight() + ")");

        return edge;
    }

    private static void printHeader() {
        System.out.println("==========================================");
        System.out.println("    MST Edge Replacement Demonstration");
        System.out.println("==========================================");
        System.out.println();
    }

    private static void printSummary(Edge removed, Edge replacement,
                                     int originalWeight, int newWeight) {
        System.out.println("==========================================");
        System.out.println("               SUMMARY");
        System.out.println("==========================================");

        System.out.println("Original MST Weight: " + originalWeight);
        System.out.println("Removed Edge: " + removed.getSource() + " -- " +
                removed.getDestination() + " (weight: " + removed.getWeight() + ")");

        if (replacement != null) {
            System.out.println("Replacement Edge: " + replacement.getSource() + " -- " +
                    replacement.getDestination() + " (weight: " + replacement.getWeight() + ")");
            System.out.println("New MST Weight: " + newWeight);
            System.out.println("Weight Change: " + (newWeight - originalWeight));
        } else {
            System.out.println("Replacement Edge: None found");
            System.out.println("Graph remains disconnected");
        }

        System.out.println("==========================================");
    }

    private static void waitForUser() {
        System.out.println();
        System.out.print("Press Enter to continue...");
        try {
            System.in.read();
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
        }
        System.out.println();
    }
}