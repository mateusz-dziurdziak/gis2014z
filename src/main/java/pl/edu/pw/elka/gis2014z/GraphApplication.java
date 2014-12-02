package pl.edu.pw.elka.gis2014z;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import pl.edu.pw.elka.gis2014z.generator.AbstractGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.EuclideanGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.GraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.RandomGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.ScaleFreeGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.SmallWorldGraphGenerator;
import pl.edu.pw.elka.gis2014z.graph.Graph;
import pl.edu.pw.elka.gis2014z.writer.TextWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GraphApplication {

    public static void main(String[] args) {
        Options options = buildOptions();
        CommandLineParser parser = new BasicParser();

        CommandLine line;
        try {
            line = parser.parse(options, args);

            if (line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("generator", options);
            } else {
               new Validator(line).validateOptions();
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
            return;
        } catch (Validator.ValidationException e) {
            System.err.println(e.getMessage());

            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("generator", options);
            return;
        }

        generateGraphs(line);
    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption(new Option("h", "displays help"));
        options.addOption(new Option("type", true, "graphType to generate. Allowed are: r(random), sw(smallWorld), sf(scaleFree), e(euclidean)"));
        options.addOption(new Option("v", true, "number of vertices"));
        options.addOption(new Option("e", true, "initial number of edges in scale free network"));
        options.addOption(new Option("sv", true, "number of vertices added at each step of scale free graph generation"));
        options.addOption(new Option("lvl", true, "initial graph level in small world graph (used in euclidean graph)"));
        options.addOption(new Option("p", true, "probability of edge(used in random graph)"));
        options.addOption(new Option("pc", true, "probability of edge change (used in small world graph)"));
        options.addOption(new Option("r", true, "radius (used in euclidean graph)"));
        options.addOption(new Option("f", true, "output files prefix"));
        return options;
    }

    private static void generateGraphs(CommandLine line) {
        String graphType = line.getOptionValue("type");
        GraphGenerator generator;
        int v = Integer.parseInt(line.getOptionValue("v"));
        switch (graphType) {
            case "r" :
                float p = Float.parseFloat(line.getOptionValue("p"));
                generator = new RandomGraphGenerator(v, p);
                break;
            case "sw" :
                int lvl = Integer.parseInt(line.getOptionValue("lvl"));
                float pc = Float.parseFloat(line.getOptionValue("pc"));
                generator = new SmallWorldGraphGenerator(v, lvl, pc);
                break;
            case "sf" :
                int e = Integer.parseInt(line.getOptionValue("e"));
                int sv = Integer.parseInt(line.getOptionValue("sv"));
                generator = new ScaleFreeGraphGenerator(v, e, sv);
                break;
            case "e" :
                float r = Float.parseFloat(line.getOptionValue("r"));
                generator = new EuclideanGraphGenerator(v, r);
                break;
            default:
                throw new IllegalArgumentException("Something went wrong.");
        }

        generator.generate();

        Graph graph = generator.getGraph();

        writeTextFiles(line, (AbstractGraphGenerator) generator, graph);
    }

    private static void writeTextFiles(CommandLine line, AbstractGraphGenerator generator, Graph graph) {
        try (OutputStream os = Files.newOutputStream(Paths.get("./" + line.getOptionValue("f") + "_generator"))) {
            TextWriter.writeGenerationInfoToStream((AbstractGraphGenerator) generator, os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream os = Files.newOutputStream((Paths.get("./" + line.getOptionValue("f") + "_graph")))) {
            TextWriter.writeGraphInfoToStream(graph, os);
            TextWriter.writeGraphToStream(graph, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
