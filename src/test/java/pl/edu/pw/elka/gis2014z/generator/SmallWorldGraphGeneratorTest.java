package pl.edu.pw.elka.gis2014z.generator;

import org.junit.Test;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import static org.junit.Assert.assertEquals;

public class SmallWorldGraphGeneratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidVerticesCount() {
        new SmallWorldGraphGenerator(-1, 0, 0.2f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGraphLevel() {
        new SmallWorldGraphGenerator(2, 2, -0.2f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidProbability() {
        new SmallWorldGraphGenerator(2, 0, -0.2f);
    }

    @Test
         public void testEdgesCountZeroProbability() {
        GraphGenerator graphGenerator = new SmallWorldGraphGenerator(100, 4, 0.0f);
        graphGenerator.generate();

        Graph graph = graphGenerator.getGraph();

        assertEquals(100, graph.getVertexCount());
        assertEquals(200, graph.getEdgeCount());
    }

    @Test
    public void testEdgesCountHighProbability() {
        GraphGenerator graphGenerator = new SmallWorldGraphGenerator(100, 4, 0.8f);
        graphGenerator.generate();

        Graph graph = graphGenerator.getGraph();

        assertEquals(100, graph.getVertexCount());
        assertEquals(200, graph.getEdgeCount());
    }

}
