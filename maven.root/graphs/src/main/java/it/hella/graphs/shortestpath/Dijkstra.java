package it.hella.graphs.shortestpath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.hella.graphs.DirectedEdge;
import it.hella.graphs.DirectedGraph;

public class Dijkstra{
	
	double[] distTo;
	List<LinkedList<DirectedEdge>> pathTo;

	Dijkstra(DirectedGraph graph, int source){
	
		distTo = new double[graph.getVertexNumber()];
		pathTo = new ArrayList<>();
		for (int i = 0; i < distTo.length; i++){
			if (i == source){
				distTo[i] = 0;
			}else{
				distTo[i] = -1;
			}
			pathTo.add(new LinkedList<DirectedEdge>());
		}
		LinkedList<Integer> stack = new LinkedList<Integer>();
		stack.push(source);
		while (!stack.isEmpty()){
			List<DirectedEdge> adjacent = graph.adjacentTo(stack.pop());
			for(DirectedEdge e : adjacent){
				BigDecimal bdDistTo = new BigDecimal(String.valueOf(distTo[e.getFrom()]));
				BigDecimal distance = bdDistTo.add(new BigDecimal(String.valueOf(e.getWeight())));
				boolean unset = distTo[e.getTo()] < 0;
				if (unset || new BigDecimal(distTo[e.getTo()]).compareTo(distance) > 0){
					distTo[e.getTo()] = distance.doubleValue();
					pathTo.get(e.getTo()).clear();
					pathTo.get(e.getTo()).addAll(pathTo.get(e.getFrom()));
					pathTo.get(e.getTo()).addLast(e);
					stack.push(e.getTo());
				}
				
			}
			
		}
		
		
	}
	public double getDistTo(int v) {
		return distTo[v];
	}
	public List<DirectedEdge> getPathTo(int v) {
		return pathTo.get(v);
	}
	public boolean hasPathTo(int v) {
		return distTo[v] > 0;
	}
	
	List<Double> getDistTo() {
		List<Double> res = new ArrayList<>();
		for (int k = 0; k < distTo.length; k++){
			res.add(distTo[k]);
		}
		return res;
	}

}
