package it.hella.sorting;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.hella.sorting.MergeSort;

public class MergeSortTests extends SorterTests<MergeSort<Integer>>{
	
	private static final Logger logger = LoggerFactory.getLogger(MergeSortTests.class);
	
	@Override
	public void instantiateSorter() {
		sorter = new MergeSort<Integer>();
	}
	
	@Test
	public void mergeTestOne(){
		
		List<Integer> list = Arrays.asList(10);
		sorter.merge(list, 0, 0, 1);
		assertThat(list, contains(10));
		
	}
	
	@Test
	public void mergeTestCouple(){
		
		List<Integer> list = Arrays.asList(20, 10);
		sorter.merge(list, 0, 0, 2);
		assertThat(list, contains(10, 20));
		
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
	public void mergeTestEqualsElements(){
		
		List<Integer> list = Arrays.asList(10, 20, 30, 40, 50, 11, 30, 31, 40, 51);
		((MergeSort<Integer>)sorter).merge(list, 0, 4, 10);
		assertThat(list, contains(10, 11, 20, 30, 30, 31, 40, 40, 50, 51));
		
	}
	
	@Test
	public void mergeTestAlternate(){
		
		List<Integer> list = Arrays.asList(11, 21, 31, 41, 51, 10, 20, 30, 40, 50);
		sorter.merge(list, 0, 4, 10);
		assertThat(list, contains(10, 11, 20, 21, 30, 31, 40, 41, 50, 51));
		
	}
	
	@Test
	public void mergeTestAlternateReversed(){
		
		List<Integer> list = Arrays.asList(10, 20, 30, 40, 50, 11, 21, 31, 41, 51);
		sorter.merge(list, 0, 4, 10);
		assertThat(list, contains(10, 11, 20, 21, 30, 31, 40, 41, 50, 51));
		
	}
	
	
}
	
	
    
	




