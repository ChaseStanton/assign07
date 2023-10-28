package assign07;

import java.util.ArrayList;
import java.util.List;

/**
 * Sets up a vertex object to be used in the graph class.
 *
 * @author Reece Kalmar, Chase Stanton
 * @version October 26th, 2023
 */
public class Vertex<Type> {
	private Type data;
	private boolean visited;
	private int distanceFromStart;
	private Vertex<Type> previous;
	private List<Vertex<Type>> adj;

	/**
	 * Constructor for the vertex class, takes and stores data as a vertice
	 * 
	 * @param data - data to be stored
	 */
	public Vertex(Type data) {
		this.data = data;
		this.visited = false;
		this.distanceFromStart = Integer.MAX_VALUE;
		this.previous = null;
		this.adj = new ArrayList<>();
	}

	/**
	 * Adds an edge to the adjadcency list completing a source/destination vertix
	 * connection
	 * 
	 * @param vertex - the vertex to be added to the adjacency
	 */
	public void addEdge(Vertex<Type> vertex) {
		adj.add(vertex);
	}

	/**
	 * Getter method to retrieve a vertices data
	 * 
	 * @return - data stored in a vertici
	 */
	public Type getData() {
		return data;
	}

	/**
	 * returns the visited boolean data from a vertice
	 * 
	 * @return - if the vertici has been visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Setter method for the visited data in a vertice
	 * 
	 * @param visited - if a vertici has been visited or not
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * Getter method to retrieve how far away a vertice is from the source vetex
	 * 
	 * @return - distance in integer
	 */
	public int getDistanceFromStart() {
		return distanceFromStart;
	}

	/**
	 * Setter method for the distance from start variable
	 * 
	 * @param distance - how far from the source node the vertice is from the vertex
	 *                 in integer
	 */
	public void setDistanceFromStart(int distance) {
		this.distanceFromStart = distance;
	}

	/**
	 * Getter method to retrieve the previous value or reverse traverse a directed
	 * graph once
	 * 
	 * @return - the past vertici\e that is connected to the vertex
	 */
	public Vertex<Type> getPrevious() {
		return previous;
	}

	/**
	 * Setter method for the previous variable that is to be stored
	 * 
	 * @param previous - the past vertice
	 */
	public void setPrevious(Vertex<Type> previous) {
		this.previous = previous;
	}

	/**
	 * Getter method to retrieve the list of all the vertices
	 * 
	 * @return - List of vertices connected to the current vertex
	 */
	public List<Vertex<Type>> getNeighbors() {
		return adj;
	}
}