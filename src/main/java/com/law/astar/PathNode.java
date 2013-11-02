/*
 * Created on Nov 3, 2006
 */
package com.law.astar;

import java.util.LinkedList;
import java.util.List;

/**
 * The PathNode class represents a GraphNode that is one node on a path
 * to a given end node.
 * 
 * The PathNode class is a wrapper for the GraphNode class in order
 * to link GraphNodes in a path and calculate F, G and H costs along
 * that path.
 *
 * <p> PathNode overrides equals() and hashCode() so that objects
 * may be stored in HashSet and other Set collections.
 *
 * @author lweber
 */
public class PathNode implements Comparable<PathNode> {
	
	private final GraphNode thisGraphNode;
	private final GraphNode endGraphNode;
	
	private PathNode parentNode = null; // Previous node in the path.
	
	private int fCost = -1;
	private int gCost = -1;
	private int hCost = -1;
	
	/**
	 * Construct a new PathNode object to represent a given GraphNode.
	 *
	 * @param gn - the graph node that this path node represents.
	 * @param endNode - the ending graph node to which a path is
	 *  being sought. This value is used by getH(), the heuristic method.
	 *  Note, endNode should be null if the calling algorithm is not
	 *  searching for a specific end node - e.g. Dijkstra's algorithm -
	 *  getH() will return 0 in this case.
	 */
	PathNode(GraphNode gn, GraphNode endNode) {
		thisGraphNode = gn;
		endGraphNode = endNode;
	}
	
	/**
	 * Get the name of this node. Node names are unique within a given Graph so
	 * that equals() and hashCode() may use them.
	 * 
	 * @return The name of this node.
	 */
	public String getNodeName() {
		return thisGraphNode.getNodeName();
	}
	
	/**
	 * Get the GraphNode associated with this PathNode.
	 *
	 * @return The graph node.
	 */
	GraphNode getGraphNode() {
		return thisGraphNode;
	}
	
	/**
	 * Get a list of nodes in the path. This is a list of GraphNode objects
	 * starting with the first node in the path and ending with this node.
	 * 
	 * @return A list of GraphNode objects.
	 */
	List<GraphNode> getNodes() {
		LinkedList<GraphNode> resultList = new LinkedList<GraphNode>();
		PathNode pn = this;
		while (pn != null) {
			resultList.addFirst(pn.getGraphNode());
			pn = pn.getParent();
		}
		return resultList;
	}
	
	/**
	 * F = the estimated cost to travel from the start node to the
	 * destination node along a path that includes this node.
	 *
	 * Path score F = G + H
	 *
	 * @return
	 */
	int getF() {
		if (fCost < 0) {
			// Cache, for performance.
			fCost = getG() + getH();
		}
		return fCost;
	}
	
	/**
	 * G = the movement cost to move from the starting node to this node,
	 * following the path generated to get there.
	 *
	 * <p> The G cost of this node equals the G cost of it's parent plus
	 * the cost to travel from the parent to this node.
	 *
	 * @return
	 */
	int getG() {
		if (gCost < 0) {
			// Cache, for performance.
			if (parentNode == null) {
				gCost = 0;
			}
			else {
				gCost = parentNode.getG() +
					thisGraphNode.getGraph().getCostToNeighbor(
						parentNode.getGraphNode(), thisGraphNode);
			}
		}
		return gCost;
	}
	
	/**
	 * H = the heuristic - the estimated cost to move from this node to the
	 * final destination. The heuristic is said to be "admissible", and thus
	 * guaranteed to find the shortest path, if it never over estimates the
	 * cost. The closer it is to the actual cost the better the performance
	 * of the path finding algorithm will be.
	 *
	 * @return The estimated cost (0 - n) to get to the final destination.
	 *  Returns 0 if no end node is specified in the constructor.
	 *
	 * @see Graph.estimateCostToEnd(n1, n2).
	 */
	int getH() {
		if (hCost < 0) {
			// Cache, for performance.
			if (endGraphNode != null) {
				hCost = thisGraphNode.getGraph().estimateCostToEnd(
						thisGraphNode, endGraphNode);
			}
			else {
				hCost = 0;
			}
		}
		return hCost;
	}
	
	/**
	 * Get the PathNode that was traveled through just before this PathNode.
	 *
	 * @return The parent PathNode, or null if none.
	 */
	PathNode getParent() {
		return parentNode;
	}
	
	/**
	 * Change this PathNode's parent to a given PathNode. The value of getG(),
	 * and therefore getF() as well, may change after this since the node
	 * is now part of a different path.
	 *
	 * @param parent -
	 */
	void setParent(PathNode parent) {
		parentNode = parent;
		
		// Reset caches since costs will change.
		fCost = -1;
		gCost = -1;
		// hCost = -1; hCost should not have to reset.
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getNodeName() +
			" P=" + (getParent() == null ? "null" : getParent().getNodeName()) +
			" F=" + getF() +
			" G=" + getG() +
			" H=" + getH();
	}
	
	/**
	 * This method is defined in terms of getNodeName() and is thus
	 * consistent with equals() and satisfies the general contract
	 * that equal objects return the same hash code.
	 */
	public int hashCode() {
		return getNodeName().hashCode();
	}
	
	/**
	 * This method is defined in terms of getNodeName() - two PathNode's
	 * representing GraphNode's with the same name are equal (and will
	 * return the same hash code).
	 */
	public boolean equals(Object obj) {
		return obj instanceof PathNode &&
				((PathNode)obj).getNodeName().equals(getNodeName());
	}
	
	/**
	 * Compare this PathNode to another PathNode.
	 * 
	 * <p> This method is consistent with equals() for nodes that are in
	 * the same Graph and thus have unique names. However, nodes are first
	 * compared by F cost - lower F cost has higher priority - and are therefore
	 * sorted in ascending order by F cost. This allows a sorted PathNodeSet to
	 * quickly return it's lowest F cost node.
	 */
	public int compareTo(PathNode n) {
		int result;
		
		int cost1 = getF();
		int cost2 = n.getF();
		
		if (cost1 == cost2) {
			// Make sure two nodes with the same F cost can be added
			// to a TreeSet.
			result = getNodeName().compareTo(n.getNodeName());
		}
		else {
			result = cost1 < cost2 ? -1 : 1;
		}
		
		return result;
	}
	
}
