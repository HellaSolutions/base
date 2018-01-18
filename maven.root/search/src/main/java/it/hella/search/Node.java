package it.hella.search;

public class Node<T extends Comparable<T>> extends AbstractNode<T, Node<T>> {

	public Node(T key) {
		super(key);
	}

}
