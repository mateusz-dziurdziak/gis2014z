package pl.edu.pw.elka.gis2014z.generator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScaleFreeGraphGeneratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInitialVerticesCount() {
        new ScaleFreeGraphGenerator(-1, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInitialEdgesCount() {
        new ScaleFreeGraphGenerator(2, 3, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAddedVertices() {
        new ScaleFreeGraphGenerator(2, 1, 0);
    }

    @Test
    public void testInitGraph() {
        GraphGenerator generator = new ScaleFreeGraphGenerator(10, 5, 1);
        generator.generate();

        assertEquals(10, generator.getGraph().getVertexCount());
        assertEquals(5, generator.getGraph().getEdgeCount());
    }

    @Test
    public void testAddVertices() {
        GraphGenerator generator = new ScaleFreeGraphGenerator(10, 5, 2);
        generator.generate();
        generator.generate();
        generator.generate();

        assertEquals(14, generator.getGraph().getVertexCount());
    }

}
