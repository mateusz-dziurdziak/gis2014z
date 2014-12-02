package pl.edu.pw.elka.gis2014z.timer;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Timer of graph operations
 */
public class GraphTimer {

    private List<Long> generationsTimes = new ArrayList<Long>();

    private long startTime = -1L;

    /**
     * Starts measuring of generation time
     * @throws java.lang.IllegalStateException if measuring of generation time was already started
     */
    public void generationStart() {
        checkState(startTime == -1L);

        startTime = System.currentTimeMillis();
    }

    /**
     * Ends measuring of current generation
     * @throws java.lang.IllegalStateException if no generation was activated in timer
     */
    public void generationEnd() {
        checkState(startTime != -1L);

        generationsTimes.add(System.currentTimeMillis() - startTime);

        startTime = -1L;
    }

    /**
     * Returns list of milliseconds per generations
     * @return list of milliseconds per generations
     */
    public List<Long> getGenerationsTimes() {
        return ImmutableList.copyOf(generationsTimes);
    }

}
