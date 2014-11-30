package pl.edu.pw.elka.gis2014z.generator;

import com.google.common.collect.Range;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Generator of scale free network
 */
public class ScaleFreeGraphGenerator extends AbstractGraphGenerator {

    /**
     * Initial vertices count
     */
    private final int initialVerticesCount;

    /**
     * Initial edges count
     */
    private final int initialEdgesCount;

    /**
     * Vertices added at each step
     */
    private final int verticesAddedAtEachStep;

    /**
     * Sum of all degrees of vertices
     */
    private int graphDegreeSum = 0;

    /**
     * Creates ScaleFreeGraphGenerator
     * @param initialVerticesCount {@link #initialVerticesCount}
     * @param initialEdgesCount {@link #initialEdgesCount}
     * @param verticesAddedAtEachStep {@link #verticesAddedAtEachStep}
     * @throws java.lang.IllegalArgumentException if
     * <ui>
     *     <li>initialVerticesCount < 0</li>
     *     <li>initialEdgesCount < 0 or initialEdgesCount > initialVerticesCount * (initialVerticesCount - 1)</li>
     *     <li>verticesAddedAtEachStep <= 0</li>
     * </ui>
     */
    public ScaleFreeGraphGenerator(int initialVerticesCount, int initialEdgesCount, int verticesAddedAtEachStep) {
        checkArgument(initialVerticesCount >= 0);
        checkArgument(Range.closed(0, (initialVerticesCount * (initialVerticesCount - 1))).contains(initialEdgesCount));
        checkArgument(verticesAddedAtEachStep > 0);

        this.initialVerticesCount = initialVerticesCount;
        this.initialEdgesCount = initialEdgesCount;
        this.verticesAddedAtEachStep = verticesAddedAtEachStep;
    }

    @Override
    protected void doGenerate() {
        Random random = getRandomGenerator();
        if (graph == null) {
            initGraph();
        } else {
            addNewVertices();
        }
    }

    /**
     * Creates initial graph with initialVerticesCount vertices and initialEdgesCount edges
     */
    private void initGraph() {
        Random random = getRandomGenerator();
        graph = new Graph(initialVerticesCount);

        while (graph.getEdgeCount() < initialEdgesCount) {
            int lowerVertex = random.nextInt(initialVerticesCount);
            int upperVertex = random.nextInt(initialVerticesCount);

            if (lowerVertex == upperVertex
                    || graph.hasEdge(lowerVertex, upperVertex)) {
                continue;
            }

            graph.addEdge(lowerVertex, upperVertex);
            graphDegreeSum += 2;
        }
    }

    /**
     * Added verticesAddedAtEachStep vertices to graph with probability of edge between existing <b>i</b> vertex = d(i)/d(G)
     * where d(i) - degree of vertex i, d(G) - total degree of graph
     */
    private void addNewVertices() {
        Random random = getRandomGenerator();
        int startVerticesCount = graph.getVertexCount();
        while (graph.getVertexCount() < startVerticesCount + verticesAddedAtEachStep) {
            int addedVertex = graph.addVertex();
            for (int i = 0; i < addedVertex; i++) {
                float edgeProbabilty = random.nextFloat();
                if (edgeProbabilty < graph.getVertexDegree(i) / graphDegreeSum) {
                    graph.addEdge(i, addedVertex);
                    graphDegreeSum += 2;
                }
            }
        }
    }

}
