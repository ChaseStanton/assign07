package assign07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This method represents a graph of a generic Type
 * 
 * @author Chase Stanton and Reece Kalmar
 *
 * @param <Type> - Generic
 */
public class Graph<Type> {
    private HashMap<Type, Vertex<Type>> vertices;

    /**
     * This constructor sets the vertice equal to an empty HashMap
     */
    public Graph() {
        this.vertices = new HashMap<Type, Vertex<Type>>();
    }

    /**
     * This method adds a directed edge to the graph from name1 to name2
     * 
     * @param name1 - The first vertex
     * @param name2 - The second vertex
     */
    public void addEdge(Type name1, Type name2) {
        Vertex<Type> vertex1;
        // if vertex already exists in graph, get its object
        if (vertices.containsKey(name1))
            vertex1 = vertices.get(name1);
        // else, create a new object and add to graph
        else {
            vertex1 = new Vertex<Type>(name1);
            vertices.put(name1, vertex1);
        }

        Vertex<Type> vertex2;
        if (vertices.containsKey(name2))
            vertex2 = vertices.get(name2);
        else {
            vertex2 = new Vertex<Type>(name2);
            vertices.put(name2, vertex2);
        }

        // add new directed edge from vertex1 to vertex2
        vertex1.addEdge(vertex2);
    }

    /**
     * This method calls depthFirstSearch on the source.
     * 
     * @param srcData - The data of the source
     * @param dstData - the data of the destination
     * @return true if there is a path, false if there isn't
     */
    public boolean areConnected(Type srcData, Type dstData) {
        if (!vertices.containsKey(srcData) || !vertices.containsKey(dstData)) {
            throw new IllegalArgumentException("Source or destination vertex not found in the graph");
        }
        Vertex<Type> src = vertices.get(srcData);
        Vertex<Type> dst = vertices.get(dstData);

        depthFirstSearch(src);
        return dst.isVisited();
    }

    /**
     * This method uses the depthFirstSearch algorithm learned in lecture and calls
     * the helper depthFirstSeatchHelper on the starting vertex.
     * Sets the distance from the start to the starting vertex to 0. Sets the
     * distance of the vertex from the start to infinity
     * 
     * @param s
     */
    public void depthFirstSearch(Vertex<Type> s) {
        // Initialize distances
        for (Vertex<Type> v : vertices.values()) {
            v.setDistanceFromStart(Integer.MAX_VALUE);
        }
        s.setDistanceFromStart(0);

        depthFirstSearchHelper(s);
    }

    /**
     * This method sets the distance from start of the vertex to the distance from
     * the start of the starting vertex + 1. Sets the previous vertex of the
     * currrent vertex
     * to the starting vertex
     * 
     * @param x - The starting vertex
     */
    private void depthFirstSearchHelper(Vertex<Type> x) {
        for (Vertex<Type> w : x.getNeighbors()) {
            if (w.getDistanceFromStart() == Integer.MAX_VALUE) {
                w.setDistanceFromStart(x.getDistanceFromStart() + 1);
                w.setPrevious(x);
                depthFirstSearchHelper(w);
            }
        }
    }

    /**
     * This method finds the shortest path from the vertex of srcData to the vertex
     * of dstData using breadth first search
     * 
     * @param srcData - The source data of the vertices
     * @param dstData - The destination data of the vertices
     * @return The shortest path from the source to destination
     */
    public List<Type> shortestPath(Type srcData, Type dstData) {
        Vertex<Type> src = vertices.get(srcData);
        Vertex<Type> dst = vertices.get(dstData);

        Queue<Vertex<Type>> queue = new LinkedList<>();
        Map<Vertex<Type>, Vertex<Type>> previousVertices = new HashMap<>();

        queue.add(src);
        previousVertices.put(src, null);

        while (!queue.isEmpty()) {
            Vertex<Type> current = queue.poll();
            if (current == dst) {
                return reconstructShortestPath(src, dst, previousVertices);
            }

            for (Vertex<Type> neighbor : current.getNeighbors()) {
                if (!previousVertices.containsKey(neighbor)) {
                    queue.add(neighbor);
                    previousVertices.put(neighbor, current);
                }
            }
        }

        throw new IllegalArgumentException("No path between the two vertices.");
    }

    /**
     * This method adds the data of the current vertex to the path if current isn't
     * null and sets current equal to the current vertex of previousVertices
     * 
     * @param src              - The source vertex
     * @param dst              - The destination vertex
     * @param previousVertices - The previous vertices
     * @return a list of the shortest path
     */
    private List<Type> reconstructShortestPath(Vertex<Type> src, Vertex<Type> dst,
            Map<Vertex<Type>, Vertex<Type>> previousVertices) {
        List<Type> path = new ArrayList<>();
        Vertex<Type> current = dst;

        while (current != null) {
            path.add(0, current.getData()); // Add at the beginning to maintain correct order
            current = previousVertices.get(current);
        }

        return path;
    }

    /**
     * This method uses topological sort to order the vertices in a graph
     * 
     * @param <Type>       - Gener
     * @param verticesList - The list of vertices
     * @param graph        - The graph of the vertices
     * @param inDegrees    - The list of indegrees
     * @return a list of the sorted vertices
     */
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

}
