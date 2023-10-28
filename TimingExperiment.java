package assign07;

import java.util.ArrayList;
import java.util.List;

public class TimingExperiment {

    public static void main(String[] args) {
        int startN = 10;
        int endN = 100;
        int stepSize = 10;

        System.out.println("N\tAreConnected(ns)\tShortestPath(ns)\tSort(ns)");

        for (int N = startN; N <= endN; N += stepSize) {
            ArrayList<String> sources = new ArrayList<>();
            ArrayList<String> destinations = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                String source = "Node" + i;
                String destination = "Node" + (i + 1);

                sources.add(source);
                destinations.add(destination);

            }
            String srcData = "Node0";
            String dstData = "Node" + (GraphUtility.sort(sources, destinations).size() - 1);

            long startTime1 = System.nanoTime();
            GraphUtility.areConnected(sources, destinations, srcData, dstData);
            long stopTime1 = System.nanoTime();

            long areConnectedTime = stopTime1 - startTime1;

            long startTime2 = System.nanoTime();
            GraphUtility.shortestPath(sources, destinations, srcData, dstData);
            long stopTime2 = System.nanoTime();

            long shortestPathTime = stopTime2 - startTime2;

            long startTime3 = System.nanoTime();
            GraphUtility.sort(sources, destinations);
            long stopTime3 = System.nanoTime();

            long sortTime = stopTime3 - startTime3;

            System.out.println(N + "\t" + areConnectedTime + "\t" + shortestPathTime + "\t" + sortTime);
        }
    }

}
