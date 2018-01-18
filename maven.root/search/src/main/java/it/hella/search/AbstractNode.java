package it.hella.search;

import java.util.UUID;

import com.google.common.base.Optional;

public abstract class AbstractNode<T extends Comparable<T>, N extends NodeInterface<T, N>>
		implements NodeInterface<T, N> {

	protected UUID nodeId = UUID.randomUUID();
	protected Optional<N> leftChild = Optional.absent();
	protected Optional<N> rightChild = Optional.absent();
	protected Optional<N> parent = Optional.absent();
	protected T key;

	public AbstractNode(T key) {
		this.key = key;
	}

	@Override
	public T getKey() {
		return key;
	}

	@Override
	public void setKey(T key) {
		this.key = key;
	}

	@Override
	public UUID getId() {
		return this.nodeId;
	}

	@Override
	public Optional<N> getLeftChild() {
		return leftChild;
	}

	@Override
	public void setLeftChild(N leftChild) {
		setLeftChild(Optional.of(leftChild));
	}

	@Override
	@SuppressWarnings("unchecked")
	public final void setLeftChild(Optional<N> leftChild) {
		this.leftChild = leftChild;
		if (leftChild.isPresent()) {
			leftChild.get().setParent((N) this);
		}
	}

	@Override
	public Optional<N> getRightChild() {
		return rightChild;
	}

	@Override
	public void setRightChild(N rightChild) {
		setRightChild(Optional.of(rightChild));
	}

	@Override
	@SuppressWarnings("unchecked")
	public final void setRightChild(Optional<N> rightChild) {
		this.rightChild = rightChild;
		if (rightChild.isPresent()) {
			rightChild.get().setParent((N) this);
		}
	}

	@Override
	public Optional<N> getParent() {
		return parent;
	}

	@Override
	public void setParent(N parent) {
		this.parent = Optional.of(parent);
	}

	@Override
	public final void setParent(Optional<N> parent) {
		this.parent = parent;
	}

	@Override
	public boolean isRightChild() {

		if (parent.isPresent() && parent.get().getRightChild().isPresent()) {
			return parent.get().getRightChild().get().equals(this);
		}
		return false;

	}

	@Override
	public boolean isLeftChild() {
		if (parent.isPresent() && parent.get().getLeftChild().isPresent()) {
			return parent.get().getLeftChild().get().equals(this);
		}
		return false;
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		N other = (N) obj;
		if (nodeId == null) {
			if (other.getId() != null)
				return false;
		} else if (!nodeId.equals(other.getId()))
			return false;
		return true;
	}

}
