package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains several methods for solving problems on generic, directed,
 * unweighted, sparse graphs.
 * 
 * @author Prof. Parker & Reece Kalmar & Chase Stanton
 * @version October 27, 2023
 */
public class GraphUtility {
    /**
     * Checks if there is a path between two vertices in the given graph.
     *
     * @param sources      a list of source vertices
     * @param destinations a list of destination vertices
     * @param srcData      the source vertex to check
     * @param dstData      the destination vertex to check
     * @param <Type>       the data type of the vertices in the graph
     * @return true if there is a path, false otherwise
     * @throws IllegalArgumentException if the sources and destinations lists have
     *                                  different sizes
     */
    public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)
            throws IllegalArgumentException {
        if (sources.size() != destinations.size()) {
            throw new IllegalArgumentException("Sources list and destination list must be the same size");
        }

        Graph<Type> graph = buildGraphFromLists(sources, destinations);
        return graph.areConnected(srcData, dstData);
    }

    /**
     * Finds the shortest path between two vertices in the given graph.
     *
     * @param sources      a list of source vertices
     * @param destinations a list of destination vertices
     * @param srcData      the source vertex for which to find the shortest path
     * @param dstData      the destination vertex for which to find the shortest
     *                     path
     * @param <Type>       the data type of the vertices in the graph
     * @return a list representing the shortest path
     * @throws IllegalArgumentException if the sources and destinations lists have
     *                                  different sizes
     */
    public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData,
            Type dstData) throws IllegalArgumentException {
        if (sources.size() != destinations.size()) {
            throw new IllegalArgumentException("Sources list and destination list must be the same size");
        }
        Graph<Type> graph = buildGraphFromLists(sources, destinations);
        return graph.shortestPath(srcData, dstData);
    }

    /**
     * Performs topological sorting on the vertices of the given graph.
     *
     * @param sources      a list of source vertices
     * @param destinations a list of destination vertices
     * @param <Type>       the data type of the vertices in the graph
     * @return a list representing the sorted vertices
     * @throws IllegalArgumentException if the sources and destinations lists have
     *                                  different sizes
     */
    public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
        if (sources.size() != destinations.size()) {
            throw new IllegalArgumentException("Sources list and destination list must be the same size");
        }
        Graph<Type> graph = buildGraphFromLists(sources, destinations);
        return graph.sortVertices();
    }

    /**
     * Builds a graph based on the provided lists of source and destination
     * vertices.
     *
     * @param sources      a list of source vertices
     * @param destinations a list of destination vertices
     * @param <Type>       the data type of the vertices in the graph
     * @return a graph constructed from the provided vertex lists
     */
    private static <Type> Graph<Type> buildGraphFromLists(List<Type> sources, List<Type> destinations) {
        Graph<Type> graph = new Graph<>();

        for (int i = 0; i < sources.size(); i++) {
            Type source = sources.get(i);
            Type destination = destinations.get(i);
            graph.addEdge(source, destination);
        }

        return graph;
    }

    /**
     * Builds "sources" and "destinations" lists according to the edges specified in
     * the given DOT file (e.g., "a -> b").
     * Assumes that the vertex data type is String.
     * 
     * Accepts many valid "digraph" DOT files (see examples posted on Canvas).
     * - Accepts \\-style comments
     * - Accepts one edge per line or edges terminated with ;
     * - Does not accept attributes in [] (e.g., [label = "a label"])
     *
     * @param filename     the name of the DOT file
     * @param sources      an empty ArrayList; when the method returns, it is a
     *                     valid "sources" list that can be passed
     *                     to the public methods in this class
     * @param destinations an empty ArrayList; when the method returns, it is a
     *                     valid "destinations" list that can be
     *                     passed to the public methods in this class
     */
    public static void buildListsFromDot(String filename, ArrayList<String> sources, ArrayList<String> destinations) {

        Scanner scan = null;
        try {
            scan = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        scan.useDelimiter(";|\n");

        // Determine if the graph is directed (i.e., look for "digraph id {").
        String line = "", edgeOp = "";
        while (scan.hasNext()) {
            line = scan.next();

            // Skip //-style comments.
            line = line.replaceFirst("//.*", "");

            if (line.indexOf("digraph") >= 0) {
                edgeOp = "->";
                line = line.replaceFirst(".*\\{", "");
                break;
            }
        }
        if (edgeOp.equals("")) {
            System.out.println("DOT graph must be directed (i.e., digraph).");
            scan.close();
            System.exit(0);
        }

        // Look for the edge operator -> and determine the source and destination
        // vertices for each edge.
        while (scan.hasNext()) {
            String[] substring = line.split(edgeOp);

            for (int i = 0; i < substring.length - 1; i += 2) {
                // Remove " and trim whitespace from node string on the left
                String vertex1 = substring[0].replace("\"", "").trim();
                // If the string is empty, try again
                if (vertex1.equals(""))
                    continue;

                // Do the same for the node string on the right
                String vertex2 = substring[1].replace("\"", "").trim();
                if (vertex2.equals(""))
                    continue;

                // Indicate an edge between vertex1 and vertex2
                sources.add(vertex1);
                destinations.add(vertex2);
            }

            // Do this until the "}" has been read
            if (substring[substring.length - 1].indexOf("}") >= 0)
                break;

            line = scan.next();

            // Skip //-style comments.
            line = line.replaceFirst("//.*", "");
        }

        scan.close();
    }
}
