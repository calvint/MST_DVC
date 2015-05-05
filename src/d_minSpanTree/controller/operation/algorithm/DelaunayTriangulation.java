package d_minSpanTree.controller.operation.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import d_minSpanTree.model.Edge;
import d_minSpanTree.model.GraphModelInterface;
import d_minSpanTree.model.Triangle;
import d_minSpanTree.model.Vertex;

public class DelaunayTriangulation implements GraphAlgorithm {

	@Override
    public void execute(GraphModelInterface gmi) {
		gmi.getEdges().clear();

		// TODO: the code here is not Delaunay triangulation.
        // TODO: (group assignment) modify below to get the code run faster
        long startTime = System.nanoTime(); // Start the total timing

        final double width = 800; // TODO relate these to actual window space dimensions
    	final double height = 700; // or, in the very least to GraphViewer.windowWidth and Height

        ArrayList<Triangle> triangulation = new ArrayList<Triangle>(); //empty triangle mesh

        //add the triangle that surrounds all of the points aka the "super triangle".
        Triangle superTriangle = new Triangle (
                new Vertex("sv1", -0.1, -0.1 ),           // Right Angle
                new Vertex("sv2", 2 * width + 0.1, 0 ),
                new Vertex("sv3", 0, 2 * height + 0.1 ));
        triangulation.add(superTriangle);

        // insert each point one at a time
        for (int i = 0; i < gmi.getVertices().size(); i++) {
        	Vertex v = gmi.getVertices().get(i);
//        	System.out.println("\nFor vertex ("+v.getX()+", "+v.getY()+"): ");

            //determine triangles to be changed
            ArrayList<Triangle> badTriangles = new ArrayList<Triangle>();
            Set<Edge> polygon = new HashSet<>();
            for (Triangle triangle: triangulation) {
            	if (triangle.pointInsideCircumcircle(v)) {
            		//instead of looping later to prune the bad triangle edges
            		//try and remove them here
            		badTriangles.add(triangle); //make it a bad triangle
            		ArrayList<Edge> triangleEdges = triangle.getEdges();
            		boolean foundEdge = false; //if we find that the edge is shared
            		//don't bother adding this triangle's edges to the polygon
            		for (Edge e : triangleEdges) {
            			if (!(polygon.contains(e))) {
            				//ignore this triangle
            				polygon.add(e);
            			}
            		}
//            		if (!foundEdge) {
//            			//if none of the edges are shared, add all of them
//            			//to the polygon
//            			polygon.addAll(triangleEdges);
//            		}
            	}
            }

//            // debug
//            System.out.println("Bad Triangles:");
//            for (Triangle t : badTriangles) {
//                System.out.println(t);
//            }

            //determine polygon around triangles that need to be changed
            //ArrayList<Edge> polygon = new ArrayList<Edge>();

            //for each of the triangles for which the new point is within the circumcircle
//            for (Triangle badTriangle : badTriangles) {
//
//            	//find edges that are NOT shared by any other triangles
//            	for (Edge edge : badTriangle.getEdges()) {
//            		boolean shared = false;
//            		outerloop:
//            		for (Triangle otherBadTriangle : badTriangles) {
//            			if (badTriangle != otherBadTriangle) {
//            				for (Edge otherEdge: otherBadTriangle.getEdges()) {
//            					if (edge.equals(otherEdge)) {
//            						//if the edge is found in any of the other triangles
//            						//break, and don't add it
//            						shared = true;
//            						break outerloop;
//            					}
//            				}
//            			}
//            		}
//            		if (!shared) {
//            			//we didn't find the edge, so do add it.
//            			polygon.add(edge);
//            		}
//            	}
//            }
              System.out.println("Polygon size: " + polygon.size());
            //remove the outdated triangles - this stays so we don't get
            //IllegalStateExceptions!
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
        	for (Triangle triangle : (ArrayList<Triangle>) triangulation.clone()) {
        	    Vertex superStart = superEdge.getStart();
        	    Vertex superEnd   = superEdge.getEnd();
        		outerLoopTwo:
        		for (Edge otherEdge: triangle.getEdges()) {
        		    Vertex otherStart = otherEdge.getStart();
        		    Vertex otherEnd   = otherEdge.getEnd();
        		    if (superStart.equals(otherStart)
        		     || superStart.equals(otherEnd)
        		     || superEnd.equals(otherStart)
        		     || superEnd.equals(otherEnd)) {
//					if (superEdge.equals(otherEdge)) {
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

//		for (int i0 = 0; i0 < gmi.getVertices().size(); i0++) {
//			for (int i1 = i0 + 1; i1 < gmi.getVertices().size(); i1++) {
//				Vertex v0 = gmi.getVertices().get(i0);
//				Vertex v1 = gmi.getVertices().get(i1);
//				Edge e = new Edge(v0, v1);
//		        double dx = v1.getX() - v0.getX();
//		        double dy = v1.getY() - v0.getY();
//		        e.setWeight(dx * dx + dy * dy);
//                gmi.getEdges().add(e);
//			}
//		}
        long endTime   = System.nanoTime(); // Finish the total timing
        float timeElapsed = (endTime-startTime)/1000000.0f; // milliseconds
        System.out.println("Edge creation O(f(nE,nV)???):" + timeElapsed);
	}

	@Override
    public String getName() {
		return "Connect All Vertices";
	}

	@Override
    public boolean canLiveUpdate() {
		return true;
	}

}
