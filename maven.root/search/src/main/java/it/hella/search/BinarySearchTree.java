package it.hella.search;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Optional;

public class BinarySearchTree<T extends Comparable<T>> implements BinaryTree<T> {

	private Optional<Node<T>> root = Optional.absent();

	@Override
	public boolean add(T key) {

		Optional<Node<T>> x = root;
		Boolean right = false;
		Node<T> parent = null;
		while (x.isPresent()) {
			parent = x.get();
			int comparison = key.compareTo(parent.getKey());
			if (comparison == 0) {
				return false;
			} else if (comparison > 0) {
				x = parent.getRightChild();
				right = true;
			} else {
				x = parent.getLeftChild();
				right = false;
			}
		}
		Node<T> node = new Node<>(key);
		if (parent == null) {
			root = Optional.of(node);
		} else {
			if (right) {
				parent.setRightChild(node);
			} else {
				parent.setLeftChild(node);
			}
		}
		return true;

	}

	public Optional<Node<T>> getMinimum() {
		if (!root.isPresent()) {
			return root;
		}
		return getMinimum(root.get());
	}

	public Optional<Node<T>> getMaximum() {
		if (!root.isPresent()) {
			return root;
		}
		return getMaximum(root.get());
	}

	private Optional<Node<T>> getMinimum(Node<T> z) {

		Optional<Node<T>> x = Optional.of(z);
		while (x.get().getLeftChild().isPresent()) {
			x = x.get().getLeftChild();
		}
		return x;

	}

	private Optional<Node<T>> getMaximum(Node<T> z) {

		Optional<Node<T>> x = Optional.of(z);
		while (x.get().getRightChild().isPresent()) {
			x = x.get().getRightChild();
		}
		return x;

	}

	@Override
	public void remove(Node<T> z) {

		if (!z.getLeftChild().isPresent()) {
			transplant(z, z.getRightChild());
		} else if (!z.getRightChild().isPresent()) {
			transplant(z, z.getLeftChild());
		} else {
			Optional<Node<T>> zMinimum = getMinimum(z.getRightChild().get());
			if (!zMinimum.isPresent()) {
				return;
			}
			Node<T> substitute = zMinimum.get();
			Node<T> parent = substitute.getParent().get();
			if (!parent.equals(z)){
				parent.setLeftChild(substitute.getRightChild());
				substitute.setRightChild(z.getRightChild());
			}
			substitute.setLeftChild(z.getLeftChild());			
			if (z.getParent().isPresent()){
				parent = z.getParent().get();
				if (parent.getLeftChild().isPresent() && parent.getLeftChild().get().equals(z)){
					parent.setLeftChild(zMinimum);
				}else{
					parent.setRightChild(zMinimum);
				}
			}else{
				root = zMinimum;
				zMinimum.get().setParent(Optional.absent());	
			}
		
		}

	}

	private void transplant(Node<T> out, Optional<Node<T>> in) {

		if (!out.getParent().isPresent()) {
			root = in;
			if (in.isPresent()){
				in.get().setParent(Optional.absent());
			}
			return;
		}
		Node<T> parent = out.getParent().get();
		if (parent.getLeftChild().isPresent() && parent.getLeftChild().get().equals(out)) {
			parent.setLeftChild(in);
		} else {
			parent.setRightChild(in);
		}

	}

	@Override
	public Optional<Node<T>> search(T key) {
		Optional<Node<T>> x = root;
		Node<T> parent = null;
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
	public Optional<Node<T>> getRoot() {
		return root;
	}

	@Override
	public void clear() {
		root = Optional.absent();

	}

	public void sort(Optional<Node<T>> root, List<T> list) {

		if (root.isPresent()) {
			Node<T> x = root.get();
			sort(x.getLeftChild(), list);
			list.add(x.getKey());
			sort(x.getRightChild(), list);
		}

	}

	private void print(Optional<Node<T>> root, StringBuilder b) {

		LinkedList<Optional<Node<T>>> actualLevel = new LinkedList<>();
		LinkedList<Optional<Node<T>>> nextLevel = new LinkedList<>();
		nextLevel.add(root);
		do {
			actualLevel.addAll(nextLevel);
			nextLevel.clear();
			while (!actualLevel.isEmpty()) {

				Optional<Node<T>> x = actualLevel.pop();
				if (x.isPresent()) {
					b.append("(" + x.get().getKey() + ", ");
					int numChildren = 0;
					if (x.get().getLeftChild().isPresent()){
						numChildren++;
					}
					if (x.get().getRightChild().isPresent()){
						numChildren++;
					}
					b.append(numChildren + "), ");
					nextLevel.addLast(x.get().getLeftChild());
					nextLevel.addLast(x.get().getRightChild());
				}else{
					b.append("nil, ");
				}

			}
			b.append("\n");
		} while (!nextLevel.isEmpty());

	}
	
	@Override
	public String toString(){
		
		StringBuilder b= new StringBuilder();
		print(root, b);
		return b.toString();
	}

}
