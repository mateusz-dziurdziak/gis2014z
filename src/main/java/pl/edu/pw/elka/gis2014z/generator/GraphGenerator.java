package pl.edu.pw.elka.gis2014z.generator;

import pl.edu.pw.elka.gis2014z.graph.Graph;

/**
 * Graph generator interface
 */
public interface GraphGenerator {

    /**
     * Generates graph. In some generator can be called multiple times.
     * @throws java.lang.IllegalStateException in case of second generate call if generator doesn't support
     * multiple generate() calls
     */
    void generate();

    /**
     * Returns generated graph
     * @return generated graph
     * @throws java.lang.IllegalStateException if graph was not generated
     */
    Graph getGraph();
}
