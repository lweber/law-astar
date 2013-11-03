A* (A-Star) search algorithm

This is an implimentation of the A* (A-Star) search algorithm, based on
Dijkstra's algorithm, that is written to Java interfaces so it's easy for
you to integrate with your existing code. Just implement these two
interfaces and you're all set:

Graph {
	Iterator<? extends GraphNode> getNeighborNodes(GraphNode n);
	int getCostToNeighbor(GraphNode n1, GraphNode n2);
	int estimateCostToEnd(GraphNode startNode, GraphNode endNode);
}

GraphNode {
	String getNodeName();
	boolean equals(Object obj);
	int hashCode();
}

Once you've done this the following methods on the PathFinder class will be
available to you:

List<GraphNode> findShortestPath(GraphNode startNode, GraphNode endNode, Graph graph);
int findLowestPathCost(GraphNode startNode, GraphNode endNode, Graph graph);
Set<GraphNode> findAllNodes(GraphNode startNode, int maxDistance, Graph graph);
