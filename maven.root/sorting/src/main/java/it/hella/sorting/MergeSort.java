package it.hella.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class MergeSort<T extends Comparable> implements Sorter<T> {

	private static final Logger logger = LoggerFactory.getLogger(MergeSort.class);

	public void sort(List<T> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		sort(list, 0, list.size());
	}

	@SuppressWarnings("unused")
	private void sort(List<T> list, int a, int b) {

		if (a >= b - 1){
			return;
		}
		int delta = (a + b - 1) / 2;
		sort(list, a, delta + 1);
		sort(list, delta + 1, b);
		merge(list, a, delta, b);

	}

	@SuppressWarnings("unchecked")
	void merge(List<T> list, int a, int delta, int b) {
		List<T> temp = new ArrayList<>();
		int j = a;
		int k = delta + 1;
		if (k >= b)
			return;
		while (j <= delta && k < b) {

			while (j <= delta && list.get(j).compareTo(list.get(k)) <= 0) {
				temp.add(list.get(j));
				j++;
			}
			while (k < b && list.get(k).compareTo(list.get(j)) <= 0) {
				temp.add(list.get(k));
				k++;
			}
			
		}
		if (j > delta) {
			for (; k < b; k++) {
				temp.add(list.get(k));
			}
		}else{
			for (; j <= delta; j++) {
				temp.add(list.get(j));
			}
		}
		for (j = a; j < b; j++) {
			list.set(j, temp.get(j - a));
		}
		temp.clear();

	}
	
}
