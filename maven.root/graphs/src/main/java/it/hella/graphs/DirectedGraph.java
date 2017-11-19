package it.hella.graphs;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraph {
	
	private List<List<DirectedEdge>> vertices;
	private int numberEdges = 0;
	
	public DirectedGraph(int vertexNumber){		
		vertices = new ArrayList<List<DirectedEdge>>();
		for (int k = 0; k < vertexNumber; k++){
			vertices.add(new ArrayList<DirectedEdge>());
		}
	}
	
	public List<DirectedEdge> adjacentTo(int v){
		return vertices.get(v);
	}
	public void addEdge(DirectedEdge e){
		vertices.get(e.getFrom()).add(e);
		numberEdges++;
	}
	public int getVertexNumber(){
		return vertices.size();
	}
	public int getEdgesNumber(){
		return numberEdges++;
	}

}
