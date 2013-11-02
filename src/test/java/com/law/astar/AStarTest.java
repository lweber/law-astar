package com.law.astar;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.law.astar.graph.GraphFactory;

/**
 * @author lweber
 */
public class AStarTest {
	
	private Graph graph;
	private GraphNode n1;
	private GraphNode n2;
	private GraphNode n4;
	private GraphNode n5;
	
	@Before
	public void setup() {
		graph = GraphFactory.createUndirectedWeightedGraph();
		n1 = GraphFactory.createGraphNode("n1");
		n2 = GraphFactory.createGraphNode("n2");
		n4 = GraphFactory.createGraphNode("n4");
		n5 = GraphFactory.createGraphNode("n5");
	}
	
	@Test
	public void findShortestPath() {
		List<GraphNode> shortestPath = PathFinder.findShortestPath(n1, n4, graph);
		assertArrayEquals(new GraphNode[] { n1, n2, n4 }, shortestPath.toArray());
	}
	
	@Test
	public void findLowestPathCost() {
		int cost = PathFinder.findLowestPathCost(n1, n4, graph);
		assertEquals(4, cost);
	}
	
	@Test
	public void findLowestPathCost_noPathExists() {
		int cost = PathFinder.findLowestPathCost(n1, n5, graph);
		assertEquals(-1, cost);
	}
	
	@Test
	public void findAllNodes() {
		Set<GraphNode> nodes = PathFinder.findAllNodes(n2, 3, graph);
		assertEquals(3, nodes.size());
		assertTrue(nodes.containsAll(Arrays.asList(n1, n2, n4)));
	}
	
}
