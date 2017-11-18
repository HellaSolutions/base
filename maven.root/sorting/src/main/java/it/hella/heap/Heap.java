package it.hella.heap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Heap<T extends Comparable> {

	private List<T> list;

	public Heap(List<T> list) {
		this.list = list;
	}

	public Optional<Integer> leftChild(int index) {
		return leftChild(index, list.size());
	}

	public Optional<Integer> rightChild(int index) {
		return rightChild(index, list.size()); 
	}
	
	private Optional<Integer> leftChild(int index, int limit) {
		int c = 2 * index + 1;
		return c < limit ? Optional.of(c) : Optional.empty();
	}

	private Optional<Integer> rightChild(int index, int limit) {
		int c = 2 * (index + 1);
		return c < limit ? Optional.of(c) : Optional.empty();
	}


	public Optional<Integer> getParent(int index) {
		return index > 0 ? Optional.of(index / 2) : Optional.empty();
	}

	public Comparable getRoot() {
		return list.get(0);
	}
	
	public int size() {
		return list.size();
	}
	
	public T get(int i) {
		return list.get(i);
	}
	
	public void set(int i, T value) {
		list.set(i, value);
	}
	
	public T getLast() {
		return list.get(list.size() - 1);
	}
	
	public Stream<T> stream() {
		return list.stream();
	}

	@SuppressWarnings("unchecked")
	public static final void buildMaxHeap(Heap heap) {

		int k = heap.size()/2;
		for (; k >= 0; k--) {
			maxHeapify(heap, k);
		}

	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Comparable> void maxHeapify(Heap<T> heap, int from) {
		
		maxHeapify(heap, from, heap.size());
	}

	@SuppressWarnings("unchecked")
	public static <T extends Comparable> void maxHeapify(Heap<T> heap, int from, int limit) {

		if (heap == null) {
			return;
		}
		if (from > limit) {
			throw new IllegalArgumentException("Heap index should be less than: " + limit);
		}
		Optional<Integer> rightChild = heap.rightChild(from, limit);
		Optional<Integer> leftChild = heap.leftChild(from, limit);
		if (!(rightChild.isPresent() || leftChild.isPresent())) {
			return;
		}
		T max = null;
		boolean left = false;
		if (leftChild.isPresent()) {
			max = heap.get(leftChild.get());
			left = true;
		}
		if (max == null || (rightChild.isPresent() && heap.get(rightChild.get()).compareTo(max) > 0)) {
			max = heap.get(rightChild.get());
			left = false;
		}
		T outValue = heap.get(from);
		if (outValue.compareTo(max) >= 0) {
			return;
		}
		heap.set(from, max);
		int next = left ? leftChild.get() : rightChild.get();
		heap.set(next, outValue);
		maxHeapify(heap, next, limit);

	}


}
