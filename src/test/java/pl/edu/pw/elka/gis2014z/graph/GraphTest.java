package pl.edu.pw.elka.gis2014z.graph;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class GraphTest {

    @Test
    public void testHasVertices() {
        int verticesCount = 50;
        Graph graph = new Graph(verticesCount);

        for (int i = 0; i < verticesCount; i++) {
            assertThat(graph.hasVertex(i));
        }

        assertEquals(0, graph.getEdgeCount());
        assertEquals(verticesCount, graph.getVertexCount());
    }

    @Test
    public void testAddAndHasEdge() {
        Graph graph = new Graph(3);

        graph.addEdge(0, 1);

        assertEquals(1, graph.getEdgeCount());
        assertThat(graph.hasEdge(0, 1));
        assertThat(graph.hasEdge(1, 0));
        assertThat(!graph.hasEdge(0, 2));
        assertThat(!graph.hasEdge(1, 2));
    }

    @Test
    public void testMultipleAddSameEdge() {
        Graph graph = new Graph(3);

        graph.addEdge(0, 1);
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);

        assertEquals(1, graph.getEdgeCount());
        assertThat(graph.hasEdge(0, 1));
        assertThat(graph.hasEdge(1, 0));
    }

    @Test
    public void testAddMultipleEdges() {
        Graph graph = new Graph(4);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(0, 2);

        assertEquals(3, graph.getEdgeCount());
        assertThat(graph.hasEdge(0, 1));
        assertThat(graph.hasEdge(1, 2));
        assertThat(graph.hasEdge(0, 2));
    }

    @Test
    public void testRemoveEdge() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);

        assertEquals(0, graph.getEdgeCount());
        assertThat(!graph.hasEdge(0, 1));
    }
}
