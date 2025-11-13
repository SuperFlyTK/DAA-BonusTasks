import graph.UnionFind;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestUnionFind {

    @Test
    void testInitialState() {
        UnionFind uf = new UnionFind(5);
        assertEquals(5, uf.getComponentCount());
        assertTrue(uf.connected(0, 0));
    }

    @Test
    void testUnionOperations() {
        UnionFind uf = new UnionFind(5);

        assertTrue(uf.union(0, 1));
        assertTrue(uf.connected(0, 1));
        assertEquals(4, uf.getComponentCount());

        assertFalse(uf.union(0, 1));
        assertEquals(4, uf.getComponentCount());
    }

    @Test
    void testTransitiveConnections() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 2);

        assertTrue(uf.connected(0, 2));
        assertFalse(uf.connected(0, 3));
    }
}