package assign07;

import java.util.ArrayList;
import java.util.List;

public class TimingExperiment {

    public static void main(String[] args) {
        int startN = 100;
        int endN = 1000;
        int stepSize = 100;

        System.out.println("N\tAreConnected(ms)\tShortestPath(ms)\tSort(ms)");

        for (int N = startN; N <= endN; N += stepSize) {
            Graph<String> graph = createGraph(N);

            long areConnectedTime = measureAreConnected(graph);
            long shortestPathTime = measureShortestPath(graph);
            long sortTime = measureSort(graph);

            System.out.println(N + "\t" + areConnectedTime + "\t" + shortestPathTime + "\t" + sortTime);
        }
    }

    private static Graph<String> createGraph(int N) {
        Graph<String> graph = new Graph<>();
        ArrayList<String> sources = new ArrayList<>();
        ArrayList<String> destinations = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String source = "Node" + i;
            String destination = "Node" + (i + 1);

            sources.add(source);
            destinations.add(destination);

            graph.addEdge(source, destination);
        }

        return graph;
    }

    private static long measureAreConnected(Graph<String> graph) {
        String srcData = "Node0";
        String dstData = "Node" + (graph.sortVertices().size() - 1);
        long startTime = System.nanoTime();
        boolean areConnected = GraphUtility.areConnected(graph, srcData, dstData);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private static long measureShortestPath(Graph<String> graph) {
        String srcData = "Node0";
        String dstData = "Node" + (graph.sortVertices().size() - 1);
        long startTime = System.nanoTime();
        List<String> shortestPath = GraphUtility.shortestPath(graph, srcData, dstData);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private static long measureSort(Graph<String> graph) {
        long startTime = System.nanoTime();
        List<String> sortedVertices = GraphUtility.sort(graph);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }
}
