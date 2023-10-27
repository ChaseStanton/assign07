package assign07;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class timing {
    public static Graph<Integer> generateSparseGraph(int N) {
        Graph<Integer> graph = new Graph<>();
    
        // Create vertices
        for (int i = 1; i <= N; i++) {
            graph.addEdge(i, (i % N) + 1);
        }
    
        return graph;
    }
    


    public static long measureAreConnected(Graph<Integer> graph, int srcData, int dstData) {
        long startTime = System.nanoTime();
        boolean connected = graph.areConnected(srcData, dstData);
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        return executionTime;
    }

    public static long measureShortestPath(Graph<Integer> graph, int srcData, int dstData) {
        long startTime = System.nanoTime();
        List<Integer> shortestPath = graph.shortestPath(srcData, dstData);
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        return executionTime;
    }

    public static long measureSort(Graph<Integer> graph) {
        long startTime = System.nanoTime();
        List<Integer> sortedVertices = GraphUtility.sort(new ArrayList<>(graph.getVertices().keySet()),
                new ArrayList<>(graph.getVertices().keySet()));
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        return executionTime;
    }
    public static void main(String[] args) {
        int[] nValues = { 10, 20, 30, 40, 50 };

        System.out.println("Timing Experiment Results:");
        System.out.println("N\tareConnected Time (ns)\tshortestPath Time (ns)\tsort Time (ns)");

        for (int N : nValues) {
            Graph<Integer> graph = generateSparseGraph(N);
            int srcData = 1;
            int dstData = N;

            // Measure areConnected method execution time
            long areConnectedTime = measureAreConnected(graph, srcData, dstData);

            // Measure shortestPath method execution time
            long shortestPathTime = measureShortestPath(graph, srcData, dstData);

            // Measure sort method execution time
            long sortTime = measureSort(graph);

            System.out.println(N + "\t" + areConnectedTime + "\t\t\t" + shortestPathTime + "\t\t\t" + sortTime);
        }
    }
}
