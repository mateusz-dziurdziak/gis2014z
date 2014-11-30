package pl.edu.pw.elka.gis2014z.generator;

import pl.edu.pw.elka.gis2014z.graph.Graph;
import pl.edu.pw.elka.gis2014z.timer.GraphTimer;

import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkState;

public abstract class AbstractGraphGenerator implements GraphGenerator {

    private GraphTimer graphTimer = new GraphTimer();

    /**
     * Generated graph
     */
    protected Graph graph;

    /**
     * Random number generator
     */
    private Random random;

    @Override
    public final void generate() {
        graphTimer.generationStart();
        doGenerate();
        graphTimer.generationEnd();
    }

    @Override
    public final Graph getGraph() {
        checkState(graph != null);
        return graph;
    }

    /**
     * Returns generations times in nanoseconds
     * @return generations times
     */
    public List<Long> getGenerationsTimes() {
        return graphTimer.getGenerationsTimes();
    }

    protected abstract void doGenerate();

    /**
     * Return random number generator
     * @return random number generator
     */
    protected Random getRandomGenerator() {
        if (random == null) {
            random = new Random();
        }

        return random;
    }

}
