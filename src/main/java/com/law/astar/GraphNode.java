/*
 * Created on Nov 3, 2006
 */
package com.law.astar;

/**
 * Interface for a graph node which implements methods required to
 * be a node in a graph which supports the A* algorithm.
 * 
 * <p> GraphNodes must override equals() and hashCode() so that they
 * may be stored in HashSet and other Set collections.
 *
 * @author lweber
 */
public interface GraphNode {
	
	/**
	 * Get the name of this node. Node names must be unique within
	 * a given Graph so that equals() and hashCode() may use them.
	 * 
	 * @return
	 */
	String getNodeName();
	
	/**
	 * Enables storing nodes in hash sets. Generally implemented as
	 * getNodeName().equals((GraphNode)obj.getNodeName()).
	 * 
	 * @param obj - 
	 * 
	 * @return
	 */
	boolean equals(Object obj);
	
	/**
	 * Enables storing nodes in hash sets. Generally implemented as
	 * getNodeName().hashCode(); in order to be consistent with
	 * equals().
	 * 
	 * @return
	 */
	int hashCode();
	
}
