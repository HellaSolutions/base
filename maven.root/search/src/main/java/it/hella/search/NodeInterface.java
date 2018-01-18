package it.hella.search;

import java.util.UUID;

import com.google.common.base.Optional;

public interface NodeInterface<T extends Comparable<T>, N extends NodeInterface<T, N>> {

	T getKey();

	void setKey(T key);

	UUID getId();

	Optional<N> getLeftChild();

	void setLeftChild(N leftChild);

	void setLeftChild(Optional<N> leftChild);

	Optional<N> getRightChild();

	void setRightChild(N rightChild);

	void setRightChild(Optional<N> rightChild);

	Optional<N> getParent();

	void setParent(N parent);

	void setParent(Optional<N> parent);

	boolean isRightChild();

	boolean isLeftChild();

}
