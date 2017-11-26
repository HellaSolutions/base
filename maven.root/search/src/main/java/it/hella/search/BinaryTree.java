package it.hella.search;

import java.util.UUID;

import com.google.common.base.Optional;

public interface BinaryTree<T extends Comparable<T>> {

	boolean add(T key);

	void remove(Node<T> node);

	Optional<Node<T>> search(T key);

	Optional<Node<T>> getRoot();

	void clear();

	public static class Node<T extends Comparable<T>>{

		private UUID nodeId = UUID.randomUUID();
		private Optional<Node<T>> leftChild = Optional.absent();
		private Optional<Node<T>> rightChild = Optional.absent();
		private Optional<Node<T>> parent = Optional.absent();
		private T key;

		public Node(T key) {
			this.key = key;
		}

		public T getKey() {
			return key;
		}

		public void setKey(T key) {
			this.key = key;
		}

		public Optional<Node<T>> getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(Node<T> leftChild) {
			setLeftChild(Optional.of(leftChild));
		}

		protected final void setLeftChild(Optional<Node<T>> leftChild) {
			this.leftChild = leftChild;
			if (leftChild.isPresent()){
				leftChild.get().setParent(this);
			}
		}

		public Optional<Node<T>> getRightChild() {
			return rightChild;
		}

		public void setRightChild(Node<T> rightChild) {
			setRightChild(Optional.of(rightChild));
		}

		protected final void setRightChild(Optional<Node<T>> rightChild) {
			this.rightChild = rightChild;
			if (rightChild.isPresent()){
				rightChild.get().setParent(this);
			}
		}

		public Optional<Node<T>> getParent() {
			return parent;
		}

		public void setParent(Node<T> parent) {
			this.parent = Optional.of(parent);
		}

		protected final void setParent(Optional<Node<T>> parent) {
			this.parent = parent;
		}

		@Override
		public String toString() {
			return "Node [key=" + key + ", leftChild=" + (leftChild.isPresent() ? leftChild.get().getKey() : "nil")
					+ ", rightChild=" + (rightChild.isPresent() ? rightChild.get().getKey() : "nil") + ", parent="
					+ (parent.isPresent() ? parent.get().getKey() : "nil") + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
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
			Node other = (Node) obj;
			if (nodeId == null) {
				if (other.nodeId != null)
					return false;
			} else if (!nodeId.equals(other.nodeId))
				return false;
			return true;
		}

		
	}

}
