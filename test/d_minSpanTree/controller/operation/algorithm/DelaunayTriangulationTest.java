package d_minSpanTree.controller.operation.algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

import d_minSpanTree.controller.Controller;
import d_minSpanTree.controller.ControllerInterface;
import d_minSpanTree.controller.operation.AddGraphAlgorithm;
import d_minSpanTree.controller.operation.AddVertex;
import d_minSpanTree.model.GraphModel;
import d_minSpanTree.model.GraphModelInterface;

public class DelaunayTriangulationTest {

	@Test
	public void triangleCountTest() {
		GraphModelInterface model = new GraphModel();
		ControllerInterface controller = new Controller(model);
		controller.getInvoker().doOperation(new AddVertex(40, 40));
		controller.getInvoker().doOperation(new AddVertex(80, 50));
		controller.getInvoker().doOperation(new AddVertex(60, 70));
		controller.getInvoker().doOperation(new AddGraphAlgorithm(new DelaunayTriangulation()));
		if (model.getEdges().size() != 3) {
			fail("there should only be one triangle...");
		}
	}
	
}
