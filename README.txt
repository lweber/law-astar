This is an implimentation of the A* (A-Star) search algorithm that is written
to Java interfaces so it's easy for you to use it with your existing graph and
graph node classes. Just make your existing classes implement the following two
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

Once you have this, the following methods on the PathFinder class will be
available to you:

List<GraphNode> findShortestPath(GraphNode startNode, GraphNode endNode, Graph graph);
int findLowestPathCost(GraphNode startNode, GraphNode endNode, Graph graph);
Set<GraphNode> findAllNodes(GraphNode startNode, int maxDistance, Graph graph);

