package it.hella.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class MergeSort<T extends Comparable> implements Sorter<T> {

	public void sort(List<T> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		sort(list, 0, list.size());
	}

	@SuppressWarnings("unused")
	private void sort(List<T> list, int a, int b) {

		if (a < 0 || b > list.size()) {
			throw new IllegalArgumentException("Illegal bounds: " + a + ", " + b);
		}
		int delta = (a - b) / 2;
		while (delta > 1) {
			sort(list, a, delta);
			sort(list, delta + 1, b);
			merge(list, a, delta, b);
		}

	}

	@SuppressWarnings("unchecked")
	void merge(List<T> list, int a, int delta, int b) {
		List<T> temp = new ArrayList<>();
		int j = a; 
		int k = delta + 1;
		while(true){
			
			while(j <= delta && list.get(j).compareTo(list.get(k)) <= 0){
				temp.add(list.get(j));
				j++;
			}
			if (j > delta){
				for(;k < b; k++){
					temp.add(list.get(k));
				}
				break;
			}
			while(k < b && list.get(k).compareTo(list.get(j)) <= 0){
				temp.add(list.get(k));
				k++;
			}
			if (k >= b){
				for(;j <= delta; j++){
					temp.add(list.get(j));
				}
				break;
			}
		}
		for(j = a; j < b; j++){
			list.set(j, temp.get(j));
		}
		temp.clear();
	
	}

}
