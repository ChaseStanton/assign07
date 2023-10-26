package assign07;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Graph<Type> {
    private List<Vertex<Type>> sourceNodes;
    private List<Vertex<Type>> destinationNodes;
    private List<Edge<Type>> edges;

    public Graph() {
        this.sourceNodes = new ArrayList<>();
        this.destinationNodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addEdge(Vertex<Type> source, Vertex<Type> destination) {
        sourceNodes.add(source);
        destinationNodes.add(destination);
        Edge<Type> edge = new Edge<>(source, destination);
        edges.add(edge);
    }

    public boolean areConnected(Type srcData, Type dstData) {
        Vertex<Type> src = findVertex(srcData);
        Vertex<Type> dst = findVertex(dstData);

        if (src == null || dst == null) {
            throw new IllegalArgumentException("No such vertices in the graph.");
        }

        Set<Vertex<Type>> visited = new HashSet<>();

        return depthFirstSearch(src, dst, visited);
    }

    private boolean depthFirstSearch(Vertex<Type> current, Vertex<Type> target, Set<Vertex<Type>> visited) {
        if (current == target) {
            return true;
        }

        visited.add(current);

        for (Edge<Type> edge : edges) {
            if (edge.getSRC() == current) {
                Vertex<Type> neighbor = edge.getDST();
                if (!visited.contains(neighbor)) {
                    if (depthFirstSearch(neighbor, target, visited)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private Vertex<Type> findVertex(Type data) {
        for (Vertex<Type> vertex : sourceNodes) {
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        for (Vertex<Type> vertex : destinationNodes) {
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        return null;
    }
    public List<Type> shortestPath(Type srcData, Type dstData) {
        Vertex<Type> src = findVertex(srcData);
        Vertex<Type> dst = findVertex(dstData);
    
        if (src == null || dst == null) {
            throw new IllegalArgumentException("No such vertices in the graph.");
        }
    
        Queue<Vertex<Type>> queue = new LinkedList<>();
        int[] parent = new int[sourceNodes.size()];
    
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
    
        int srcIndex = sourceNodes.indexOf(src);
        queue.add(src);
        parent[srcIndex] = srcIndex;
    
        while (!queue.isEmpty()) {
            Vertex<Type> current = queue.poll();
            int currentIndex = sourceNodes.indexOf(current);
    
            if (current == dst) {
                return reconstructShortestPathData(parent, srcIndex, currentIndex);
            }
    
            for (int i = 0; i < edges.size(); i++) {
                Edge<Type> edge = edges.get(i);
                if (edge.getSRC() == current) {
                    Vertex<Type> neighbor = edge.getDST();
                    int neighborIndex = sourceNodes.indexOf(neighbor);
                    if (parent[neighborIndex] == -1) {
                        queue.add(neighbor);
                        parent[neighborIndex] = currentIndex;
                    }
                }
            }
        }
    
        throw new IllegalArgumentException("No path between the two vertices.");
    }
    
    private List<Type> reconstructShortestPathData(int[] parent, int srcIndex, int dstIndex) {
        List<Type> path = new ArrayList<>();
        int currentIndex = dstIndex;
    
        while (currentIndex != srcIndex) {
            path.add(0, sourceNodes.get(currentIndex).getData());
            currentIndex = parent[currentIndex];
        }
    
        path.add(0, sourceNodes.get(srcIndex).getData());
        return path;
    }
    

    public static <Type> List<Type> topologicalSort(List<Type> verticesList, List<List<Type>> graph,
            List<Integer> inDegrees) {
        Queue<Type> queue = new LinkedList<>();
        List<Type> result = new ArrayList<>();

        // Enqueue vertices with in-degree 0
        for (int i = 0; i < inDegrees.size(); i++) {
            if (inDegrees.get(i) == 0) {
                queue.offer(verticesList.get(i));
            }
        }

        // While the queue isn't empty, dequeue the queue.
        while (!queue.isEmpty()) {
            Type vertex = queue.poll();
            result.add(vertex);

            // Update in-degrees of adjacent vertices
            int vertexIndex = verticesList.indexOf(vertex);
            for (Type next : graph.get(vertexIndex)) {
                int nextIndex = verticesList.indexOf(next);
                inDegrees.set(nextIndex, inDegrees.get(nextIndex) - 1);
                if (inDegrees.get(nextIndex) == 0) {
                    queue.offer(next);
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Edge<Type> edge : edges) {
            stringBuilder.append(edge.getSRC().getData()).append(" -> ").append(edge.getDST().getData()).append("\n");
        }
        return stringBuilder.toString();
    }
}
