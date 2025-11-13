import graph.Graph;
import graph.Edge;
import mst.KruskalMST;
import mst.MSTReplacer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestMSTReplacer {

    @Test
    void testBasicReplacement() {
        Graph graph = createReplacementGraph();
        KruskalMST mst = new KruskalMST(graph);
        MSTReplacer replacer = new MSTReplacer(graph, mst.getMSTEdges());

        Edge toRemove = findEdge(mst.getMSTEdges(), 1, 2);
        Edge replacement = replacer.removeAndReplace(toRemove);

        assertNotNull(replacement);
        assertEquals(4, replacer.getCurrentMSTEdges().size());
    }

    @Test
    void testNoReplacementNeeded() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);

        KruskalMST mst = new KruskalMST(graph);
        MSTReplacer replacer = new MSTReplacer(graph, mst.getMSTEdges());

        Edge removed = replacer.removeAndReplace(mst.getMSTEdges().get(0));
        assertTrue(replacer.getCurrentMSTEdges().size() <= 2);
    }

    private Graph createReplacementGraph() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(3, 4, 4);
        graph.addEdge(0, 2, 5);
        return graph;
    }

    private Edge findEdge(java.util.List<Edge> edges, int u, int v) {
        return edges.stream()
                .filter(e -> (e.getSource() == u && e.getDestination() == v) ||
                        (e.getSource() == v && e.getDestination() == u))
                .findFirst()
                .orElse(null);
    }
}