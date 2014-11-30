package pl.edu.pw.elka.gis2014z.graph;

import net.openhft.koloboke.collect.map.hash.HashIntObjMap;
import net.openhft.koloboke.collect.map.hash.HashIntObjMaps;
import net.openhft.koloboke.collect.set.hash.HashIntSet;
import net.openhft.koloboke.collect.set.hash.HashIntSets;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Model of graph. Following assumptions taken:
 * <ul>
 *     <li>self edges forbidden</li>
 *     <li>parallel edges are forbidden</li>
 *     <li>graph is not oriented</li>
 *     <li>removing of vertex is forbidden</li>
 *     <li>removing of edge is allowed</li>
 *     <li>edges does not have weights</li>
 * </ul>
 */
public class Graph {

    /**
     * Number of vertices in graph
     */
    private int vertexCount = 0;

    /**
     * Number of edges in graph
     */
    private int edgeCount = 0;

    /**
     * Map of vertices in graph
     *
     * <p>{ vertexNumber -> set of connected vertices }</p>
     *
     * <p>Connection between vertices is stored only in one place. If v1 < v2 only v1 list contains v2 number</p>
     */
    private final HashIntObjMap<HashIntSet> edgeMap;


    /**
     * Creates empty graph (without edges)
     * @param vertexCount number of initial vertices
     */
    public Graph(int vertexCount) {
        this.vertexCount = vertexCount;
        edgeMap = HashIntObjMaps.newUpdatableMap(vertexCount);
    }

    /**
     * Returns number of vertices in graph
     * @return {@link #vertexCount}
     */
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * Adds vertex to graph
     * @return number of added vertex
     */
    public int addVertex() {
        return ++this.vertexCount;
    }

    /**
     * Checks if graph contains given vertex
     * @param number vertexNumber
     * @return true if graph contains vertex, false in the other case
     * @throws java.lang.IllegalArgumentException if number is less than 0
     */
    public boolean hasVertex(int number) {
        checkArgument(number >= 0);

        return number < vertexCount;
    }

    /**
     * Return number of edges in graph
     * @return {@link #edgeCount}
     */
    public int getEdgeCount() {
        return edgeCount;
    }

    /**
     * Adds edge between vertices
     * @param firstVertex first vertex number
     * @param secondVertex second vertex number
     * @throws java.lang.IllegalArgumentException if firstVertex/secondVertex is not in graph
     * or if firstVertex==secondVertex
     */
    public void addEdge(int firstVertex, int secondVertex) {
        checkArgument(firstVertex >= 0 && firstVertex < vertexCount);
        checkArgument(secondVertex >= 0 && secondVertex < vertexCount);
        checkArgument(firstVertex != secondVertex);

        int lower = min(firstVertex, secondVertex);
        int upper = max(firstVertex, secondVertex);

        HashIntSet edgesFromLowerVertexSet = edgeMap.get(lower);
        if (edgesFromLowerVertexSet == null) {
            edgesFromLowerVertexSet = HashIntSets.newMutableSet();
            edgeMap.put(lower, edgesFromLowerVertexSet);
        }

        if (edgesFromLowerVertexSet.add(upper)) {
            edgeCount++;
        }
    }

    /**
     * Removes edge from graph
     * @param firstVertex first vertex number
     * @param secondVertex second vertex number
     * @throws java.lang.IllegalArgumentException if firstVertex/secondVertex is not in graph
     * @throws java.lang.IllegalStateException if edge does not exist
     */
    public void removeEdge(int firstVertex, int secondVertex) {
        checkArgument(firstVertex >= 0 && firstVertex < vertexCount);
        checkArgument(secondVertex >= 0 && secondVertex < vertexCount);

        int lower = min(firstVertex, secondVertex);
        int upper = max(firstVertex, secondVertex);

        HashIntSet edgesFromLowerVertexSet = edgeMap.get(lower);
        checkState(edgesFromLowerVertexSet != null);

        checkState(edgesFromLowerVertexSet.removeInt(upper));
        edgeCount--;
    }

    /**
     * Checks if edge between vertices exists
     * @param firstVertex first vertex number
     * @param secondVertex second vertext number
     * @return true if vertex exists, false in the other case
     * @throws java.lang.IllegalArgumentException if firstVertex/secondVertex is not in graph
     */
    public boolean hasEdge(int firstVertex, int secondVertex) {
        checkArgument(firstVertex >= 0 && firstVertex < vertexCount);
        checkArgument(secondVertex >= 0 && secondVertex < vertexCount);

        int lower = min(firstVertex, secondVertex);
        int upper = max(firstVertex, secondVertex);

        HashIntSet edgesFromLowerVertexSet = edgeMap.get(lower);

        return edgesFromLowerVertexSet == null ? false : edgesFromLowerVertexSet.contains(upper);
    }

}
