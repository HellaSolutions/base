package it.hella.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Optional;

public abstract class AbstractBinarySearchTree<T extends Comparable<T>, N extends NodeInterface<T, N>>
		implements BinaryTree<T, N> {

	protected Optional<N> root = Optional.absent();

	protected abstract N getNode(T key);

	@Override
	public List<T> asSortedList() {

		List<T> sorted = new ArrayList<>();
		buildSortedList(root, sorted);
		return sorted;

	}

	@Override
	public Optional<N> search(T key) {
		Optional<N> x = root;
		N parent = null;
		while (x.isPresent()) {
			parent = x.get();
			int comparison = key.compareTo(parent.getKey());
			if (comparison == 0) {
				return x;
			} else if (comparison > 0) {
				x = parent.getRightChild();
			} else {
				x = parent.getLeftChild();
			}
		}
		return Optional.absent();

	}

	@Override
	public Optional<N> getRoot() {
		return root;
	}

	@Override
	public void clear() {
		root = Optional.absent();

	}

	@Override
	public Optional<N> getMinimum() {
		if (!root.isPresent()) {
			return root;
		}
		return getMinimum(root.get());
	}

	@Override
	public Optional<N> getMaximum() {
		if (!root.isPresent()) {
			return root;
		}
		return getMaximum(root.get());
	}

	@Override
	public boolean add(T key) {

		return insertNode(key).isPresent();

	}

	protected Optional<N> insertNode(T key) {

		Optional<N> x = root;
		Boolean right = false;
		N parent = null;
		while (x.isPresent()) {
			parent = x.get();
			int comparison = key.compareTo(parent.getKey());
			if (comparison == 0) {
				return Optional.absent();
			} else if (comparison > 0) {
				x = parent.getRightChild();
				right = true;
			} else {
				x = parent.getLeftChild();
				right = false;
			}
		}
		N node = getNode(key);
		if (parent == null) {
			root = Optional.of(node);
		} else {
			if (right) {
				parent.setRightChild(node);
			} else {
				parent.setLeftChild(node);
			}
		}
		return Optional.of(node);

	}

	@Override
	public void remove(N z) {

		boolean leftPresent = z.getRightChild().isPresent();
		boolean rightPresent = z.getRightChild().isPresent();
		if (!(leftPresent && rightPresent)) {
			Optional<N> optSubstitute = rightPresent ? z.getRightChild() : z.getLeftChild();
			if (optSubstitute.isPresent()) {
				transplant(z, optSubstitute);
			}
		} else {
			Optional<N> zMinimum = getMinimum(z.getRightChild().get());
			if (!zMinimum.isPresent()) {
				throw new IllegalStateException("getMinimum returned an empty value");
			}
			N substitute = zMinimum.get();
			N parent = substitute.getParent().get();
			if (!parent.equals(z)) {
				parent.setLeftChild(substitute.getRightChild());
				substitute.setRightChild(z.getRightChild());
			}
			substitute.setLeftChild(z.getLeftChild());
			transplant(z, zMinimum);

		}

	}

	@Override
	public String toString() {

		StringBuilder b = new StringBuilder();
		print(root, b);
		return b.toString();

	}

	protected Optional<N> getMinimum(N z) {

		Optional<N> x = Optional.of(z);
		while (x.get().getLeftChild().isPresent()) {
			x = x.get().getLeftChild();
		}
		return x;

	}

	protected Optional<N> getMaximum(N z) {

		Optional<N> x = Optional.of(z);
		while (x.get().getRightChild().isPresent()) {
			x = x.get().getRightChild();
		}
		return x;

	}

	protected void buildSortedList(Optional<N> root, List<T> list) {

		if (root.isPresent()) {
			N x = root.get();
			buildSortedList(x.getLeftChild(), list);
			list.add(x.getKey());
			buildSortedList(x.getRightChild(), list);
		}

	}

	protected void transplant(N out, N in) {
		transplant(out, Optional.of(in));
	}

	protected void transplant(N out, Optional<N> in) {

		Optional<N> oParent = out.getParent();
		if (!oParent.isPresent()) {
			root = in;
			if (in.isPresent()) {
				in.get().setParent(Optional.absent());
			}
			return;
		}
		N parent = oParent.get();
		if (out.isRightChild()) {
			parent.setRightChild(in);
		} else {
			parent.setLeftChild(in);
		}
		out.setParent(Optional.absent());

	}

	protected void leftRotate(N pivot) {

		if (pivot.getRightChild().isPresent()) {
			N pivotRightChild = pivot.getRightChild().get();
			Optional<N> pivotRightChildLeftChild = pivotRightChild.getLeftChild();
			transplant(pivot, pivotRightChild);
			pivotRightChild.setLeftChild(pivot);
			pivot.setRightChild(pivotRightChildLeftChild);
		}

	}

	protected void rightRotate(N pivot) {

		if (pivot.getLeftChild().isPresent()) {
			N pivotLeftChild = pivot.getLeftChild().get();
			Optional<N> pivotLeftChildRightChild = pivotLeftChild.getRightChild();
			transplant(pivot, pivotLeftChild);
			pivotLeftChild.setRightChild(pivot);
			pivot.setLeftChild(pivotLeftChildRightChild);
		}

	}

	protected void print(Optional<N> root, StringBuilder b) {

		LinkedList<Optional<N>> actualLevel = new LinkedList<>();
		LinkedList<Optional<N>> nextLevel = new LinkedList<>();
		nextLevel.add(root);
		do {
			actualLevel.addAll(nextLevel);
			nextLevel.clear();
			while (!actualLevel.isEmpty()) {
				Optional<N> x = actualLevel.pop();
				if (x.isPresent()) {
					b.append(x.get() + ", ");
					nextLevel.addLast(x.get().getLeftChild());
					nextLevel.addLast(x.get().getRightChild());
				} else {
					b.append("nil, ");
				}
			}
			b.append("\n");
		} while (!nextLevel.isEmpty());

	}

}
