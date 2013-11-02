package com.law.astar.graph;

import com.law.astar.GraphNode;

/**
 * @author lweber
 */
public class Edge {
	
	private final GraphNode n1;
	private final GraphNode n2;
	
	Edge(GraphNode n1, GraphNode n2) {
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public GraphNode getN1() {
		return n1;
	}
	
	public GraphNode getN2() {
		return n2;
	}
	
	public boolean connects(GraphNode n1, GraphNode n2) {
		return (this.n1.equals(n1) && this.n2.equals(n2)) || (this.n1.equals(n2) && this.n2.equals(n1));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((n1 == null) ? 0 : n1.hashCode());
		result = prime * result + ((n2 == null) ? 0 : n2.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (n1 == null) {
			if (other.n1 != null)
				return false;
		}
		else if (!n1.equals(other.n1))
			return false;
		if (n2 == null) {
			if (other.n2 != null)
				return false;
		}
		else if (!n2.equals(other.n2))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + getN1() + ")-(" + getN2() + ")";
	}
	
}
