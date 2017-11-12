package it.hella.sorting;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public abstract class SorterTests<T extends Sorter<Integer>> {
	
	private static final Logger logger = LoggerFactory.getLogger(SorterTests.class);
	
	protected static final int MAX_TEST = 1000;
	protected static final int MAX_SIZE = 1000;
	protected T sorter;
	
	private boolean randomTestsEnabled = true;
	
	public void setRandomTestsEnabled(boolean randomTestsEnabled) {
		this.randomTestsEnabled = randomTestsEnabled;
	}

	@Before
	public abstract void instantiateSorter();
	
	@Test
	public void sortTestEven(){
		
		List<Integer> list = Arrays.asList(3, 9, 6, 1, 4, 9, 3, 4, 3, 8);
		sorter.sort(list);
		assertThat(list, contains(1, 3, 3, 3, 4, 4, 6, 8, 9, 9));
		
	}

	
	@Test
	public void mergeSortRandomTest(){
		
		if (!randomTestsEnabled){
			logger.info("Random tests are disabled");
			return;
		}
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
