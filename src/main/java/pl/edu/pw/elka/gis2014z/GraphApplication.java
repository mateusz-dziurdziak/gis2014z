package pl.edu.pw.elka.gis2014z;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import pl.edu.pw.elka.gis2014z.algorithm.IsCompact;
import pl.edu.pw.elka.gis2014z.generator.AbstractGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.EuclideanGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.GraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.RandomGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.ScaleFreeGraphGenerator;
import pl.edu.pw.elka.gis2014z.generator.SmallWorldGraphGenerator;
import pl.edu.pw.elka.gis2014z.graph.Graph;
import pl.edu.pw.elka.gis2014z.writer.JpgWriter;
import pl.edu.pw.elka.gis2014z.writer.TextWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GraphApplication {

    public static void main(String[] args) throws IOException {
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
                generateGraphs(line);
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

    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption(new Option("h", "displays help"));
        options.addOption(new Option("type", true, "graphType to generate. Allowed are: r(random), sw(smallWorld), sf(scaleFree), e(euclidean)"));
        options.addOption(new Option("v", true, "number of vertices"));
        options.addOption(new Option("e", true, "initial number of edges in scale free network"));
        options.addOption(new Option("m", true, "degree of added vertex in scale free network"));
        options.addOption(new Option("sv", true, "number of vertices added at each step of scale free graph generation"));
        options.addOption(new Option("lvl", true, "initial graph level in small world graph (used in euclidean graph)"));
        options.addOption(new Option("p", true, "probability of edge(used in random graph)"));
        options.addOption(new Option("pc", true, "probability of edge change (used in small world graph)"));
        options.addOption(new Option("r", true, "radius (used in euclidean graph)"));
        options.addOption(new Option("f", true, "output files prefix"));
        return options;
    }

    private static void generateGraphs(CommandLine line) throws IOException {
        String graphType = line.getOptionValue("type");
        GraphGenerator generator;
        int v = Integer.parseInt(line.getOptionValue("v"));
        switch (graphType) {
            case "r":
                float p = Float.parseFloat(line.getOptionValue("p"));
                generator = new RandomGraphGenerator(v, p);
                break;
            case "sw":
                int lvl = Integer.parseInt(line.getOptionValue("lvl"));
                float pc = Float.parseFloat(line.getOptionValue("pc"));
                generator = new SmallWorldGraphGenerator(v, lvl, pc);
                break;
            case "sf":
                int e = Integer.parseInt(line.getOptionValue("e"));
                int sv = Integer.parseInt(line.getOptionValue("sv"));
                int m = Integer.parseInt(line.getOptionValue("m"));
                generator = new ScaleFreeGraphGenerator(v, e, m, sv);
                break;
            case "e":
                float r = Float.parseFloat(line.getOptionValue("r"));
                generator = new EuclideanGraphGenerator(v, r);
                break;
            default:
                throw new IllegalArgumentException("Something went wrong.");
        }

        generator.generate();

        Graph graph = generator.getGraph();

        if (graphType.equals("sf")) {
            while (readBoolean("Czy chcesz przeprowadzić kolejna iteracje")) {
                generator.generate();
                System.out.println("Generowanie zakonczone.");
            }
        }

        if (new IsCompact().apply(graph)) {
            System.out.println("Graf jest spojny.");
        } else {
            System.out.println("Graf nie jest spojny.");
        }

        writeTextFiles(line, (AbstractGraphGenerator) generator, graph);
        writeToJpgFile(line, graph);
    }

    private static void writeTextFiles(CommandLine line, AbstractGraphGenerator generator, Graph graph) {
        try (OutputStream os = Files.newOutputStream(Paths.get("./" + line.getOptionValue("f") + "_generator.txt"))) {
            TextWriter.writeGenerationInfoToStream((AbstractGraphGenerator) generator, os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream os = Files.newOutputStream((Paths.get("./" + line.getOptionValue("f") + "_graph.txt")))) {
            TextWriter.writeGraphInfoToStream(graph, os);
            TextWriter.writeGraphToStream(graph, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToJpgFile(CommandLine line, Graph graph) {
        try {
            JpgWriter.writeGraphicGraphToFile(graph,
                    Paths.get("./" + line.getOptionValue("f") + "_picture.png").toFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean readBoolean(String question) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println(question);
            System.out.println("Wpisz T/N");
            String line = br.readLine();
            line = line.toUpperCase();

            if ("T".equals(line.trim())) {
                return true;
            } else if ("N".equals(line.trim())) {
                return false;
            }
            System.out.println("Spróbuj ponownie");
        } while (true);
    }

}
