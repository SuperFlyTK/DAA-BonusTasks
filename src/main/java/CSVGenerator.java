import graph.Graph;
import graph.Edge;
import mst.KruskalMST;
import mst.MSTReplacer;
import util.ComponentFinder;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CSVGenerator {

    public static void main(String[] args) {
        generatePerformanceCSV();
    }

    public static void generatePerformanceCSV() {
        String csvFile = "results/performance_data.csv";

        try {
            Files.createDirectories(Paths.get("results"));

            try (FileWriter writer = new FileWriter(csvFile)) {
                writer.write("Test Case,Vertices,Edges,Original MST Weight,Removed Edge,Replacement Edge,New MST Weight,Weight Change,Components After Removal,Execution Time (ms)\n");

                List<TestResult> results = new ArrayList<>();
                results.add(testBasicGraph());
                results.add(testSampleGraph());
                results.add(testLargeGraph());
                results.add(testBridgeRemoval());
                results.add(testComplexGraph());

                for (TestResult result : results) {
                    writer.write(result.toCSVRow() + "\n");
                }

                System.out.println("CSV report generated: " + csvFile);
                System.out.println("Total test cases: " + results.size());

            } catch (IOException e) {
                System.out.println("Error writing CSV file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Error creating results directory: " + e.getMessage());
        }
    }

    private static TestResult testBasicGraph() {
        long startTime = System.nanoTime();

        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 3, 5);

        KruskalMST mst = new KruskalMST(graph);

        Edge toRemove = findEdgeByVertices(mst.getMSTEdges(), 1, 2);
        if (toRemove == null) toRemove = mst.getMSTEdges().get(0);

        List<Edge> mstCopy = new ArrayList<>(mst.getMSTEdges());
        mstCopy.remove(toRemove);
        List<Set<Integer>> components = ComponentFinder.findComponents(graph.getVertices(), mstCopy);

        MSTReplacer replacer = new MSTReplacer(graph, mst.getMSTEdges());
        Edge replacement = replacer.removeAndReplace(toRemove);

        // Добавляем работу для измерения времени
        for (int i = 0; i < 1000; i++) {
            Graph.calculateTotalWeight(replacer.getCurrentMSTEdges());
        }

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000;

        return new TestResult(
                "Basic Graph",
                graph.getVertices(),
                graph.getEdges().size(),
                mst.getTotalWeight(),
                toRemove,
                replacement,
                Graph.calculateTotalWeight(replacer.getCurrentMSTEdges()),
                components.size(),
                Math.max(executionTime, 1) // Минимум 1 мс
        );
    }

    private static TestResult testSampleGraph() {
        long startTime = System.nanoTime();

        Graph graph = Graph.createSampleGraph();
        KruskalMST mst = new KruskalMST(graph);

        Edge toRemove = findEdgeByVertices(mst.getMSTEdges(), 1, 2);
        if (toRemove == null) toRemove = mst.getMSTEdges().get(0);

        List<Edge> mstCopy = new ArrayList<>(mst.getMSTEdges());
        mstCopy.remove(toRemove);
        List<Set<Integer>> components = ComponentFinder.findComponents(graph.getVertices(), mstCopy);

        MSTReplacer replacer = new MSTReplacer(graph, mst.getMSTEdges());
        Edge replacement = replacer.removeAndReplace(toRemove);

        // Добавляем работу для измерения времени
        for (int i = 0; i < 1000; i++) {
            Graph.calculateTotalWeight(replacer.getCurrentMSTEdges());
        }

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000;

        return new TestResult(
                "Sample Graph",
                graph.getVertices(),
                graph.getEdges().size(),
                mst.getTotalWeight(),
                toRemove,
                replacement,
                Graph.calculateTotalWeight(replacer.getCurrentMSTEdges()),
                components.size(),
                Math.max(executionTime, 1)
        );
    }

    private static TestResult testLargeGraph() {
        long startTime = System.nanoTime();

        Graph graph = new Graph(8);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 7);
        graph.addEdge(2, 4, 3);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 5);
        graph.addEdge(4, 5, 4);
        graph.addEdge(4, 6, 6);
        graph.addEdge(5, 7, 3);
        graph.addEdge(6, 7, 2);

        KruskalMST mst = new KruskalMST(graph);

        Edge toRemove = findEdgeByVertices(mst.getMSTEdges(), 3, 4);
        if (toRemove == null) toRemove = mst.getMSTEdges().get(2);

        List<Edge> mstCopy = new ArrayList<>(mst.getMSTEdges());
        mstCopy.remove(toRemove);
        List<Set<Integer>> components = ComponentFinder.findComponents(graph.getVertices(), mstCopy);

        MSTReplacer replacer = new MSTReplacer(graph, mst.getMSTEdges());
        Edge replacement = replacer.removeAndReplace(toRemove);

        // Добавляем работу для измерения времени
        for (int i = 0; i < 1000; i++) {
            Graph.calculateTotalWeight(replacer.getCurrentMSTEdges());
        }

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000;

        return new TestResult(
                "Large Graph",
                graph.getVertices(),
                graph.getEdges().size(),
                mst.getTotalWeight(),
                toRemove,
                replacement,
                Graph.calculateTotalWeight(replacer.getCurrentMSTEdges()),
                components.size(),
                Math.max(executionTime, 1)
        );
    }

    private static TestResult testBridgeRemoval() {
        long startTime = System.nanoTime();

        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(3, 4, 4);
        graph.addEdge(0, 4, 8);

        KruskalMST mst = new KruskalMST(graph);

        Edge toRemove = findEdgeByVertices(mst.getMSTEdges(), 0, 1);
        if (toRemove == null) toRemove = mst.getMSTEdges().get(0);

        List<Edge> mstCopy = new ArrayList<>(mst.getMSTEdges());
        mstCopy.remove(toRemove);
        List<Set<Integer>> components = ComponentFinder.findComponents(graph.getVertices(), mstCopy);

        MSTReplacer replacer = new MSTReplacer(graph, mst.getMSTEdges());
        Edge replacement = replacer.removeAndReplace(toRemove);

        // Добавляем работу для измерения времени
        for (int i = 0; i < 1000; i++) {
            Graph.calculateTotalWeight(replacer.getCurrentMSTEdges());
        }

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000;

        return new TestResult(
                "Bridge Removal",
                graph.getVertices(),
                graph.getEdges().size(),
                mst.getTotalWeight(),
                toRemove,
                replacement,
                Graph.calculateTotalWeight(replacer.getCurrentMSTEdges()),
                components.size(),
                Math.max(executionTime, 1)
        );
    }

    private static TestResult testComplexGraph() {
        long startTime = System.nanoTime();

        Graph graph = new Graph(7);
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 3, 1);
        graph.addEdge(2, 4, 5);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 3);
        graph.addEdge(4, 6, 4);
        graph.addEdge(5, 6, 2);

        KruskalMST mst = new KruskalMST(graph);

        Edge toRemove = findEdgeByVertices(mst.getMSTEdges(), 5, 6);
        if (toRemove == null) toRemove = mst.getMSTEdges().get(3);

        List<Edge> mstCopy = new ArrayList<>(mst.getMSTEdges());
        mstCopy.remove(toRemove);
        List<Set<Integer>> components = ComponentFinder.findComponents(graph.getVertices(), mstCopy);

        MSTReplacer replacer = new MSTReplacer(graph, mst.getMSTEdges());
        Edge replacement = replacer.removeAndReplace(toRemove);

        // Добавляем работу для измерения времени
        for (int i = 0; i < 1000; i++) {
            Graph.calculateTotalWeight(replacer.getCurrentMSTEdges());
        }

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000;

        return new TestResult(
                "Complex Graph",
                graph.getVertices(),
                graph.getEdges().size(),
                mst.getTotalWeight(),
                toRemove,
                replacement,
                Graph.calculateTotalWeight(replacer.getCurrentMSTEdges()),
                components.size(),
                Math.max(executionTime, 1)
        );
    }

    private static Edge findEdgeByVertices(List<Edge> edges, int u, int v) {
        for (Edge edge : edges) {
            if ((edge.getSource() == u && edge.getDestination() == v) ||
                    (edge.getSource() == v && edge.getDestination() == u)) {
                return edge;
            }
        }
        return null;
    }

    static class TestResult {
        String testCase;
        int vertices;
        int edges;
        int originalWeight;
        Edge removedEdge;
        Edge replacementEdge;
        int newWeight;
        int components;
        long executionTime;

        public TestResult(String testCase, int vertices, int edges, int originalWeight,
                          Edge removedEdge, Edge replacementEdge, int newWeight,
                          int components, long executionTime) {
            this.testCase = testCase;
            this.vertices = vertices;
            this.edges = edges;
            this.originalWeight = originalWeight;
            this.removedEdge = removedEdge;
            this.replacementEdge = replacementEdge;
            this.newWeight = newWeight;
            this.components = components;
            this.executionTime = executionTime;
        }

        public String toCSVRow() {
            String removedStr = (removedEdge != null) ?
                    removedEdge.getSource() + "-" + removedEdge.getDestination() + " (weight: " + removedEdge.getWeight() + ")" : "None";

            String replacementStr = (replacementEdge != null) ?
                    replacementEdge.getSource() + "-" + replacementEdge.getDestination() + " (weight: " + replacementEdge.getWeight() + ")" : "No replacement";

            int weightChange = newWeight - originalWeight;

            return String.format("%s,%d,%d,%d,%s,%s,%d,%d,%d,%d",
                    testCase, vertices, edges, originalWeight, removedStr, replacementStr,
                    newWeight, weightChange, components, executionTime);
        }
    }
}