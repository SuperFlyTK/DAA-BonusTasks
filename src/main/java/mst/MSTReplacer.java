package mst;

import graph.*;
import util.ComponentFinder;
import java.util.*;

public class MSTReplacer {
    private final Graph originalGraph;
    private List<Edge> currentMSTEdges;

    public MSTReplacer(Graph originalGraph, List<Edge> mstEdges) {
        this.originalGraph = originalGraph;
        this.currentMSTEdges = new ArrayList<>(mstEdges);
    }

    public Edge removeAndReplace(Edge edgeToRemove) {
        System.out.println("=== EDGE REMOVAL ===");

        boolean removed = currentMSTEdges.removeIf(edge ->
                (edge.getSource() == edgeToRemove.getSource() &&
                        edge.getDestination() == edgeToRemove.getDestination()) ||
                        (edge.getSource() == edgeToRemove.getDestination() &&
                                edge.getDestination() == edgeToRemove.getSource())
        );

        if (!removed) {
            System.out.println("Edge not found in MST");
            return null;
        }

        System.out.println("Removed: " + edgeToRemove.getSource() + " -- " +
                edgeToRemove.getDestination() + " (weight: " + edgeToRemove.getWeight() + ")");

        List<Set<Integer>> components = ComponentFinder.findComponents(
                originalGraph.getVertices(), currentMSTEdges
        );

        ComponentFinder.displayComponents(components);

        if (components.size() == 1) {
            System.out.println("No replacement needed");
            return null;
        }

        Edge replacement = findReplacementEdge(components);

        if (replacement != null) {
            currentMSTEdges.add(replacement);
            System.out.println("Replacement: " + replacement.getSource() + " -- " +
                    replacement.getDestination() + " (weight: " + replacement.getWeight() + ")");
        } else {
            System.out.println("No replacement found");
        }

        return replacement;
    }

    private Edge findReplacementEdge(List<Set<Integer>> components) {
        if (components.size() != 2) {
            return findReplacementEdgeMultipleComponents(components);
        }

        Set<Integer> component1 = components.get(0);
        Set<Integer> component2 = components.get(1);
        Edge minEdge = null;
        int minWeight = Integer.MAX_VALUE;

        for (Edge edge : originalGraph.getEdges()) {
            int source = edge.getSource();
            int dest = edge.getDestination();

            boolean connectsComponents =
                    (component1.contains(source) && component2.contains(dest)) ||
                            (component2.contains(source) && component1.contains(dest));

            if (connectsComponents) {
                boolean alreadyInMST = currentMSTEdges.stream()
                        .anyMatch(mstEdge ->
                                (mstEdge.getSource() == source && mstEdge.getDestination() == dest) ||
                                        (mstEdge.getSource() == dest && mstEdge.getDestination() == source)
                        );

                if (!alreadyInMST && edge.getWeight() < minWeight) {
                    minWeight = edge.getWeight();
                    minEdge = edge;
                }
            }
        }
        return minEdge;
    }

    private Edge findReplacementEdgeMultipleComponents(List<Set<Integer>> components) {
        Edge minEdge = null;
        int minWeight = Integer.MAX_VALUE;

        for (Edge edge : originalGraph.getEdges()) {
            boolean alreadyInMST = currentMSTEdges.stream()
                    .anyMatch(mstEdge ->
                            (mstEdge.getSource() == edge.getSource() &&
                                    mstEdge.getDestination() == edge.getDestination()) ||
                                    (mstEdge.getSource() == edge.getDestination() &&
                                            mstEdge.getDestination() == edge.getSource())
                    );

            if (alreadyInMST) continue;

            int sourceComponent = -1;
            int destComponent = -1;

            for (int i = 0; i < components.size(); i++) {
                if (components.get(i).contains(edge.getSource())) sourceComponent = i;
                if (components.get(i).contains(edge.getDestination())) destComponent = i;
            }

            if (sourceComponent != destComponent && sourceComponent != -1 && destComponent != -1) {
                if (edge.getWeight() < minWeight) {
                    minWeight = edge.getWeight();
                    minEdge = edge;
                }
            }
        }
        return minEdge;
    }

    public List<Edge> getCurrentMSTEdges() { return new ArrayList<>(currentMSTEdges); }

    public void displayCurrentMST() {
        System.out.println("=== UPDATED MST ===");
        for (int i = 0; i < currentMSTEdges.size(); i++) {
            Edge edge = currentMSTEdges.get(i);
            System.out.println((i + 1) + ". " + edge.getSource() + " -- " +
                    edge.getDestination() + " (weight: " + edge.getWeight() + ")");
        }
        int totalWeight = Graph.calculateTotalWeight(currentMSTEdges);
        System.out.println("Total Weight: " + totalWeight);
    }
}