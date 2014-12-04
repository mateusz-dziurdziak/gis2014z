package pl.edu.pw.elka.gis2014z.graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToGraphStreamGraphTest {

    @Test
    public void testTransformationNoEdges() {
        Graph graph = new Graph(50);

        org.graphstream.graph.Graph transformed = new ToGraphStreamGraph().apply(graph);

        assertEquals(50, transformed.getNodeCount());
    }

    @Test
    public void testTransformation() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        org.graphstream.graph.Graph transformed = new ToGraphStreamGraph().apply(graph);

        assertEquals(3, transformed.getEdgeCount());
    }

}
