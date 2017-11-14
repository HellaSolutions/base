package it.hella.sorting;

import java.util.List;

import it.hella.heap.Heap;

public class HeapSort<T extends Comparable> implements Sorter<T> {

	@Override
	public void sort(List<T> list) {
		
		Heap<T> heap = new Heap<>(list);
		Heap.buildMaxHeap(heap);
		int iterate = heap.size() - 1;
		do{
			T swap = heap.get(iterate);
			heap.set(iterate, heap.get(0));
			heap.set(0, swap);
			Heap.maxHeapify(heap, 0, iterate--);
		}while(iterate > 0);
		
	}

}
