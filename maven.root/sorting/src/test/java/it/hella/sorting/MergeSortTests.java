package it.hella.sorting;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import org.junit.Before;
import org.junit.Test;

import it.hella.sorting.MergeSort;

public class MergeSortTests {
	
	private MergeSort<Integer> sorter;

	@Before
	public void before(){
		sorter = new MergeSort<Integer>();
	}
	
	@Test
	public void mergeTestOrdered(){
		
		List<Integer> list = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
		sorter.merge(list, 0, 3, 10);
		assertThat(list, contains(10, 20, 30, 40, 50, 60, 70, 80, 90, 100));
		
	}
	
	@Test
	public void mergeTestReversed(){
		
		List<Integer> list = Arrays.asList(10, 20, 30, 40, 50, 1, 2, 3, 4, 5);
		sorter.merge(list, 0, 4, 10);
		assertThat(list, contains(1, 2, 3, 4, 5, 10, 20, 30, 40, 50));
		
	}
	
	@Test
	public void mergeTestAlternate(){
		
		List<Integer> list = Arrays.asList(10, 20, 30, 40, 50, 11, 21, 31, 41, 51);
		sorter.merge(list, 0, 4, 10);
		assertThat(list, contains(10, 11, 20, 21, 30, 31, 40, 41, 50, 51));
		
	}
	
}
	
	
    
	




