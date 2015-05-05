package d_minSpanTree.controller.operation.algorithm;

import static org.junit.Assert.fail;

import org.junit.Test;

import d_minSpanTree.controller.Controller;
import d_minSpanTree.controller.ControllerInterface;
import d_minSpanTree.controller.operation.AddGraphAlgorithm;
import d_minSpanTree.controller.operation.AddVertex;
import d_minSpanTree.model.Edge;
import d_minSpanTree.model.GraphModel;
import d_minSpanTree.model.GraphModelInterface;
import d_minSpanTree.model.Vertex;

public class DelaunayTriangulationTest {

	@Test
	public void triangleCountTest() {
		GraphModelInterface model = new GraphModel();
		ControllerInterface controller = new Controller(model);
		controller.getInvoker().doOperation(new AddVertex(40, 40));
		controller.getInvoker().doOperation(new AddVertex(80, 50));
		controller.getInvoker().doOperation(new AddVertex(60, 70));
		controller.getInvoker().doOperation(new AddGraphAlgorithm(new DelaunayTriangulation()));
	    for (Vertex v : model.getVertices()) {
	        System.out.println("V at ("+v.getX()+", "+v.getY()+")");
	    }
		for (Edge e : model.getEdges()) {
		    System.out.println("Edge from (" + e.getStart().getX() + ", " + e.getStart().getY()
		            + ") to (" + e.getEnd().getX() + ", " + e.getEnd().getY() + ").");
		}
		if (model.getEdges().size() != 3) {
			fail("There should be exactly one triangle!");
		}
	}

}
