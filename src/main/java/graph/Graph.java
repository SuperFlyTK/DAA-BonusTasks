package graph;

import java.util.*;

public class Graph {
    private final int vertices;
    private final List<Edge> edges;
    private final List<List<Edge>> adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        edges.add(edge);
        adjacencyList.get(source).add(edge);
        adjacencyList.get(destination).add(new Edge(destination, source, weight));
    }

    public void addEdge(Edge edge) {
        addEdge(edge.getSource(), edge.getDestination(), edge.getWeight());
    }

    public int getVertices() { return vertices; }
    public List<Edge> getEdges() { return new ArrayList<>(edges); }
    public List<List<Edge>> getAdjacencyList() { return adjacencyList; }

    public List<Edge> getAdjacentEdges(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException("Vertex out of range");
        }
        return new ArrayList<>(adjacencyList.get(vertex));
    }

    public void printGraph() {
        System.out.println("Graph with " + vertices + " vertices and " + edges.size() + " edges:");
        for (Edge edge : edges) {
            System.out.println("  " + edge.getSource() + " -- " + edge.getDestination() +
                    " (weight: " + edge.getWeight() + ")");
        }
    }

    public static Graph createSampleGraph() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(2, 4, 5);
        graph.addEdge(3, 4, 1);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 2);
        return graph;
    }

    public static int calculateTotalWeight(List<Edge> edges) {
        return edges.stream().mapToInt(Edge::getWeight).sum();
    }
}