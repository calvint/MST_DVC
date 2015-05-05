package d_minSpanTree.controller.operation;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import d_minSpanTree.model.Edge;
import d_minSpanTree.model.GraphModelInterface;
import d_minSpanTree.model.Triangle;
import d_minSpanTree.model.Vertex;

public class AddNRandomVertices implements GraphOperation {
    boolean reset;
    int nRandomVertices;
    Random rand = new Random(); // One can seed with a parameter variable here
    static int seed = 1;

	public AddNRandomVertices(boolean reset, int nRandVert, Stack<UndoableGraphOperation> commandStack) {
	    this.reset = reset;
	    nRandomVertices = nRandVert;
        rand.setSeed(seed++);
	}

	public void execute(GraphModelInterface gmi) {
	    // Start from no graph (Without this, it should be just an append of new vertices.)
	    if (reset) {
            gmi.getVertices().clear();
            gmi.getEdges().clear();
	    }

	    //TODO: (group assignment) modify below to get the code run faster
        long startTime = System.nanoTime(); // Start the total timing

        final double width = 800; // TODO relate these to actual window space dimensions
    	final double height = 700; // or, in the very least to GraphViewer.windowWidth and Height
    	/* Delaunay */
        ArrayList<Triangle> triangulation = new ArrayList<Triangle>(); //empty triangle mesh

        //add the triangle that surrounds all of the points aka the "super triangle".
        Triangle superTriangle = new Triangle (
                new Vertex("sv1", -0.1, -0.1 ),           // Right Angle
                new Vertex("sv2", 2 * width + 0.1, 0 ),
                new Vertex("sv3", 0, 2 * height + 0.1 ));
        triangulation.add(superTriangle);

        // create each point as normal
        for (int i=0; i<nRandomVertices; i++) {
            double xPos = rand.nextDouble() * width;
            double yPos = rand.nextDouble() * height;
            Vertex v = new Vertex("p"+gmi.getVertices().size(), xPos, yPos);
            gmi.getVertices().add(v);

            //determine triangles to be changed
            ArrayList<Triangle> badTriangles = new ArrayList<Triangle>();
            for (Triangle triangle: triangulation) {
            	if (triangle.pointInsideCircumcircle(v)) {
            	    // TODO: Add all possible triangles!
            	    // The triangles we're trying to remove here don't exist yet!
            		badTriangles.add(triangle);
            	}
            }

            //determine polygon around triangles that need to be changed
            ArrayList<Edge> polygon = new ArrayList<Edge>();
            //for each opf the triangles we have
            for (Triangle badTriangle : badTriangles) {
            	//find edges that are NOT shared by any other triangles
            	for (Edge edge : badTriangle.getEdges()) {
            		boolean shared = false;
            		outerloop:
            		for (Triangle otherBadTriangle : badTriangles) {
            			if (badTriangle != otherBadTriangle) {
            				for (Edge otherEdge: otherBadTriangle.getEdges()) {
            					if (edge.equals(otherEdge)) {
            						//if the edge is found in any of the other triangles
            						//break, and don't add it
            						shared = true;
            						break outerloop;
            					}
            				}
            			}
            		}
            		if (!shared) {
            			//we didn't find the edge, so do add it.
            			polygon.add(edge);
            		}
            	}
            }
          //  System.out.println("Polygon size: " + polygon.size());
            //remove the outdated triangles
            for (Triangle badTriangle : badTriangles) {
            	triangulation.remove(badTriangle);
            }

            //retriangulate the polygon
            for (Edge polyEdge : polygon) {
            	triangulation.add(new Triangle(polyEdge, v));
            }
        }

        //remove the super triangle
        for (Edge superEdge : superTriangle.getEdges()) {
        	for (Triangle triangle : triangulation) {
        		outerLoopTwo:
        		for (Edge otherEdge: triangle.getEdges()) {
					if (superEdge.equals(otherEdge)) {
						triangulation.remove(triangle);
						break outerLoopTwo;
					}
				}
        	}
        }

        //add triangulation to gmi
        for (Triangle triangle : triangulation) {
        	for (Edge edge : triangle.getEdges()) {
        		Boolean isRepeat = false;
        		outerLoopThree:
    			for (Edge otherEdge: gmi.getEdges()) {
					if (edge.equals(otherEdge)) {
						isRepeat = true;
						break outerLoopThree;
					}
				}
        		if (isRepeat == false) {
        			gmi.getEdges().add(edge);
        		}
        	}
        }
        /* Delaunay */

//        for (int i=0; i<nRandomVertices; i++) {
//            double xPos = rand.nextDouble() * width;
//            double yPos = rand.nextDouble() * height;
//            Vertex v = new Vertex("p"+gmi.getVertices().size(), xPos, yPos);
//
//            // Edge creation O(n^2).
//            for (Vertex vB : gmi.getVertices()) {
//                Edge e = new Edge(v, vB);
//                double weight = Math.sqrt(
//                        (v.getX()-vB.getX())*(v.getX()-vB.getX())+
//                        (v.getY()-vB.getY())*(v.getY()-vB.getY())  );
//                e.setWeight(weight);
//
//                gmi.getEdges().add(e);
//            }
//
//            // Vertex creation O(n)
//            gmi.getVertices().add(v);
//		}
        long endTime  = System.nanoTime(); // Finish the total timing
        float timeElapsed = (endTime-startTime)/1000000.0f; // milliseconds
        System.out.println("Edge creation O(f(nE,nV)???):" + timeElapsed);

		gmi.runAlgorithms();
	}

	public String getName() {
		return "Random 100";
	}

}
