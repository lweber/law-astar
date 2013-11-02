/*
 * Created on Nov 3, 2006
 */
package com.law.astar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
		
		HashSet<PathNode> pathSet = dijkstra(startNode, endNode, Integer.MAX_VALUE, graph);
		return pathSet.size() == 1 ? pathSet.iterator().next().getNodes() : null;
		
//		long time1 = System.nanoTime();
//		PathNode pn = findPathNode(startNode, endNode, graph);
//		time1 = System.nanoTime() - time1;
//		
//		// Teporary test code to make sure dijkstra() is operating correctly.
//		long time2 = System.nanoTime();
//		HashSet<PathNode> pathSet = dijkstra(startNode, endNode, Integer.MAX_VALUE, graph);
//		time2 = System.nanoTime() - time2;
//		PathNode pn2 = pathSet.size() == 1 ? pathSet.iterator().next() : null;
//		if ((pn == null) != (pn2 == null)) {
//			System.out.println("Error: findPathNode=" + pn + ", dijkstra=" + pn2);
//		}
//		if (pn != null) {
//			List<GraphNode> l1 = pn.getPath();
//			List<GraphNode> l2 = pn2.getPath();
//			
//			BigDecimal t1 = BigDecimal.valueOf(time1).divide(BigDecimal.valueOf(1000000));
//			BigDecimal t2 = BigDecimal.valueOf(time2).divide(BigDecimal.valueOf(1000000));
//			System.out.println((l1.containsAll(l2) && l2.containsAll(l1)
//					&& l1.size() == l2.size()) +
//					": time1=" + t1 + "ms, time2=" + t2 + "ms");
//		}
//		
//		return pn != null ? pn.getPath() : null;
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
		
		HashSet<PathNode> pathSet = dijkstra(startNode, endNode, Integer.MAX_VALUE, graph);
		return pathSet.size() == 1 ? pathSet.iterator().next().getG() : -1;
		
//		PathNode pn = findPathNode(startNode, endNode, graph);
//		
//		// Teporary test code to make sure dijkstra() is operating correctly.
//		int getg = pn != null ? pn.getG() : -1;
//		HashSet<PathNode> pathSet = dijkstra(startNode, endNode, Integer.MAX_VALUE, graph);
//		int cost = pathSet.size() == 1 ? pathSet.iterator().next().getG() : -1;
//		if (cost != getg) {
//			System.out.println("Error: cost != pn.getG() : cost=" + cost + "pn.getG()=" + getg);
//		}
//		
//		return pn != null ? pn.getG() : -1;
	}
	
	/**
	 * Find all the nodes that are within a maximum distance from a given start
	 * node in a graph.
	 * 
	 * @param startNode - 
	 * @param maxDistance - 
	 * @param graph - 
	 * 
	 * @return A set of zero or more nodes from the graph.
	 */
	static public HashSet<GraphNode> findAllNodes(GraphNode startNode, int maxDistance, Graph graph) {
		
		HashSet<PathNode> pathSet = dijkstra(startNode, null, maxDistance, graph);
		
		HashSet<GraphNode> resultSet = new HashSet<GraphNode>(pathSet.size());
		
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
		
		PathNode startPathNode = new PathNode(startNode, null);
		PathNode endPathNode = endNode == null ? null : new PathNode(endNode, endNode);
		
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
					PathNode neighborPathNode = new PathNode(neighborNode, endNode);
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
	
//	/**
//	 * A* path finding algorithm.
//	 * 
//	 * @param startNode - 
//	 * @param endNode - 
//	 * @param graph - 
//	 * 
//	 * @return
//	 */
//	static PathNode findPathNode(GraphNode startNode, GraphNode endNode, Graph graph) {
//		
//		PathNode startPathNode = new PathNode(startNode, endNode);
//		PathNode endPathNode = new PathNode(endNode, endNode);
//		
//		PathNodeSet closedSet = new PathNodeSet(false);
//		PathNodeSet openSet = new PathNodeSet(true);
//		
//		// Add the starting node to the open set.
//		openSet.add(startPathNode);
//		
//		// As long as the open set is not empty the path may yet be found.
//		// When the endNode is found the path is found. If the open set
//		// becomes empty then there is no path.
//		while (openSet.size() > 0) {
//			
//			// Get the lowest F cost node in the open set.
//			PathNode curNode = openSet.getFirst();
//			
//			// Path is found when the end node would be added to the closed set.
//			if (curNode.equals(endPathNode)) {
//				return curNode;
//			}
//			
//			// Move the node to the closed set.
//			openSet.remove(curNode);
//			closedSet.add(curNode);
//			
//			// For each node adjacent to the current node.
//			Iterator<GraphNode> neighborNodes = graph.getNeighborNodes(curNode.getGraphNode());
//			
//			while (neighborNodes.hasNext()) {
//				GraphNode neighborNode = neighborNodes.next();
//				
//				// If it is in the closed set it has already been eliminated.
//				if (closedSet.contains(neighborNode)) continue;
//				
//				// No need to skip curNode's own parent because it is already
//				// in the closed set.
//				
//				PathNode openListNode = openSet.get(neighborNode);
//				
//				if (openListNode == null) {
//					// If the neighbor node isn't in the open set then put
//					// it there, making the current node its parent first
//					// so the F cost can be calculated correctly.
//					PathNode neighborPathNode = new PathNode(neighborNode, endNode);
//					neighborPathNode.setParent(curNode);
//					openSet.add(neighborPathNode);
//				}
//				else {
//					// If the neighbor node is already in the open set then
//					// check to see if the current path to it is better (has
//					// a lower G cost) than the path leading to it now. If
//					// the current path is better then update the node's
//					// parent to add the node to the current path.
//					int costToNeighbor = graph.getCostToNeighbor(
//							curNode.getGraphNode(), neighborNode);
//					
//					if (costToNeighbor + curNode.getG() < openListNode.getG()) {
//						// Setting a new parent will clear the cached cost
//						// values for the PathNode. Remove the node from the
//						// set and add it again so that the set will be
//						// sorted correctly.
//						openSet.remove(openListNode);
//						openListNode.setParent(curNode);
//						openSet.add(openListNode);
//					}
//				}
//			}
//		}
//		
//		return null;
//	}
	
}
