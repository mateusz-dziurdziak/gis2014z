package pl.edu.pw.elka.gis2014z.generator;

import com.google.common.collect.Range;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Generator of random graph.
 */
public class RandomGraphGenerator implements GraphGenerator {

    /**
     * Number of vertices
     */
    private final int verticesCount;

    /**
     * Probability of edge
     */
    private final float probablityOfEdge;

    /**
     * Generated graph
     */
    private Graph graph;

    /**
     * Creates RandomGraphGenerator
     * @param verticesCount number of vertices
     * @param probablityOfEdge probability of edge
     * @throws java.lang.IllegalArgumentException if verticesCount < 0 or if probablityOfEdge is not inside range [0, 1]
     */
    public RandomGraphGenerator(int verticesCount, float probablityOfEdge) {
        checkArgument(verticesCount >= 0);
        checkArgument(Range.closed(0.0f, 1.0f).contains(probablityOfEdge));

        this.verticesCount = verticesCount;
        this.probablityOfEdge = probablityOfEdge;
    }

    @Override
    public void generate() {
        checkState(graph == null);

        graph = new Graph(verticesCount);

        Random random = new Random();

        for (int i = 0; i < verticesCount; i++) {
            for (int j = i; j < verticesCount; j++) {
                if (i == j ) {
                    // we are not creating self edges
                    continue;
                }

                float randomProb = random.nextFloat();
                if (randomProb < probablityOfEdge) {
                    graph.addEdge(i, j);
                }
            }
        }
    }

    @Override
    public Graph getGraph() {
        checkState(graph != null);
        return graph;
    }

}
