package d_minSpanTree.model;

public class Edge extends Object {
	private Vertex start, end;
	private double weight, opacity;
	private EdgeState state;

	public Edge(Vertex v1, Vertex v2) {
		this(v1, v2, Double.NaN, 0.1, EdgeState.UNDIRECTED);
	}

	public Edge(Vertex v1, Vertex v2, double weight, double opacity, EdgeState state) {
		start = v1;
		end = v2;
		this.weight = weight;
		this.opacity = opacity;
		this.state = state;

		v1.getAdjacent().add(v2);
		v2.getAdjacent().add(v1);
	}

	public Vertex getStart() {
		return start;
	}

	public void setStart(Vertex v) {
		start = v;
	}

	public Vertex getEnd() {
		return end;
	}

	public void setEnd(Vertex v) {
		end = v;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double w) {
		weight = w;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double o) {
		opacity = o;
	}

	public EdgeState getState() {
		return state;
	}

	public void setState(EdgeState e) {
		state = e;
	}
	
	public boolean equals(Edge other) {
		if ( this.start.getX() == other.start.getX() & this.start.getY() == other.start.getY() &
				this.end.getX() == other.end.getX() & this.end.getY() == other.end.getY() ) {
			return true;
		} else if ( this.start.getX() == other.end.getX() & this.start.getY() == other.end.getY() &
				this.end.getX() == other.start.getX() & this.end.getY() == other.start.getY() ) {
			return true;
		} else {
			return false;
		}
	}

	public boolean equals(Object other) {
		if (!(other instanceof Edge) ) {
			return false;
		} else {
			return equals((Edge) other);
		}
	}
}
