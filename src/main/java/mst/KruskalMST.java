package mst;

import graph.*;
import java.util.*;

public class KruskalMST {
    private final List<Edge> mstEdges;
    private final int totalWeight;

    public KruskalMST(Graph graph) {
        this.mstEdges = new ArrayList<>();
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);

        UnionFind uf = new UnionFind(graph.getVertices());
        int edgesAdded = 0;
        int targetEdges = graph.getVertices() - 1;

        for (Edge edge : sortedEdges) {
            if (edgesAdded >= targetEdges) break;
            if (uf.union(edge.getSource(), edge.getDestination())) {
                mstEdges.add(edge);
                edgesAdded++;
            }
        }
        this.totalWeight = Graph.calculateTotalWeight(mstEdges);
    }

    public List<Edge> getMSTEdges() { return new ArrayList<>(mstEdges); }
    public int getTotalWeight() { return totalWeight; }

    public void displayMST() {
        System.out.println("=== MINIMUM SPANNING TREE ===");
        for (int i = 0; i < mstEdges.size(); i++) {
            Edge edge = mstEdges.get(i);
            System.out.println((i + 1) + ". " + edge.getSource() + " -- " +
                    edge.getDestination() + " (weight: " + edge.getWeight() + ")");
        }
        System.out.println("Total Weight: " + totalWeight);
        System.out.println("Edges: " + mstEdges.size());
    }

    public boolean isValid(int vertices) {
        return mstEdges.size() == vertices - 1;
    }
}