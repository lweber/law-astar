package com.law.astar.graph;

import com.law.astar.GraphNode;

/**
 * @author lweber
 */
public class WeightedEdge extends Edge {
	
	private int weight;
	
	public WeightedEdge(GraphNode n1, GraphNode n2, int weight) {
		super(n1, n2);
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + weight;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeightedEdge other = (WeightedEdge) obj;
		if (weight != other.weight)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + getN1() + ")-" + weight + "-(" + getN2() + ")";
	}
	
}
