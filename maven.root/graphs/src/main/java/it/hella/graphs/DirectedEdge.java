package it.hella.graphs;

public class DirectedEdge {
	
	int from;
	int to;
	double weight;
	
	public DirectedEdge(){}
		
	public DirectedEdge(int from, int to, double weight) {
		super();
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "DirectedEdge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
	}

}
