package assign07;

public class Vertex<Type> {
	private Type data;

	public Vertex(Type data) {
		this.data = data;
	}

	public Type getData() {
		return this.data;
	}

	@Override
	public String toString() {
		return data.toString();
	}
}
