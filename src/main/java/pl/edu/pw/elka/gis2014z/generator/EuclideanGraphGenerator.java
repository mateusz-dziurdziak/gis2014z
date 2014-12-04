package pl.edu.pw.elka.gis2014z.generator;

import com.google.common.collect.Range;
import pl.edu.pw.elka.gis2014z.graph.GraphWithDimension;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Generates eucledian network graph generator
 */
public class EuclideanGraphGenerator extends AbstractGraphGenerator {

    /**
     * Vertices number in graph
     */
    private final int verticesCount;

    /**
     * Radius
     */
    private final float radius;

    /**
     * Constructs generator
     * @param verticesCount {@link #verticesCount}
     * @param radius {@link #radius}
     * @throws java.lang.IllegalArgumentException if
     * <ul>
     *     <li>verticesCount < 0</li>
     *     <li>radius is not inside range [0, sqrt(2)]</li>
     * </ul>
     */
    public EuclideanGraphGenerator(int verticesCount, float radius) {
        checkArgument(verticesCount >= 0);
        checkArgument(Range.closed(0f, (float) Math.sqrt(2)).contains(radius));

        this.verticesCount = verticesCount;
        this.radius = radius;
    }

    @Override
    protected void doGenerate() {
        checkState(graph == null);

        graph = new GraphWithDimension(0);
        GraphWithDimension convertedGraph = (GraphWithDimension) graph;

        Random random = getRandomGenerator();

        float r = radius * radius;

        for (int i = 0; i < verticesCount; i++) {
            float x = random.nextFloat();
            float y = random.nextFloat();
            convertedGraph.addVertex(x, y);

            for (int j = 0; j < i; j++) {
                float xSec = convertedGraph.getVertexX(j);
                float ySec = convertedGraph.getVertexY(j);
                float d = ((x-xSec)*(x-xSec)) + ((y-ySec)*(y-ySec));
                if (d <= r) {
                    convertedGraph.addEdge(i, j);
                }
            }
        }
    }

}
