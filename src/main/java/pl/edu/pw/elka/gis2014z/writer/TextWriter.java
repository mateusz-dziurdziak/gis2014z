package pl.edu.pw.elka.gis2014z.writer;

import net.openhft.koloboke.collect.IntCursor;
import pl.edu.pw.elka.gis2014z.generator.AbstractGraphGenerator;
import pl.edu.pw.elka.gis2014z.graph.Graph;
import pl.edu.pw.elka.gis2014z.graph.GraphWithDimension;

import java.io.OutputStream;
import java.io.PrintWriter;

public class TextWriter {

    public static void writeGraphInfoToStream(Graph graph, OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.write("Wierzchołki: " + graph.getVertexCount() + "\n");
        printWriter.write("Krawędzie: " + graph.getEdgeCount() +"\n");

        printWriter.flush();
    }

    public static void writeGenerationInfoToStream(AbstractGraphGenerator graphGenerator,
                                                   OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        int generationNumber = 1;
        for (Long time : graphGenerator.getGenerationsTimes()) {
            printWriter.write("Generowanie nr " + generationNumber + " zajęło " + time + " ms\n");
            generationNumber++;
        }

        printWriter.flush();
    }

    public static void writeGraphToStream(Graph graph, OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);

        if (graph instanceof GraphWithDimension) {
            GraphWithDimension graphWithDimension = (GraphWithDimension) graph;
            printWriter.write("Wierzchołek | x | y\n");
            for (int i = 0; i < graph.getVertexCount(); i++) {
                printWriter.write(i + " | " + graphWithDimension.getVertexX(i) + " | " + graphWithDimension.getVertexY(i) + "\n");
            }
            printWriter.write("\n\n");
        }

        printWriter.write("Wierzchołek | sąsiad\n");
        for (int i = 0; i < graph.getVertexCount(); i++) {
            IntCursor cursor = graph.getVertexNeighbours(i).cursor();
            while(cursor.moveNext()) {
                printWriter.write(i + " " + cursor.elem() + "\n");
            }
        }

        printWriter.flush();
    }

}
