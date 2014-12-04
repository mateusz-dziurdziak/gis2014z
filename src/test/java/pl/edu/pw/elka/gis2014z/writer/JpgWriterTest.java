package pl.edu.pw.elka.gis2014z.writer;

import org.junit.Test;
import pl.edu.pw.elka.gis2014z.generator.EuclideanGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.RandomGraphGenerator;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import java.io.File;
import java.io.IOException;

public class JpgWriterTest {

    @Test
    public void testExport() throws IOException {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);

        JpgWriter.writeGraphicGraphToFile(graph, "testFile.png");

        new File("testFile.png").delete();
    }

    @Test
    public void testRandomExport() throws IOException {
        RandomGraphGenerator generator = new RandomGraphGenerator(500, 0.01f);
        generator.generate();

        JpgWriter.writeGraphicGraphToFile(generator.getGraph(), "random.png");

        new File("random.png").delete();
    }

    @Test
    public void testEuclideanExport() throws IOException {
        EuclideanGraphGenerator generator = new EuclideanGraphGenerator(200, 0.2f);
        generator.generate();

        JpgWriter.writeGraphicGraphToFile(generator.getGraph(), "euclidean.png");

        new File("euclidean.png").delete();
    }

}
