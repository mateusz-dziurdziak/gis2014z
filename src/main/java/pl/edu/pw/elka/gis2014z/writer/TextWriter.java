package pl.edu.pw.elka.gis2014z.writer;

import net.openhft.koloboke.collect.IntCursor;
import pl.edu.pw.elka.gis2014z.generator.AbstractGraphGenerator;
import pl.edu.pw.elka.gis2014z.graph.Graph;

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
        for (int i = 0; i < graph.getVertexCount(); i++) {
            IntCursor cursor = graph.getVertexNeighbours(i).cursor();
            while(cursor.moveNext()) {
                printWriter.write(i + " " + cursor.elem() + "\n");
            }
        }
        printWriter.flush();
    }

}
