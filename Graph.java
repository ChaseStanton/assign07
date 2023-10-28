package assign07;

import java.util.*;

/**
 * Represents a generic directed graph and provides various operations and
 * algorithms for working with the graph.
 *
 * @param <Type> the data type of the vertices in the graph
 */
class Graph<Type> {
    private Map<Type, List<Type>> adjList;

    /**
     * Constructs an empty graph.
     */
    public Graph() {
        adjList = new HashMap<>();
    }

    /**
     * Adds a directed edge from the source vertex to the destination vertex.
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     */
    public void addEdge(Type source, Type destination) {
        adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
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
        if (!adjList.containsKey(srcData) || !adjList.containsKey(dstData)) {
            throw new IllegalArgumentException("Vertex not found in the graph");
        }

        Set<Type> visited = new HashSet<>();
        return dfs(srcData, dstData, visited);
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
    private boolean dfs(Type currentVertex, Type targetVertex, Set<Type> visited) {
        if (currentVertex.equals(targetVertex)) {
            return true;
        }

        visited.add(currentVertex);
        List<Type> neighbors = adjList.get(currentVertex);

        if (neighbors != null) {
            for (Type neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    if (dfs(neighbor, targetVertex, visited)) {
                        return true;
                    }
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

        Queue<Type> queue = new LinkedList<>();
        Map<Type, Type> previousVertex = new HashMap<>();

        queue.add(srcData);
        previousVertex.put(srcData, null);

        while (!queue.isEmpty()) {
            Type currentVertex = queue.poll();

            if (currentVertex.equals(dstData)) {
                List<Type> shortestPath = new ArrayList<>();
                Type vertex = dstData;

                while (vertex != null) {
                    shortestPath.add(vertex);
                    vertex = previousVertex.get(vertex);
                }

                Collections.reverse(shortestPath);
                return shortestPath;
            }

            List<Type> neighbors = adjList.get(currentVertex);

            if (neighbors != null) {
                for (Type neighbor : neighbors) {
                    if (!previousVertex.containsKey(neighbor)) {
                        previousVertex.put(neighbor, currentVertex);
                        queue.add(neighbor);
                    }
                }
            }
        }

        throw new IllegalArgumentException("No path found between the two vertices");
    }

    /**
     * Performs topological sorting on the vertices of the graph.
     *
     * @return a list representing the sorted vertices
     * @throws IllegalArgumentException if the graph contains a cycle
     */
    public List<Type> sortVertices() {
        int numVertices = adjList.size();
        int[] inDegrees = new int[numVertices];
        Queue<Type> queue = new LinkedList<>();
        List<Type> sortedVertices = new ArrayList<>();
    
        // Calculate in-degrees for all vertices
        for (List<Type> neighbors : adjList.values()) {
            for (Type neighbor : neighbors) {
                int neighborIndex = getIndex(neighbor);
                if (neighborIndex != -1) {
                    inDegrees[neighborIndex]++;
                }
            }
        }
    
        // Enqueue vertices with in-degrees 0
        for (Type vertex : adjList.keySet()) {
            int vertexIndex = getIndex(vertex);
            if (inDegrees[vertexIndex] == 0) {
                queue.offer(vertex);
            }
        }
    
        while (!queue.isEmpty()) {
            Type vertex = queue.poll();
            sortedVertices.add(vertex);
    
            for (Type neighbor : adjList.get(vertex)) {
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
    private int getIndex(Type vertex) {
        int index = 0;
        for (Type key : adjList.keySet()) {
            if (key.equals(vertex)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
