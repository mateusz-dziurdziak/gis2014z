package pl.edu.pw.elka.gis2014z.graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphWithWeightedEdgesTest {

    @Test
    public void testSetAndGetWeight() {
        GraphWithWeightedEdges graph = new GraphWithWeightedEdges(6);
        graph.addEdge(0, 1, 1.0f);
        graph.addEdge(1, 4, 2.0f);

        assertEquals(6, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
        assertEquals(1.0f, graph.getEdgeWeight(0, 1), 0.f);
        assertEquals(2.0f, graph.getEdgeWeight(1, 4), 0.f);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddEdgeWithoutWeight() {
        GraphWithWeightedEdges graph = new GraphWithWeightedEdges(2);
        graph.addEdge(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWeightOfUnexistingEdge() {
        GraphWithWeightedEdges graph = new GraphWithWeightedEdges(6);
        graph.getEdgeWeight(0, 1);
    }

}
