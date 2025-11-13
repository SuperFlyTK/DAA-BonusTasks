import graph.Graph;
import mst.KruskalMST;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestKruskalMST {

    @Test
    void testMSTConstruction() {
        Graph graph = createTestGraph();
        KruskalMST mst = new KruskalMST(graph);

        assertEquals(3, mst.getMSTEdges().size());
        assertTrue(mst.isValid(graph.getVertices()));
        assertTrue(mst.getTotalWeight() > 0);
    }

    @Test
    void testMSTCorrectness() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 4);

        KruskalMST mst = new KruskalMST(graph);
        assertEquals(8, mst.getTotalWeight());
    }

    private Graph createTestGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 4);
        return graph;
    }
}