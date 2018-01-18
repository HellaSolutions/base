package it.hella.search;

import java.util.List;

import com.google.common.base.Optional;

public interface BinaryTree<T extends Comparable<T>, N extends NodeInterface<T, N>> {

	boolean add(T key);

	void remove(N node);

	Optional<N> search(T key);

	Optional<N> getRoot();

	void clear();

	Optional<N> getMinimum();

	Optional<N> getMaximum();

	List<T> asSortedList();

}
