package pl.edu.pw.elka.gis2014z.graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GraphWithDimensionTest {

    @Test
    public void testSetAndGetWeight() {
        GraphWithDimension graph = new GraphWithDimension(0);
        graph.addVertex(1.0f, 3.0f);
        graph.addVertex(0.0f, 2.0f);

        assertEquals(2, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());
        assertEquals(1.0f, graph.getVertexX(0), 0.f);
        assertEquals(3.0f, graph.getVertexY(0), 0.f);
        assertEquals(0.0f, graph.getVertexX(1), 0.f);
        assertEquals(2.0f, graph.getVertexY(1), 0.f);
    }

}
