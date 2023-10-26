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
 * @author Prof. Parker & ??
 * @version October 19, 2023
 */
public class GraphUtility {
    public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)
        throws IllegalArgumentException {
    Graph<Type> graph = new Graph<>();
    
    if (sources.size() != destinations.size()) {
        throw new IllegalArgumentException("Invalid input: sources and destinations must have the same size.");
    }
    
    for (int i = 0; i < sources.size(); i++) {
        Vertex<Type> srcVertex = new Vertex<>(sources.get(i));
        Vertex<Type> dstVertex = new Vertex<>(destinations.get(i));
        graph.addEdge(srcVertex, dstVertex);
    }
    if (!graph.areConnected(srcData, dstData)){
        throw new IllegalArgumentException("The two vertexes are not connected");   
    }
    return graph.areConnected(srcData, dstData);
}


    public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData,
            Type dstData) throws IllegalArgumentException {
                Graph<Type> graph = new Graph<>();
    
    if (sources.size() != destinations.size()) {
        throw new IllegalArgumentException("Invalid input: sources and destinations must have the same size.");
    }
    
    for (int i = 0; i < sources.size(); i++) {
        Vertex<Type> srcVertex = new Vertex<>(sources.get(i));
        Vertex<Type> dstVertex = new Vertex<>(destinations.get(i));
        graph.addEdge(srcVertex, dstVertex);
    }
    
    return graph.shortestPath(srcData, dstData);
            }
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