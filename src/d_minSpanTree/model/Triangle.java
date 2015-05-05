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
	    // The reason for saving so many points is to reduce the number of calculations.
	    final double Ax = edgeArray.get(0).getStart().getX();
	    final double Ay = edgeArray.get(0).getStart().getY();
	    final double Bx = edgeArray.get(2).getStart().getX();
	    final double By = edgeArray.get(2).getStart().getY();
	    final double Cx = edgeArray.get(2).getEnd().getX();
	    final double Cy = edgeArray.get(2).getEnd().getY();
	    final double Dx = v.getX();
        final double Dy = v.getY();
        final double DDx = Dx*Dx;
        final double DDy = Dy*Dy;
	    final double a = (Ax - Dx);
	    final double b = (Ay - Dy);
	    final double c = (Ax*Ax - DDx) + (Ay*Ay - DDy);
	    final double d = (Bx - Dx);
	    final double e = (By - Dy);
	    final double f = (Bx*Bx - DDx) + (By*By - DDy);
	    final double g = (Cx - Dx);
	    final double h = (Cy - Dy);
	    final double i = (Cx*Cx - DDx) + (Cy*Cy - DDy);

	    // These three lines should be equivalent
	    // return a*e*i + b*f*g + c*d*h - c*e*g - b*d*i - a*f*h > 0;
	    // return (a*e*i + b*f*g + c*d*h) - (c*e*g + b*d*i + a*f*h) > 0;
		return (a*e*i + b*f*g + c*d*h) > (c*e*g + b*d*i + a*f*h);
	}

	public ArrayList<Edge> getEdges() {
		return edgeArray;
	}

}
