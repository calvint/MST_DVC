package d_minSpanTree.controller.operation.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import d_minSpanTree.model.Edge;
import d_minSpanTree.model.GraphModelInterface;
import d_minSpanTree.model.Vertex;
import d_minSpanTree.model.VertexComparator;

public class MinimumSpanningTree implements GraphAlgorithm {
	private ArrayList<ArrayList<Vertex>> forest;
	private TreeMap<Vertex,ArrayList<Vertex>> vertexToTree;

	public void execute(GraphModelInterface gmi) {
		forest = new ArrayList<ArrayList<Vertex>>();
		for (Edge e : gmi.getEdges()) {
			e.setOpacity(.05);
		}
		ArrayList<Edge> finalTree = new ArrayList<Edge>();
		vertexToTree = new TreeMap<>(new VertexComparator());
		ArrayList<Object> edges = new ArrayList<Object>();
		edges.addAll(gmi.getEdges());
		
		for (Vertex v : gmi.getVertices()) {
			ArrayList<Vertex> tree = new ArrayList<Vertex>();
			tree.add(v);
			vertexToTree.put(v,tree);
			forest.add(tree);
		}

        // Kruskal's algorithm O(f(nE,nV)???) in an efficient time complexity implementation.
        long startTime = System.nanoTime(); // Start the total timing
        // Quicksort of the entire edge array puts us at O(???) time complexity

		(new QuickSort(edges, new AscEdgeWeight())).sort();

		for (int i = 0; i < edges.size(); i++) {
			Edge e = (Edge) edges.get(i);
			Vertex start = e.getStart();
			Vertex end = e.getEnd();
			ArrayList<Vertex> tree1 = findTreeWithMap(start);
			ArrayList<Vertex> tree2 = findTreeWithMap(end);
			if (tree1 != tree2) {
				finalTree.add(e);
				tree1.addAll(tree2);
				//now update the tree holder.
				for (Vertex vert : tree2) {
					vertexToTree.remove(vert);
					vertexToTree.put(vert,tree1); //overwrites
				}
				forest.remove(tree2);
			}
		}
        long endTime  = System.nanoTime(); // Finish the total timing
        float timeElapsed = (endTime-startTime)/1000000.0f; // milliseconds
        System.out.println("MST (Kruskal) time O(f(nE,nV)???):" + timeElapsed);

		for (Edge e : finalTree) {
			e.setOpacity(1);
		}
		
		gmi.getDisplayEdges().clear();
        gmi.getDisplayEdges().addAll(finalTree); // Only adding the MST edges for display
        //gmi.getDisplayEdges().addAll(gmi.getEdges()); // Adding all edges for display (Use this only if Delaunay)
	}

	private ArrayList<Vertex> findTreeWithMap(Vertex vert) {
		return vertexToTree.get(vert);
	}
	
//Old findTree, for debugging and benchmarking purposes.	
	private ArrayList<Vertex> findTree(Vertex vert) {
		for (ArrayList<Vertex> tree : forest) {
			for (Vertex v : tree)
				if (vert == v)
					return tree;
		}
		return null;
	}

	public String getName() {
		return "Minimum Spanning Tree";
	}

	public boolean canLiveUpdate() {
		return true;
	}

}
