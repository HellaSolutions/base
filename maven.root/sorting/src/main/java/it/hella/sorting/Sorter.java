package it.hella.sorting;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface Sorter<T extends Comparable> {
	public void sort(List<T> list);
}
