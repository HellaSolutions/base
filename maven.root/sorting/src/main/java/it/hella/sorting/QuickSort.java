package it.hella.sorting;

import java.util.List;
import java.util.Random;

public class QuickSort<T extends Comparable> implements Sorter<T>{

	@Override
	public void sort(List<T> list) {
		
		recursiveSort(list, 0, list.size());
		
	}
	
	@SuppressWarnings("unchecked")
	int partition(List<T> list, int pivot, int a, int b){
		
		if (pivot >= b){
			throw new IllegalArgumentException("pivot out of range: " + pivot);
		}
		int k = pivot;
		for (int i = a; i < b; i++){
			
			int comparison = list.get(k).compareTo(list.get(i));
			if (i > k && comparison >= 0){
				if (i == k + 1){
					T temp = list.get(k);
					list.set(k, list.get(i));
					list.set(i, temp);
				}else{
					T tempK = list.get(k);
					list.set(k, list.get(i));
					list.set(i, list.get(k + 1));
					list.set(k + 1, tempK);
				}
				k = k + 1;
			}else if (i < k && comparison < 0){
				T temp = list.get(k);
				list.set(k, list.get(i));
				list.set(i, temp);
				k = i;
			}
			
		}
		return k;
		
	}
	
	private void recursiveSort(List<T> list, int a, int b){
		
		if (a >= b - 1){
			return;
		}
		int pivot = a + (new Random()).nextInt(b - a - 1);
		pivot = partition(list, pivot, a, b);
		recursiveSort(list, a, pivot);
		recursiveSort(list, pivot + 1, b);
		
	}

}
