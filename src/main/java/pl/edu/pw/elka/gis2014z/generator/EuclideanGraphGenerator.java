package pl.edu.pw.elka.gis2014z.generator;

import com.google.common.collect.Range;
import pl.edu.pw.elka.gis2014z.graph.GraphWithWeightedEdges;

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

        graph = new GraphWithWeightedEdges(verticesCount);
        GraphWithWeightedEdges convertedGraph = (GraphWithWeightedEdges) graph;

        Random random = getRandomGenerator();

        float x[] = new float[verticesCount];
        float y[] = new float[verticesCount];

        float r = radius * radius;

        for (int i = 0; i < verticesCount; i++) {
            x[i] = random.nextFloat();
            y[i] = random.nextFloat();

            for (int j = 0; j < i; j++) {
                float d = ((x[i]-x[j])*(x[i]-x[j])) + (y[i]-y[j])*(y[i]-y[j]);
                if (d <= r) {
                    convertedGraph.addEdge(i, j, (float) Math.sqrt(d));
                }
            }
        }
    }

}
