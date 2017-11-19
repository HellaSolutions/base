package it.hella.graphs.shortestpath;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import it.hella.graphs.DirectedEdge;
import it.hella.graphs.DirectedGraph;

public class DijkstraTests {
	
	@Test
	public void testShortestPath(){
		
		DirectedGraph graph = new DirectedGraph(8);
		graph.addEdge(new DirectedEdge(4, 5, 35));
		graph.addEdge(new DirectedEdge(5, 4, 35));
		graph.addEdge(new DirectedEdge(4, 7, 37));
		graph.addEdge(new DirectedEdge(5, 7, 28));
		graph.addEdge(new DirectedEdge(7, 5, 28));
		graph.addEdge(new DirectedEdge(5, 1, 32));
		graph.addEdge(new DirectedEdge(0, 4, 38));
		graph.addEdge(new DirectedEdge(0, 2, 26));
		graph.addEdge(new DirectedEdge(7, 3, 39));
		graph.addEdge(new DirectedEdge(1, 3, 29));
		graph.addEdge(new DirectedEdge(2, 7, 34));
		graph.addEdge(new DirectedEdge(6, 2, 40));
		graph.addEdge(new DirectedEdge(3, 6, 52));
		graph.addEdge(new DirectedEdge(6, 0, 58));
		graph.addEdge(new DirectedEdge(6, 4, 93));
		Dijkstra d = new Dijkstra(graph, 0);
		List<LinkedList<DirectedEdge>> list = d.pathTo;
		assertThat(d.getDistTo(), contains(0.0, 105.0, 26.0, 99.0, 38.0, 73.0, 151.0, 60.0));
		
	}

}
