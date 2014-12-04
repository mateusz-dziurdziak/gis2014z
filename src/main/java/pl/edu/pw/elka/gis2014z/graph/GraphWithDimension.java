package pl.edu.pw.elka.gis2014z.graph;

import net.openhft.koloboke.collect.map.hash.HashIntFloatMap;
import net.openhft.koloboke.collect.map.hash.HashIntFloatMaps;
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
public class GraphWithDimension extends Graph {

    private HashIntFloatMap xMap = HashIntFloatMaps.newUpdatableMap();

    private HashIntFloatMap yMap = HashIntFloatMaps.newUpdatableMap();

    /**
     * Creates empty graph (without edges)
     *
     * @param vertexCount number of initial vertices
     */
    public GraphWithDimension(int vertexCount) {
        super(vertexCount);
    }


    @Override
    @Deprecated
    public int addVertex() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int addVertex(float x, float y) {
        int index = super.addVertex();
        xMap.put(index, x);
        yMap.put(index, y);
        return index;
    }

    @Override
    @Deprecated
    public void removeEdge(int firstVertex, int secondVertex) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets vertex x position
     * @param vertex vertex
     * @return vertex x position
     */
    public float getVertexX(int vertex) {
        return xMap.get(vertex);
    }

    /**
     * Gets vertex y position
     * @param vertex vertex
     * @return vertex y position
     */
    public float getVertexY(int vertex) {
        return yMap.get(vertex);
    }

}
