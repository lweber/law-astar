package com.law.astar.graph;

import com.law.astar.Graph;
import com.law.astar.GraphNode;

/**
 * @author lweber
 */
public class GraphFactory {
	
	/**
	 * A simple weighted graph with four nodes and five weighted edges:
	 * <ul>
	 * <li> (n1)-1-(n2)
	 * <li> (n1)-8-(n4)
	 * <li> (n2)-4-(n3)
	 * <li> (n2)-3-(n4)
	 * <li> (n3)-1-(n4)
	 * </ul>
	 */
	public static Graph createUndirectedWeightedGraph() {
		
		UndirectedWeightedGraph g = new UndirectedWeightedGraph();
		
		GraphNode n1 = createGraphNode("n1");
		GraphNode n2 = createGraphNode("n2");
		GraphNode n3 = createGraphNode("n3");
		GraphNode n4 = createGraphNode("n4");
		
		g.add(new WeightedEdge(n1, n2, 1));
		g.add(new WeightedEdge(n1, n4, 8));
		g.add(new WeightedEdge(n2, n3, 4));
		g.add(new WeightedEdge(n2, n4, 3));
		g.add(new WeightedEdge(n3, n4, 1));
		
		return g;
	}
	
	/**
	 * Create a new graph node with the given name.
	 */
	public static GraphNode createGraphNode(String nodeName) {
		return new Vertex(nodeName);
	}
	
}
