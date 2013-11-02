/*
 * Created on Nov 3, 2006
 */
package com.law.astar;

/**
 * Interface for a graph node which implements methods required to
 * be a node in a graph which supports the A* algorithm.
 * 
 * <p> GraphNodes must be able to provide the Graph that they belong to.
 *
 * <p> GraphNodes must override equals() and hashCode() so that they
 * may be stored in HashSet and other Set collections.
 *
 * @author lweber
 */
public interface GraphNode {
	
	/**
	 * Get the graph that contains this node.
	 * 
	 * @return
	 */
	public Graph getGraph();
	
	/**
	 * Get the name of this node - node names must be unique within
	 * a given Graph so that equals() and hashCode() may use them.
	 * 
	 * @return
	 */
	public String getNodeName();
	
	/**
	 * Enables storing nodes in hash sets. Generally implemented as
	 * getNodeName().equals((GraphNode)obj.getNodeName()).
	 * 
	 * @param obj - 
	 * 
	 * @return
	 */
	public boolean equals(Object obj);
	
	/**
	 * Enables storing nodes in hash sets. Generally implemented as
	 * getNodeName().hashCode(); in order to be consistent with
	 * equals().
	 * 
	 * @return
	 */
	public int hashCode();
	
}
