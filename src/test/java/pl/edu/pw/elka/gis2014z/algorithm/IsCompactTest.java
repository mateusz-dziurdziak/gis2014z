package pl.edu.pw.elka.gis2014z.algorithm;

import org.junit.Test;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import static org.assertj.core.api.Assertions.assertThat;

public class IsCompactTest {

    @Test
    public void testEmpty() {
        Graph graph = new Graph(10);

        assertThat(!new IsCompact().apply(graph));
    }

    @Test
    public void testNonCompact() {
        Graph graph = new Graph(4);

        graph.addEdge(0, 1);
        graph.addEdge(2, 3);

        assertThat(!new IsCompact().apply(graph));
    }

    @Test
    public void testCompact() {
        Graph graph = new Graph(4);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        assertThat(new IsCompact().apply(graph));
    }

}
