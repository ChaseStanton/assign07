package assign07;

/**
 * This method represents a Edge of a vertex of a generic type
 * 
 * @author Chase Stanton and Reece Kalmar
 *
 * @param <Type> - Generic
 */
public class Edge<Type> {
	private Vertex<Type> dst;

	/**
	 * This constructor sets the destination vertex equal to the vertex provided in
	 * the parameter
	 * 
	 * @param dst
	 */
	public Edge(Vertex<Type> dst) {
		this.dst = dst;
	}

	/**
	 * This method retrieves the destination vertex
	 * 
	 * @return the destination vertex
	 */
	public Vertex<Type> getOtherVertex() {
		return this.dst;
	}

}
