package assign07;

public class Edge<Type> {
	private Vertex<Type> src, dst;

	public Edge(Vertex<Type> src, Vertex<Type> dst) {
		this.src = src;
		this.dst = dst;
	}

	public Vertex<Type> getSRC() {
		return this.src;
	}

	public Vertex<Type> getDST() {
		return this.dst;
	}

	@Override
	public String toString() {
		return src.getData() + " -> " + dst.getData();
	}
}
