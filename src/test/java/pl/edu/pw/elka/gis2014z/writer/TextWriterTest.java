package pl.edu.pw.elka.gis2014z.writer;

import org.junit.Test;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class TextWriterTest {

    @Test
    public void testWriteEmpty() {
        Graph graph = new Graph(0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TextWriter.writeGraphToStream(graph, outputStream);

        assertEquals("", outputStream.toString());
    }

    @Test
    public void testNonEmptyWithoutEdges() {
        Graph graph = new Graph(6);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TextWriter.writeGraphToStream(graph, outputStream);

        assertEquals("", outputStream.toString());
    }

    @Test
    public void testNonEmpty() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(2, 3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TextWriter.writeGraphToStream(graph, outputStream);

        assertEquals("0 1\n2 3\n", new String(outputStream.toByteArray()));
    }

}
