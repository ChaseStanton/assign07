package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains several methods for solving problems on generic, directed,
 * unweighted,
 * sparse graphs.
 *
 * @author Prof. Parker & Chase Stanton & Reece Kalmar
 * @version October 19, 2023
 */
public class GraphUtility {
    /**
     * This method is a driver method that uses depth first search to determine if
     * there is a path from the vertex with srcData to
     * the vertex with dstData
     * 
     * @param <Type>       - Generic
     * @param sources      - The list of sources
     * @param destinations - The list of destinations
     * @param srcData      - The data from the source vertex
     * @param dstData      - the data from the destination vertex
     * @return true if there is a path, false if there isn't
     * @throws IllegalArgumentException if the source list and destination list
     *                                  don't have the same size
     */
    public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)
            throws IllegalArgumentException {
        Graph<Type> graph = new Graph<>();

        if (sources.size() != destinations.size()) {
            throw new IllegalArgumentException("Invalid input: sources and destinations must have the same size.");
        }

        for (int i = 0; i < sources.size(); i++) {
            graph.addEdge(sources.get(i), destinations.get(i));
        }

        return graph.areConnected(srcData, dstData);
    }

    /**
     * This method is a driver method that uses breadth first search to find the
     * shortest path from the vertex with srcData to the
     * vertex with dstData
     * 
     * @param <Type>       - Generic
     * @param sources      - The list of sources
     * @param destinations - The list of destinations
     * @param srcData      - The data from the source vertex
     * @param dstData      - The data from the destination vertex
     * @return the shortest path from the two vertexes
     * @throws IllegalArgumentException if sources and destinations are not the same
     *                                  size
     */
    public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData,
            Type dstData) throws IllegalArgumentException {
        Graph<Type> graph = new Graph<>();

        if (sources.size() != destinations.size()) {
            throw new IllegalArgumentException("Invalid input: sources and destinations must have the same size.");
        }

        for (int i = 0; i < sources.size(); i++) {
            graph.addEdge(sources.get(i), destinations.get(i));
        }

        return graph.shortestPath(srcData, dstData);
    }

    /**
     * This method is a driver method that uses topologicalSort to generate a sorted
     * ordering of the vertices in the graph
     * 
     * @param <Type>       - Generic
     * @param sources      - The list of sources
     * @param destinations - The list of destinations
     * @return the sorted list
     * @throws IllegalArgumentException if the graph contains a cycle
     */
    public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
        List<Type> vertices = new ArrayList<>();
        List<List<Type>> graph = new ArrayList<>();
        List<Integer> inDegrees = new ArrayList<>();

        makeGraph(sources, destinations, vertices, graph, inDegrees);

        List<Type> result = Graph.topologicalSort(vertices, graph, inDegrees);
        if (result.size() != vertices.size())
            throw new IllegalArgumentException("The graph cannot contain a cycle");
        return result;

    }

    /**
     * This method makes a graph that is used in the sort method
     * 
     * @param <Type>       - Generic
     * @param sources      - The list of sources
     * @param destinations - The list of destinations
     * @param vertices     - The list of vertices
     * @param graph        - The graph to be added to
     * @param inDegrees    - The indegrees of the list
     */
    private static <Type> void makeGraph(List<Type> sources, List<Type> destinations, List<Type> vertices,
            List<List<Type>> graph, List<Integer> inDegrees) {
        for (int i = 0; i < sources.size(); i++) {
            Type source = sources.get(i);
            Type destination = destinations.get(i);

            if (!vertices.contains(source)) {
                vertices.add(source);
                inDegrees.add(0);
                graph.add(new ArrayList<>());
            }
            if (!vertices.contains(destination)) {
                vertices.add(destination);
                inDegrees.add(0);
                graph.add(new ArrayList<>());
            }

            int sourceIndex = vertices.indexOf(source);
            int destinationIndex = vertices.indexOf(destination);
            inDegrees.set(destinationIndex, inDegrees.get(destinationIndex) + 1);
            graph.get(sourceIndex).add(destination);
        }
    }

    /**
     * Builds "sources" and "destinations" lists according to the edges
     * specified in the given DOT file (e.g., "a -> b"). Assumes that the vertex
     * data type is String.
     *
     * Accepts many valid "digraph" DOT files (see examples posted on Canvas).
     * --accepts \\-style comments
     * --accepts one edge per line or edges terminated with ;
     * --does not accept attributes in [] (e.g., [label = "a label"])
     *
     * @param filename     - name of the DOT file
     * @param sources      - empty ArrayList, when method returns it is a valid
     *                     "sources" list that can be passed to the public methods
     *                     in this
     *                     class
     * @param destinations - empty ArrayList, when method returns it is a valid
     *                     "destinations" list that can be passed to the public
     *                     methods in
     *                     this class
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
        // Determine if graph is directed (i.e., look for "digraph id {").
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
            System.out.println("DOT graph must be directed (i.e.,digraph).");
            scan.close();
            System.exit(0);
        }
        // Look for edge operator -> and determine the source and destination
        // vertices for each edge.
        while (scan.hasNext()) {
            String[] substring = line.split(edgeOp);
            for (int i = 0; i < substring.length - 1; i += 2) {
                // remove " and trim whitespace from node string on the left
                String vertex1 = substring[0].replace("\"", "").trim();
                // if string is empty, try again
                if (vertex1.equals(""))
                    continue;
                // do the same for the node string on the right
                String vertex2 = substring[1].replace("\"", "").trim();
                if (vertex2.equals(""))
                    continue;
                // indicate edge between vertex1 and vertex2
                sources.add(vertex1);
                destinations.add(vertex2);
            }
            // do until the "}" has been read
            if (substring[substring.length - 1].indexOf("}") >= 0)
                break;
            line = scan.next();
            // Skip //-style comments.
            line = line.replaceFirst("//.*", "");
        }
        scan.close();
    }
}