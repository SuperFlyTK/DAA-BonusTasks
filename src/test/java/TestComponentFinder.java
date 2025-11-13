import graph.Graph;
import util.ComponentFinder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

class TestComponentFinder {

    @Test
    void testConnectedGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);

        List<Set<Integer>> components = ComponentFinder.findComponents(4, graph.getEdges());
        assertEquals(1, components.size());
        assertTrue(ComponentFinder.isConnected(4, graph.getEdges()));
    }

    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(3, 4, 1);

        List<Set<Integer>> components = ComponentFinder.findComponents(5, graph.getEdges());
        assertEquals(2, components.size());
        assertFalse(ComponentFinder.isConnected(5, graph.getEdges()));
    }
}