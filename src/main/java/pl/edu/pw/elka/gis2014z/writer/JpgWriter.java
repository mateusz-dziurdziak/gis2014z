package pl.edu.pw.elka.gis2014z.writer;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkImages;
import pl.edu.pw.elka.gis2014z.graph.ToGraphStreamGraph;

import java.io.IOException;

public class JpgWriter {

    public static void writeGraphicGraphToFile(pl.edu.pw.elka.gis2014z.graph.Graph graph, String fileName)
            throws IOException {
        Graph transformed = new ToGraphStreamGraph().apply(graph);

        FileSinkImages fsi = new FileSinkImages(FileSinkImages.OutputType.PNG, FileSinkImages.Resolutions.HD720);
        fsi.setQuality(FileSinkImages.Quality.HIGH);

        fsi.setStyleSheet(
                "graph { padding: 50px; fill-color: black; }" +
                        "node { fill-color: #3d5689; }" +
                        "edge { fill-color: blue; }");

        fsi.setOutputPolicy(FileSinkImages.OutputPolicy.BY_EVENT);
        fsi.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_IN_LAYOUT_RUNNER);

        fsi.writeAll(transformed, fileName);
    }

}
