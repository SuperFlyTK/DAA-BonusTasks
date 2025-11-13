package util;

import graph.Edge;
import java.util.*;

public class ComponentFinder {

    public static List<Set<Integer>> findComponents(int vertices, List<Edge> edges) {
        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (Edge edge : edges) {
            adjacencyList.get(edge.getSource()).add(edge.getDestination());
            adjacencyList.get(edge.getDestination()).add(edge.getSource());
        }

        boolean[] visited = new boolean[vertices];
        List<Set<Integer>> components = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                Set<Integer> component = new HashSet<>();
                dfs(i, adjacencyList, visited, component);
                components.add(component);
            }
        }
        return components;
    }

    private static void dfs(int vertex, List<List<Integer>> adjacencyList,
                            boolean[] visited, Set<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adjacencyList, visited, component);
            }
        }
    }

    public static void displayComponents(List<Set<Integer>> components) {
        System.out.println("=== CONNECTED COMPONENTS ===");
        if (components.size() == 1) {
            System.out.println("Graph is connected");
            List<Integer> vertices = new ArrayList<>(components.get(0));
            Collections.sort(vertices);
            System.out.println("Vertices: " + vertices);
        } else {
            System.out.println("Graph has " + components.size() + " components");
            for (int i = 0; i < components.size(); i++) {
                List<Integer> vertices = new ArrayList<>(components.get(i));
                Collections.sort(vertices);
                System.out.println("Component " + (i + 1) + ": " + vertices);
            }
        }
    }

    public static boolean isConnected(int vertices, List<Edge> edges) {
        return findComponents(vertices, edges).size() == 1;
    }
}