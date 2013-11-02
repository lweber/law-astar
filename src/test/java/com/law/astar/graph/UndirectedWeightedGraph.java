package com.law.astar.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.law.astar.Graph;
import com.law.astar.GraphNode;

/**
 * @author lweber
 */
public class UndirectedWeightedGraph implements Graph {
	
	private Set<WeightedEdge> edges = new HashSet<WeightedEdge>();
	
	public UndirectedWeightedGraph() {
	}
	
	public void add(WeightedEdge edge) {
		edges.add(edge);
	}
	
	public Iterator<? extends GraphNode> getNeighborNodes(GraphNode n) {
		Set<GraphNode> neighbors = new HashSet<GraphNode>();
		for (WeightedEdge e : edges) {
			if (e.getN1().equals(n)) {
				neighbors.add(e.getN2());
			}
			else if (e.getN2().equals(n)) {
				neighbors.add(e.getN1());
			}
		}
		return neighbors.iterator();
	}
	
	public int getCostToNeighbor(GraphNode n1, GraphNode n2) {
		// TODO Could there be more than one connecting edge?
		for (WeightedEdge e : edges) {
			if (e.connects(n1, n2)) {
				return e.getWeight();
			}
		}
		throw new IllegalStateException(
				"The graph must contain both nodes and they must be neighbors.");
	}
	
	public int estimateCostToEnd(GraphNode startNode, GraphNode endNode) {
		return 0;
	}
	
}
