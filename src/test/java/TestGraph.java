import graph.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestGraph {

    @Test
    void testGraphCreation() {
        Graph graph = new Graph(5);
        assertEquals(5, graph.getVertices());
        assertEquals(0, graph.getEdges().size());
    }

    @Test
    void testAddEdges() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 10);
        graph.addEdge(1, 2, 20);

        assertEquals(2, graph.getEdges().size());
        assertEquals(1, graph.getAdjacentEdges(0).size());
        assertEquals(2, graph.getAdjacentEdges(1).size());
    }

    @Test
    void testSampleGraph() {
        Graph graph = Graph.createSampleGraph();
        assertEquals(6, graph.getVertices());
        assertEquals(9, graph.getEdges().size());
    }
}