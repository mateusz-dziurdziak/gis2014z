package pl.edu.pw.elka.gis2014z.graph;

import net.openhft.koloboke.collect.map.hash.HashLongFloatMap;
import net.openhft.koloboke.collect.map.hash.HashLongFloatMaps;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Model of graph. Following assumptions taken:
 * <ul>
 *     <li>self edges forbidden</li>
 *     <li>parallel edges are forbidden</li>
 *     <li>graph is not oriented</li>
 *     <li>removing of vertex is forbidden</li>
 *     <li>removing of edge is forbidden</li>
 * </ul>
 */
public class GraphWithWeightedEdges extends Graph {

    /**
     * Maps containg weight of edges (i,j).
     * {i*Long.MAX_VALUE + j -> weight}
     */
    private HashLongFloatMap weightMap = HashLongFloatMaps.newUpdatableMap();

    /**
     * Creates empty graph (without edges)
     *
     * @param vertexCount number of initial vertices
     */
    public GraphWithWeightedEdges(int vertexCount) {
        super(vertexCount);
    }

    @Override
    @Deprecated
    public void addEdge(int firstVertex, int secondVertex) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds edge to graph
     * @param firstVertext first vertex number
     * @param secondVertex second vertex number
     * @param weight weight of edge
     */
    public void addEdge(int firstVertext, int secondVertex, float weight) {
        super.addEdge(firstVertext, secondVertex);
        weightMap.put(calculateEdgeIndex(firstVertext, secondVertex), weight);
    }

    @Override
    @Deprecated
    public void removeEdge(int firstVertex, int secondVertex) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns weight of edge
     * @param firstVertext first vertex number
     * @param secondVertex second vertex number
     * @return weight of edge
     * @throws java.lang.IllegalArgumentException if edge does not exist
     */
    public float getEdgeWeight(int firstVertext, int secondVertex) {
        checkArgument(hasEdge(firstVertext, secondVertex));

        return weightMap.get(calculateEdgeIndex(firstVertext, secondVertex));
    }

    private long calculateEdgeIndex(int firstVertex, int secondVertex) {
        int lower = min(firstVertex, secondVertex);
        int upper = max(firstVertex, secondVertex);

        return Long.MAX_VALUE * lower + upper;
    }

}
