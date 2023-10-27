package assign07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph<Type> {
	private HashMap<Type, Vertex<Type>> vertices;  

    public Graph() {
        this.vertices = new HashMap<Type, Vertex<Type>>();
    }

    public void addEdge(Type name1, Type name2) {
		Vertex<Type> vertex1;
		// if vertex already exists in graph, get its object
		if(vertices.containsKey(name1))
			vertex1 = vertices.get(name1);
		// else, create a new object and add to graph
		else {
			vertex1 = new Vertex<Type>(name1);
			vertices.put(name1, vertex1);
		}

		Vertex<Type> vertex2;
		if(vertices.containsKey(name2))
			vertex2 = vertices.get(name2);
		else {
			vertex2 = new Vertex<Type>(name2);
			vertices.put(name2, vertex2);
		}

		// add new directed edge from vertex1 to vertex2
		vertex1.addEdge(vertex2);
	}
    public boolean areConnected(Type srcData, Type dstData) {
        if (!vertices.containsKey(srcData) || !vertices.containsKey(dstData)) {
            throw new IllegalArgumentException("Source or destination vertex not found in the graph");
        }
        Vertex<Type> src = vertices.get(srcData);
        Vertex<Type> dst = vertices.get(dstData);

        depthFirstSearch(src);
        return dst.isVisited();
    }

    

    public void depthFirstSearch(Vertex<Type> s) {
        // Initialize distances
        for (Vertex<Type> v : vertices.values()) {
            v.setDistanceFromStart(Integer.MAX_VALUE);
        }
        s.setDistanceFromStart(0);
        
        depthFirstSearchHelper(s);
    }
    private void depthFirstSearchHelper(Vertex<Type> x) {
        for (Vertex<Type> w : x.getNeighbors()) {
            if (w.getDistanceFromStart() == Integer.MAX_VALUE) {
                w.setDistanceFromStart(x.getDistanceFromStart() + 1); // Increment hop count
                w.setPrevious(x);
                depthFirstSearchHelper(w);
            }
        }
    }
    
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
                // Reconstruct and return the shortest path
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
        
    
        
    
private List<Type> reconstructShortestPath(Vertex<Type> src, Vertex<Type> dst, Map<Vertex<Type>, Vertex<Type>> previousVertices) {
    List<Type> path = new ArrayList<>();
    Vertex<Type> current = dst;

    while (current != null) {
        path.add(current.getData());
        current = previousVertices.get(current);
    }

    Collections.reverse(path);
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
    		StringBuilder result = new StringBuilder();
    		
    		for(Vertex<Type> v : vertices.values()) 
    			result.append(v + "\n");
    		
    		return result.toString();
    	}
}
