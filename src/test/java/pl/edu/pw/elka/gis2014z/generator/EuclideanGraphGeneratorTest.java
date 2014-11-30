package pl.edu.pw.elka.gis2014z.generator;

import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class EuclideanGraphGeneratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidVerticesCount() {
        new EuclideanGraphGenerator(-1, 0.2f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRadius() {
        new EuclideanGraphGenerator(2, -0.2f);
    }

    @Test
    public void testZeroRadius() {
        GraphGenerator generator = new EuclideanGraphGenerator(100, 0.0f);
        generator.generate();

        assertEquals(100, generator.getGraph().getVertexCount());
        assertEquals(0, generator.getGraph().getEdgeCount());
    }

    @Test
    public void testMaximumRadius() {
        GraphGenerator generator = new EuclideanGraphGenerator(100, (float) Math.sqrt(2));
        generator.generate();

        assertEquals(100, generator.getGraph().getVertexCount());
        assertEquals(100 * 99 / 2, generator.getGraph().getEdgeCount());
    }

    @Test
    public void testNormalRadius() {
        GraphGenerator generator = new EuclideanGraphGenerator(100, 0.4f) {
            @Override
            protected Random getRandomGenerator() {
                return new Random(1);
            }
        };
        generator.generate();

        assertEquals(100, generator.getGraph().getVertexCount());
        assertThat(generator.getGraph().getEdgeCount() > 0);
    }

}
