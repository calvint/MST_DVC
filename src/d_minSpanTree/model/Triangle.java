package d_minSpanTree.model;

import java.util.ArrayList;

public class Triangle {
	
	ArrayList<Edge> edgeArray = new ArrayList<Edge>();
	
	public Triangle(Vertex vertex, Vertex vertex2, Vertex vertex3) {
		edgeArray.add(new Edge(vertex, vertex2));
		edgeArray.add(new Edge(vertex, vertex3));
		edgeArray.add(new Edge(vertex3, vertex2));
	}

	public Triangle(Edge edge, Vertex vertex3) {
		Vertex vertex = edge.getStart();
		Vertex vertex2 = edge.getEnd();
		edgeArray.add(new Edge(vertex, vertex2));
		edgeArray.add(new Edge(vertex, vertex3));
		edgeArray.add(new Edge(vertex3, vertex2));
	}

	public boolean pointInsideCircumcircle(Vertex v) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Edge> getEdges() {
		return edgeArray;
	}

}
