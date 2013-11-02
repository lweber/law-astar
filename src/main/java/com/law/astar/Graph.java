/*
 * Created on Nov 3, 2006
 */
package com.law.astar;

import java.util.Iterator;

/**
 * Interface for a graph which implements methods required for
 * finding paths between nodes in the graph via the A* algorithm.
 * 
 * @author lweber
 */
public interface Graph {
	
	/**
	 * Get the nodes that are direct neighbors of, and accessible from,
	 * the given node.
	 *
	 * @param n - The node whose neighbors will be returned.
	 *
	 * @return An Iterator of GraphNode objects, in no particular order.
	 */
	Iterator<? extends GraphNode> getNeighborNodes(GraphNode n);
	
	/**
	 * Get the actual cost to travel from node n1 to neighbor node n2.
	 *
	 * @param n1 - The starting neighbor node.
	 * @param n2 - The ending neighbor node.
	 *
	 * @return The travel cost: zero or greater.
	 */
	int getCostToNeighbor(GraphNode n1, GraphNode n2);
	
	/**
	 * Known as the heuristic algorithm, this estimates the travel
	 * cost from a current node to an end node. The heuristic is said
	 * to be "admissible", and thus guarantees the aStar algorithm will
	 * find the shortest path, if it never over estimates the cost. The
	 * closer it is to the actual cost the better the performance of the
	 * path finding algorithm will be.
	 *
	 * @param startNode - The starting node.
	 * @param endNode - The end node.
	 *
	 * @return An estimated travel cost: zero or greater.
	 */
	int estimateCostToEnd(GraphNode startNode, GraphNode endNode);
	
}
