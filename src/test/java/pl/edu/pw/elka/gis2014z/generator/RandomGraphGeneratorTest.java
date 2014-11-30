package pl.edu.pw.elka.gis2014z.generator;

import org.junit.Test;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class RandomGraphGeneratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidVerticesCount() {
        new RandomGraphGenerator(-1, 0.2f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidProbability() {
        new RandomGraphGenerator(2, -0.2f);
    }

    @Test
    public void testZeroProbablity() {
        GraphGenerator generator = new RandomGraphGenerator(200, 0.0f);
        generator.generate();

        Graph graph = generator.getGraph();

        assertEquals(200, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    public void testHighProbability() {
        GraphGenerator generator = new RandomGraphGenerator(200, 0.8f);
        generator.generate();

        Graph graph = generator.getGraph();

        assertEquals(200, graph.getVertexCount());
        assertThat(graph.getEdgeCount() > 0);
    }

}
