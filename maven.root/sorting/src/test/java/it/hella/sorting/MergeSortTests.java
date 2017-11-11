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

public class MergeSortTests {
	
	private static final Logger logger = LoggerFactory.getLogger(MergeSortTests.class);
	
	private static final int MAX_TEST = 1000;
	private static final int MAX_SIZE = 1000;
	private MergeSort<Integer> sorter;

	@Before
	public void before(){
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
		sorter.merge(list, 0, 4, 10);
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
	
	@Test
	public void mergeSortTest(){
		
		List<Integer> list = Arrays.asList(3, 9, 6, 1, 4, 9, 3, 4, 3, 8);
		sorter.sort(list);
		assertThat(list, contains(1, 3, 3, 3, 4, 4, 6, 8, 9, 9));
		
	}
	
	@Test
	public void mergeSortRandomTest(){
		
		List<Integer> random = new ArrayList<>();
		Random rg = new Random();
		for(int t = 0; t <= MAX_TEST; t++){
			for (int i = 0; i < MAX_SIZE; i++){
				random.add(rg.nextInt(MAX_SIZE));
			}
			logger.debug(Arrays.toString(random.stream().toArray()));
			sorter.sort(random);
			logger.debug(Arrays.toString(random.stream().toArray()));
			for(int i = 0; i < MAX_SIZE - 1; i++){
				assertTrue(random.get(i) <= random.get(i + 1));
			}
			random.clear();
			
		}
		
	}
	
}
	
	
    
	




