package it.hella.search;

public class BinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T, Node<T>> {

	@Override
	protected Node<T> getNode(T key) {
		return new Node<>(key);
	}

}
