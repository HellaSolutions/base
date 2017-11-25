package it.hella.search;

import java.util.List;

public class BinarySearch {

	private BinarySearch() {
	}

	@SuppressWarnings("rawtypes")
	public static <T extends Comparable> int search(T e, List<T> orderedList) {
		return search(e, orderedList, 0, orderedList.size() - 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Comparable> int search(T e, List<T> orderedList, int lowLimit, int upLimit) {

		if (lowLimit < 0 || lowLimit > upLimit || upLimit > orderedList.size() - 1) {
			throw new IllegalArgumentException("Invalid bounds (" + lowLimit + ", " + upLimit + ")");
		}
		if (e.compareTo(orderedList.get(upLimit)) > 0){
			return - upLimit - 2;
		}
		if (e.compareTo(orderedList.get(lowLimit)) < 0){
			return -lowLimit - 1;
		}
		int a = lowLimit;
		int b = upLimit;
		int m = (a + b) / 2;
		int comparison;
		while (m > a) {
			comparison = orderedList.get(m).compareTo(e);
			if (comparison == 0) {
				return m;
			}
			if (comparison > 0) {
				b = m;
			} else {
				a = m;
			}
			m = (a + b) / 2;
		}
		if (e.compareTo(orderedList.get(a)) == 0) {
			return a;
		}
		if (e.compareTo(orderedList.get(a + 1)) == 0) {
			return a + 1;
		}
		return -a-2;

	}

}
