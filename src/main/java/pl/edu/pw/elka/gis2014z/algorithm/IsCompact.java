package pl.edu.pw.elka.gis2014z.algorithm;

import net.openhft.koloboke.collect.IntCursor;
import net.openhft.koloboke.collect.set.hash.HashIntSet;
import net.openhft.koloboke.collect.set.hash.HashIntSets;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import java.util.Set;
import java.util.function.Function;

/**
 * Returns information if graph is compact
 */
public class IsCompact implements Function<Graph, Boolean>{

    @Override
    public Boolean apply(Graph graph) {
        Set<Integer> visitedVertices = tryVisitAllVertices(graph);
        return visitedVertices.size() == graph.getVertexCount();
    }

    private Set<Integer> tryVisitAllVertices(Graph graph) {
        final HashIntSet toVisit = HashIntSets.newMutableSet();
        if (graph.getVertexCount() != 0) {
            toVisit.add(0);
        }

        HashIntSet visited = HashIntSets.newUpdatableSet();
        while (!toVisit.isEmpty()) {
            IntCursor cursor = toVisit.cursor();
            cursor.moveNext();
            int vertex = cursor.elem();
            HashIntSet neighbours = graph.getVertexNeighbours(vertex);
            neighbours.forEach((int value) -> {
                if (!visited.contains(value)) {
                    toVisit.add(value);
                }
            });

            visited.add(vertex);
            toVisit.removeInt(vertex);
        }
        return visited;
    }

}
