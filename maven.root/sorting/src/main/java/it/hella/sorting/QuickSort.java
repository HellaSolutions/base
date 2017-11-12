package it.hella.sorting;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QuickSort<T extends Comparable> implements Sorter<T> {
	
	private static final Random RG = new Random();

	@Override
	public void sort(List<T> list) {

		sort(list, 0, list.size());

	}

	@SuppressWarnings("unchecked")
	int partition(List<T> list, int pivot, int a, int b) {

		if (pivot >= b) {
			throw new IllegalArgumentException("pivot out of range: " + pivot);
		}
		int k = pivot;
		for (int i = a; i < b; i++) {

			int comparison = list.get(k).compareTo(list.get(i));
			if (i > k && comparison >= 0) {
				if (i == k + 1) {
					swap(list, i , k);
				} else {
					T tempK = list.get(k);
					list.set(k, list.get(i));
					list.set(i, list.get(k + 1));
					list.set(k + 1, tempK);
				}
				k++;
			} else if (i < k && comparison < 0) {
				swap(list, i , k);
				k = i;
			}

		}
		return k;

	}

	private void swap(List<T> list, int i, int k) {

		T temp = list.get(k);
		list.set(k, list.get(i));
		list.set(i, temp);

	}
	
	private void sort(List<T> list, int a, int b) {

 		LinkedList<Integer[]> stack = new LinkedList<>();
		stack.push(new Integer[]{a, b});
		while(!stack.isEmpty()){
			
			Integer[] bounds = stack.pop();
			if (bounds[1] - bounds[0] > 1){
				int pivot = bounds[0] + RG.nextInt(bounds[1] - bounds[0] - 1);
				pivot = partition(list, pivot, bounds[0], bounds[1]);
				stack.push(new Integer[]{bounds[0], pivot});
				stack.push(new Integer[]{pivot + 1, bounds[1]});
			}
			
		}

	}

	@SuppressWarnings("unused")
	private void recursiveSort(List<T> list, int a, int b) {

		if (a >= b - 1) {
			return;
		}
		int pivot = a + RG.nextInt(b - a - 1);
		pivot = partition(list, pivot, a, b);
		recursiveSort(list, a, pivot);
		recursiveSort(list, pivot + 1, b);

	}

}
