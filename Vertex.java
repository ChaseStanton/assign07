package assign07;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Vertex<Type> {
    private Type data;
    private boolean visited;
    private int distanceFromStart;
    private Vertex<Type> previous;
    private List<Vertex<Type>> adj;

    public Vertex(Type data) {
        this.data = data;
        this.visited = false;
        this.distanceFromStart = Integer.MAX_VALUE; 
        this.previous = null;
        this.adj = new ArrayList<>();
    }

    public void addEdge(Vertex<Type> vertex) {
        adj.add(vertex);
    }

    public Type getData() {
        return data;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(int distance) {
        this.distanceFromStart = distance;
    }

    public Vertex<Type> getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex<Type> previous) {
        this.previous = previous;
    }

    public List<Vertex<Type>> getNeighbors() {
        return adj;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}