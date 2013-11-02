/*
 * Created on Nov 3, 2006
 */
package com.law.astar;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * A set of path nodes. The set may, optionally, be sorted according to
 * the natural sort order of PathNode (lowest F cost to highest F cost).
 * If its not sorted the nodes are kept in the order they are added.
 * In either case, contains() and remove() are fast (not linear).
 *
 * @author lweber
 */
public class PathNodeSet {
	
	private final boolean isSorted;
	
	private TreeSet<PathNode> sortedNodes;
	private LinkedHashMap<GraphNode, PathNode> linkedAndHashedNodes =
			new LinkedHashMap<GraphNode, PathNode>();
	
	/**
	 * Construct a new sorted or unsorted PathNodeSet. Sorted sets are
	 * sorted from lowest F cost to highest F cost. Unsorted sets keep
	 * nodes in the order they are added.
	 *
	 * @param sortNodes - whether the nodes in the set should be kept
	 *  sorted or not.
	 */
	PathNodeSet(boolean sortNodes) {
		
		isSorted = sortNodes;
		
		if (isSorted) {
			sortedNodes = new TreeSet<PathNode>();
		}
	}
	
	/**
	 * Determine whether this set contains a PathNode for the
	 * given GraphNode. This is a hash lookup so its fast.
	 *
	 * @param gn -
	 *
	 * @return
	 */
	boolean contains(GraphNode gn) {
		return linkedAndHashedNodes.containsKey(gn);
	}
	
	/**
	 * Get the PathNode for a given GraphNode. This is a hash lookup
	 * so its fast.
	 *
	 * @param gn -
	 *
	 * @return The PathNode, or null if not found.
	 */
	PathNode get(GraphNode gn) {
		return linkedAndHashedNodes.get(gn);
	}
	
	/**
	 * Get the first PathNode in the set. This operation makes more sense
	 * on a sorted set, but does work on an unsorted set.
	 *
	 * @return The first node, or null if there are none.
	 */
	PathNode getFirst() {
		if (size() == 0) {
			return null;
		}
		else if (isSorted) {
			return sortedNodes.first();
		}
		else {
			return linkedAndHashedNodes.entrySet().iterator().next().getValue();
		}
	}
	
	/**
	 * Note, Node's are comparable so sortedNodes is always
	 * sorted from lowest F cost to highest F cost.
	 *
	 * @param pn -
	 */
	void add(PathNode pn) {
		if (isSorted) {
			sortedNodes.add(pn);
		}
		
		linkedAndHashedNodes.put(pn.getGraphNode(), pn);
		
		// Sanity check.
		if (isSorted && !(sortedNodes.size() == linkedAndHashedNodes.size())) {
			throw new RuntimeException(
					"Problem in sorted PathNodeSet: unequal collection sizes after add().");
		}
	}
	
	/**
	 * Remove a node from the set.
	 * 
	 * @param n -
	 * 
	 * @return The PathNode that was removed, or null if it was not there.
	 */
	PathNode remove(PathNode n) {
		if (isSorted) {
			sortedNodes.remove(n);
		}
		
		PathNode removedNode =
			linkedAndHashedNodes.remove(n.getGraphNode());
		
		// Sanity check.
		if (isSorted && !(sortedNodes.size() == linkedAndHashedNodes.size())) {
			throw new RuntimeException(
					"Problem in sorted PathNodeSet: unequal collection sizes after remove().");
		}
		
		return removedNode;
	}
	
	/**
	 * Get the number of nodes in the set.
	 * 
	 * @return
	 */
	int size() {
		return linkedAndHashedNodes.size();
	}
	
	/**
	 * Get the PathNodes in this set. The nodes will be iterated in sorted
	 * order if this set is a sorted set, or in the order they were added
	 * to this set if its not sorted.
	 *
	 * @return An iterator of PathNode objects.
	 */
	public Iterator<PathNode> iterator() {
		if (isSorted) {
			return sortedNodes.iterator();
		}
		else {
			return linkedAndHashedNodes.values().iterator();
		}
	}
	
	@Override
	public String toString() {
		if (isSorted) {
			return "Sorted: " + sortedNodes.toString();
		}
		else {
			return "Unsorted: " + linkedAndHashedNodes.values().toString();
		}
	}
	
	/**
	 * Get the PathNodes in this set as an array. The nodes will be in sorted
	 * order if this set is a sorted set, or in the order they were added
	 * to this set if its not sorted.
	 *
	 * @return
	 */
	PathNode [] toArray() {
		if (isSorted) {
			PathNode [] result = new PathNode [sortedNodes.size()];
			return sortedNodes.toArray(result);
		}
		else {
			// TODO does this return the nodes in the correct order?
			PathNode [] result = new PathNode [linkedAndHashedNodes.size()];
			return linkedAndHashedNodes.values().toArray(result);
		}
	}
	
}
