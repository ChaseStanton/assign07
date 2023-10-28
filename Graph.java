package assign07;

import java.util.*;

/**
 * Represents a generic directed graph and provides various operations and
 * algorithms for working with the graph.
 *
 * @param <Type> the data type of the vertices in the graph
 */
class Graph<Type> {
    private Map<Type, Vertex<Type>> vertices;

    /**
     * Constructs an empty graph.
     */
    public Graph() {
        vertices = new HashMap<>();
    }

    /**
     * Adds a directed edge from the source vertex to the destination vertex.
     *
     * @param sourceData      the source vertex
     * @param destinationData the destination vertex
     */
    public void addEdge(Type sourceData, Type destinationData) {
        Vertex<Type> sourceVertex = getOrCreateVertex(sourceData);
        Vertex<Type> destinationVertex = getOrCreateVertex(destinationData);
        sourceVertex.addEdge(destinationVertex);
    }

    // Helper method to get or create a vertex
    private Vertex<Type> getOrCreateVertex(Type data) {
        Vertex<Type> vertex = vertices.get(data);
        if (vertex == null) {
            vertex = new Vertex<>(data);
            vertices.put(data, vertex);
        }
        return vertex;
    }

    /**
     * Checks if there is a path between two vertices in the graph.
     *
     * @param srcData the source vertex
     * @param dstData the destination vertex
     * @return true if there is a path, false otherwise
     * @throws IllegalArgumentException if the source or destination vertex is not
     *                                  found in the graph
     */
    public boolean areConnected(Type srcData, Type dstData) {
        Vertex<Type> srcVertex = vertices.get(srcData);
        Vertex<Type> dstVertex = vertices.get(dstData);

        if (srcVertex == null || dstVertex == null) {
            throw new IllegalArgumentException("Vertex not found in the graph");
        }

        Set<Vertex<Type>> visited = new HashSet<>();
        return dfs(srcVertex, dstVertex, visited);
    }

    /**
     * Checks if there is a path between two vertices in the graph using depth-first
     * search.
     *
     * @param currentVertex the current vertex being visited
     * @param targetVertex  the target vertex to reach
     * @param visited       a set of visited vertices to avoid cycles
     * @return true if there is a path, false otherwise
     */
    private boolean dfs(Vertex<Type> currentVertex, Vertex<Type> targetVertex, Set<Vertex<Type>> visited) {
        if (currentVertex.equals(targetVertex)) {
            return true;
        }

        visited.add(currentVertex);

        for (Vertex<Type> neighbor : currentVertex.getNeighbors()) {
            if (!visited.contains(neighbor)) {
                if (dfs(neighbor, targetVertex, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Finds the shortest path between two vertices in the graph.
     *
     * @param srcData the source vertex
     * @param dstData the destination vertex
     * @return a list representing the shortest path
     * @throws IllegalArgumentException if the source or destination vertex is not
     *                                  found in the graph
     * @throws IllegalArgumentException if no path is found between the two vertices
     */
    public List<Type> shortestPath(Type srcData, Type dstData) {
        Vertex<Type> srcVertex = vertices.get(srcData);
        Vertex<Type> dstVertex = vertices.get(dstData);

        if (srcVertex == null || dstVertex == null) {
            throw new IllegalArgumentException("Vertex not found in the graph");
        }

        Map<Vertex<Type>, Vertex<Type>> previousVertex = new HashMap<>();
        Queue<Vertex<Type>> queue = new LinkedList<>();

        srcVertex.setDistanceFromStart(0);
        queue.add(srcVertex);

        while (!queue.isEmpty()) {
            Vertex<Type> currentVertex = queue.poll();

            if (currentVertex.equals(dstVertex)) {
                return reconstructShortestPath(previousVertex, srcVertex, dstVertex);
            }

            for (Vertex<Type> neighbor : currentVertex.getNeighbors()) {
                int newDistance = currentVertex.getDistanceFromStart() + 1;
                if (newDistance < neighbor.getDistanceFromStart()) {
                    neighbor.setDistanceFromStart(newDistance);
                    previousVertex.put(neighbor, currentVertex);
                    queue.add(neighbor);
                }
            }
        }

        throw new IllegalArgumentException("No path found between the two vertices");
    }

    private List<Type> reconstructShortestPath(Map<Vertex<Type>, Vertex<Type>> previousVertex, Vertex<Type> srcVertex, Vertex<Type> dstVertex) {
        List<Type> shortestPath = new ArrayList<>();
        Vertex<Type> vertex = dstVertex;

        while (vertex != null) {
            shortestPath.add(vertex.getData());
            vertex = previousVertex.get(vertex);
        }

        Collections.reverse(shortestPath);
        return shortestPath;
    }

    /**
     * Performs topological sorting on the vertices of the graph.
     *
     * @return a list representing the sorted vertices
     * @throws IllegalArgumentException if the graph contains a cycle
     */
    public List<Type> sortVertices() {
        int numVertices = vertices.size();
        int[] inDegrees = new int[numVertices];
        Queue<Vertex<Type>> queue = new LinkedList<>();
        List<Type> sortedVertices = new ArrayList<>();

        for (Vertex<Type> vertex : vertices.values()) {
            for (Vertex<Type> neighbor : vertex.getNeighbors()) {
                int neighborIndex = getIndex(neighbor);
                if (neighborIndex != -1) {
                    inDegrees[neighborIndex]++;
                }
            }
        }

        for (Vertex<Type> vertex : vertices.values()) {
            int vertexIndex = getIndex(vertex);
            if (inDegrees[vertexIndex] == 0) {
                queue.offer(vertex);
            }
        }

        while (!queue.isEmpty()) {
            Vertex<Type> vertex = queue.poll();
            sortedVertices.add(vertex.getData());

            for (Vertex<Type> neighbor : vertex.getNeighbors()) {
                int neighborIndex = getIndex(neighbor);
                if (neighborIndex != -1) {
                    inDegrees[neighborIndex]--;
                    if (inDegrees[neighborIndex] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        if (sortedVertices.size() != numVertices) {
            throw new IllegalArgumentException("Graph contains a cycle");
        }

        return sortedVertices;
    }

    /**
     * Helper method to get the index of a vertex in the graph's adjacency list.
     *
     * @param vertex the vertex to find the index of
     * @return the index of the vertex, or -1 if not found
     */
    private int getIndex(Vertex<Type> vertex) {
        int index = 0;
        for (Vertex<Type> v : vertices.values()) {
            if (v.equals(vertex)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}