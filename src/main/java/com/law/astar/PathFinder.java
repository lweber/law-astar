/*
 * Created on Nov 3, 2006
 */
package com.law.astar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Methods for finding paths in a graph.
 *
 * @author lweber
 */
public class PathFinder {
	
	/**
	 * Find and return the shortest path between two nodes using the A*
	 * search algorithm.
	 * 
	 * @param startNode - 
	 * @param endNode - 
	 * @param graph - 
	 * 
	 * @return A list of GraphNode objects ordered from startNode to endNode,
	 *  or null if a path was not found.
	 */
	static public List<GraphNode> findShortestPath(GraphNode startNode, GraphNode endNode, Graph graph) {
		
		Set<PathNode> pathSet = dijkstra(startNode, endNode, Integer.MAX_VALUE, graph);
		return pathSet.size() == 1 ? pathSet.iterator().next().getNodes() : null;
	}
	
	/**
	 * Get the lowest cost to traverse from a given start node to a given
	 * end node in a graph. If the cost is -1 that means no path exists
	 * between the two nodes. Uses the A* search algorithm.
	 * 
	 * @param startNode - 
	 * @param endNode - 
	 * @param graph - 
	 * 
	 * @return The cost from startNode to endNode, or -1 if no path exists.
	 */
	static public int findLowestPathCost(GraphNode startNode, GraphNode endNode, Graph graph) {
		
		Set<PathNode> pathSet = dijkstra(startNode, endNode, Integer.MAX_VALUE, graph);
		return pathSet.size() == 1 ? pathSet.iterator().next().getG() : -1;
	}
	
	/**
	 * Find all the nodes that are within a maximum distance from a given start
	 * node in a graph. Note, the start node is included in the result.
	 * 
	 * @param startNode - 
	 * @param maxDistance - 
	 * @param graph - 
	 * 
	 * @return A set of zero or more nodes from the graph.
	 */
	static public Set<GraphNode> findAllNodes(GraphNode startNode, int maxDistance, Graph graph) {
		
		Set<PathNode> pathSet = dijkstra(startNode, null, maxDistance, graph);
		
		Set<GraphNode> resultSet = new HashSet<GraphNode>(pathSet.size());
		
		for (PathNode pn : pathSet) {
			resultSet.add(pn.getGraphNode());
		}
		
		return resultSet;
	}
	
	/**
	 * A* path finding algorithm (a generalization of Dijkstra's algorithm).
	 * 
	 * @param startNode - Starting node for the search.
	 * @param endNode - End node, or null if searching for a set of nodes.
	 * @param maxDistance - Maximum distance (cost) of the returned node(s).
	 * @param graph - The graph containing the nodes.
	 * 
	 * @return A set of zero or more path nodes - if 'endNode' is not null then
	 *  the returned set will contain either zero or one path node.
	 */
	static HashSet<PathNode> dijkstra(
			GraphNode startNode, GraphNode endNode, int maxDistance, Graph graph) {
		
		PathNode startPathNode = new PathNode(startNode, null, graph);
		PathNode endPathNode = endNode == null ? null : new PathNode(endNode, endNode, graph);
		
		PathNodeSet closedSet = new PathNodeSet(false);
		PathNodeSet openSet = new PathNodeSet(true);
		
		// Add the starting node to the open set.
		openSet.add(startPathNode);
		
		// As long as the open set is not empty the path may yet be found.
		// When the endNode is found the path is found. If the open set
		// becomes empty then there is no path.
		while (openSet.size() > 0) {
			
			// Get the lowest F cost node in the open set.
			PathNode curNode = openSet.getFirst();
			
			// Path is found when the end node would be added to the closed set.
			if (endPathNode != null && curNode.equals(endPathNode)) {
				HashSet<PathNode> resultSet = new HashSet<PathNode>(1);
				resultSet.add(curNode);
				return resultSet;
			}
			
			// Move the node to the closed set.
			openSet.remove(curNode);
			closedSet.add(curNode);
			
			// For each node adjacent to the current node.
			Iterator<? extends GraphNode> neighborNodes =
					graph.getNeighborNodes(curNode.getGraphNode());
			
			while (neighborNodes.hasNext()) {
				GraphNode neighborNode = neighborNodes.next();
				
				// If it is in the closed set it has already been eliminated.
				if (closedSet.contains(neighborNode)) continue;
				
				// No need to skip curNode's own parent because it is already
				// in the closed set.
				
				PathNode openListNode = openSet.get(neighborNode);
				
				if (openListNode == null) {
					// If the neighbor node isn't in the open set then put
					// it there, making the current node its parent first
					// so the F cost can be calculated correctly.
					PathNode neighborPathNode = new PathNode(neighborNode, endNode, graph);
					neighborPathNode.setParent(curNode);
					
					if (neighborPathNode.getG() <= maxDistance) {
						openSet.add(neighborPathNode);
					}
				}
				else {
					// If the neighbor node is already in the open set then
					// check to see if the current path to it is better (has
					// a lower G cost) than the path leading to it now. If
					// the current path is better then update the node's
					// parent to add the node to the current path.
					int costToNeighbor = graph.getCostToNeighbor(
							curNode.getGraphNode(), neighborNode);
					
					if (costToNeighbor + curNode.getG() < openListNode.getG()) {
						// Setting a new parent will clear the cached cost
						// values for the PathNode. Remove the node from the
						// set and add it again so that the set will be
						// sorted correctly.
						openSet.remove(openListNode);
						openListNode.setParent(curNode);
						openSet.add(openListNode);
					}
				}
			}
		}
		
		// All nodes that make it into the closed set have a path within
		// max distance of the start node.
		HashSet<PathNode> resultSet = new HashSet<PathNode>(closedSet.size());
		Iterator<PathNode> it = closedSet.iterator();
		while (it.hasNext()) {
			resultSet.add(it.next());
		}
		
		return resultSet;
	}
	
}
