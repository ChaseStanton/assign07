package assign07;

public class Edge<Type> {
	private Vertex<Type> dst;

	public Edge(Vertex<Type> dst) {
		this.dst = dst;
	}

	

	public Vertex<Type> getOtherVertex() {
		return this.dst;
	}

	@Override
	public String toString() {
		return this.dst.toString();
	}
}
