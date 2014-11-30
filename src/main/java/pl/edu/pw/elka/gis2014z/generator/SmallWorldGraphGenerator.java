package pl.edu.pw.elka.gis2014z.generator;

import com.google.common.collect.Range;
import pl.edu.pw.elka.gis2014z.graph.Graph;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Generator of small world network
 */
public class SmallWorldGraphGenerator extends AbstractGraphGenerator {

    /**
     * Number of vertices
     */
    private final int verticesCount;

    /**
     * Inital graph level (how many edges has every vertex at the begining)
     */
    private final int initialGraphLevel;

    /**
     * Probabilty of edge change
     */
    private final float probabilityOfEdgeChange;

    /**
     * Creates generator
     * @param verticesCount {@link #verticesCount}
     * @param initialGraphLevel {@link #initialGraphLevel}
     * @param probabilityOfEdgeChange {@link #probabilityOfEdgeChange}
     * @throws java.lang.IllegalArgumentException
     * <ul>
     *     <li>vertices count < 0</li>
     *     <li>initialGraphLevel < 0 or >= verticesCount</li>
     *     <li>initialGraphLevel is odd</li>
     *     <li>probabilityOfEdgeChange is not in range [0, 1]</li>
     * </ul>
     */
    public SmallWorldGraphGenerator(int verticesCount, int initialGraphLevel, float probabilityOfEdgeChange) {
        checkArgument(verticesCount >= 0);
        checkArgument(initialGraphLevel >= 0 && initialGraphLevel <= verticesCount - 1);
        checkArgument(initialGraphLevel % 2 == 0);
        checkArgument(Range.closed(0.0f, 1.0f).contains(probabilityOfEdgeChange));

        this.verticesCount = verticesCount;
        this.initialGraphLevel = initialGraphLevel;
        this.probabilityOfEdgeChange = probabilityOfEdgeChange;
    }


    @Override
    protected void doGenerate() {
        checkState(graph == null);

        graph = new Graph(verticesCount);

        generateInitialEdges();
        randomizeEdges();
    }

    /**
     * Generates regular graph edges
     */
    private void generateInitialEdges() {
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 1; j <= initialGraphLevel / 2; j++) {
                graph.addEdge(i, (i + j) % verticesCount);
            }
        }
    }

    /**
     * Randomizes edges of graph with probablity {@link #probabilityOfEdgeChange}
     */
    private void randomizeEdges() {
        Random random = new Random();
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 1; j <= initialGraphLevel / 2; j++) {
                float changeProb = random.nextFloat();
                if (changeProb > probabilityOfEdgeChange) {
                    continue;
                }

                randomizeEdge(random, i, (i + j) % verticesCount);
            }
        }
    }

    /**
     * Randomizes edges between vertices
     * @param random random number generators
     * @param firstVertex firstVertex number
     * @param secondVertex secondVertexNumber
     */
    private void randomizeEdge(Random random, int firstVertex, int secondVertex) {
        graph.removeEdge(firstVertex, secondVertex);

        int newSecondVertex = random.nextInt(verticesCount);
        if (firstVertex == newSecondVertex
                || graph.hasEdge(firstVertex, newSecondVertex)) {
            newSecondVertex = random.nextInt(verticesCount);
        }

        graph.addEdge(firstVertex, newSecondVertex);
    }

}
