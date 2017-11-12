package it.hella.sorting;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class QuickSortTests extends SorterTests<QuickSort<Integer>> {

	@Override
	public void instantiateSorter() {
		
		sorter = new QuickSort<Integer>();
		super.setRandomTestsEnabled(true);
		
	}
	
	@Test
	public void partitionTestOne(){
		
		List<Integer> list = Arrays.asList(7);
		int pivot = sorter.partition(list, 0, 0, list.size());
		assertEquals(0, pivot);
		
	}
	
	@Test
	public void partitionTestCouple(){
		
		List<Integer> list = Arrays.asList(7, 1);
		int pivot = sorter.partition(list, 0, 0, list.size());
		assertEquals(1, pivot);
		assertEquals(Integer.valueOf(7), list.get(pivot));
		assertPartitioned(list, pivot);
		
	}
	
	@Test
	public void partitionTestFirst(){
		
		List<Integer> list = Arrays.asList(7, 1, 12, 3, 24, 5);
		int pivot = sorter.partition(list, 0, 0, list.size());
		assertEquals(3, pivot);
		assertEquals(Integer.valueOf(7), list.get(pivot));
		assertPartitioned(list, pivot);
		
	}
	
	@Test
	public void partitionTestLast(){
		
		List<Integer> list = Arrays.asList(7, 1, 12, 3, 24, 5);
		int pivot = sorter.partition(list, 5, 0, list.size());
		assertEquals(2, pivot);
		assertEquals(Integer.valueOf(5), list.get(pivot));
		assertPartitioned(list, pivot);
		
	}
	
	@Test
	public void partitionTestMiddle(){
		
		List<Integer> list = Arrays.asList(7, 1, 12, 3, 24, 5);
		int pivot = sorter.partition(list, 2, 0, list.size());
		assertEquals(4, pivot);
		assertEquals(Integer.valueOf(12), list.get(pivot));
		assertPartitioned(list, pivot);
		
	}
	
	private static void assertPartitioned(List<Integer> list, int pivot){
		
		for (int i = 0; i < list.size(); i++){
			int c = list.get(i).compareTo(list.get(pivot));
			assertTrue(i <= pivot ? c <= 0 : c > 0);
		}
	}

}
