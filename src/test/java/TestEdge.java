import graph.Edge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestEdge {

    @Test
    void testEdgeCreation() {
        Edge edge = new Edge(1, 2, 5);
        assertEquals(1, edge.getSource());
        assertEquals(2, edge.getDestination());
        assertEquals(5, edge.getWeight());
    }

    @Test
    void testEdgeComparison() {
        Edge light = new Edge(1, 2, 3);
        Edge heavy = new Edge(3, 4, 7);
        assertTrue(light.compareTo(heavy) < 0);
        assertTrue(heavy.compareTo(light) > 0);
    }

    @Test
    void testEdgeEquality() {
        Edge edge1 = new Edge(1, 2, 5);
        Edge edge2 = new Edge(1, 2, 5);
        Edge edge3 = new Edge(2, 1, 5);

        assertEquals(edge1, edge2);
        assertEquals(edge1, edge3);
        assertNotEquals(edge1, new Edge(1, 2, 3));
    }
}