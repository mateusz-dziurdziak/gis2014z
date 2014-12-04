package pl.edu.pw.elka.gis2014z.graph;

import net.openhft.koloboke.collect.IntCursor;
import net.openhft.koloboke.collect.set.hash.HashIntSet;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.function.Function;

public class ToGraphStreamGraph implements Function<Graph, org.graphstream.graph.Graph> {


    @Override
    public org.graphstream.graph.Graph apply(Graph graph) {
        org.graphstream.graph.Graph transformed = new SingleGraph("");

        for (int i=0; i < graph.getVertexCount(); i++) {
            transformed.addNode(String.valueOf(i));
            if (graph instanceof GraphWithDimension) {
                transformed.getNode(String.valueOf(i)).setAttribute("xyz",
                        ((GraphWithDimension) graph).getVertexX(i),
                        ((GraphWithDimension) graph).getVertexY(i),
                        0.0);
            }
        }

        for (int i=0; i < graph.getVertexCount(); i++) {
            HashIntSet neighbours = graph.getVertexNeighbours(i);
            IntCursor cursor = neighbours.cursor();
            while(cursor.moveNext()) {
                int secondVertex = cursor.elem();
                String edgeId = String.valueOf(i) + "_" + String.valueOf(secondVertex);
                transformed.addEdge(edgeId, String.valueOf(i), String.valueOf(secondVertex));
            }
        }

        return transformed;
    }

}
