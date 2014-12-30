package pl.edu.pw.elka.gis2014z.writer;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkImages;
import pl.edu.pw.elka.gis2014z.graph.GraphWithDimension;
import pl.edu.pw.elka.gis2014z.graph.ToGraphStreamGraph;

import java.io.IOException;
import java.util.Random;

public class JpgWriter {

    public static void writeGraphicGraphToFile(pl.edu.pw.elka.gis2014z.graph.Graph graph, String fileName)
            throws IOException {
        Graph transformed = new ToGraphStreamGraph().apply(graph);

        FileSinkImages fsi = new FileSinkImages(FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.HD720);
        fsi.setQuality(FileSinkImages.Quality.HIGH);

        Random random = new Random();
        String[] colours = new String[]{"red", "yellow", "green", "blue", "orange"};
        // Set the colors.
        for(Edge edge:transformed.getEachEdge()) {
            edge.setAttribute("ui.color", random.nextDouble());
        }

        fsi.setStyleSheet(
                "graph { padding: 50px; fill-color: black; }" +
                        "node { fill-color: black; }" +
                        "edge { fill-color: red, yellow, green, blue, orange;" +
                        "   fill-mode: dyn-plain; }");

        fsi.setOutputPolicy(FileSinkImages.OutputPolicy.BY_EVENT);
        if (graph instanceof GraphWithDimension) {
            fsi.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_IN_LAYOUT_RUNNER);
        } else {
            fsi.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_ONCE_AT_NEW_IMAGE);
        }

        fsi.writeAll(transformed, fileName);
    }

}
