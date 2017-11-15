package it.hella.sorting;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.hella.random.RandomGenerators;

public abstract class SorterTests<T extends Sorter<Integer>> {
	
	private static final Logger logger = LoggerFactory.getLogger(SorterTests.class);
	
	protected static final int MAX_TEST = 10;
	protected static final int MAX_SIZE = 100;
	protected T sorter;
	
	private boolean randomTestsEnabled = true;
	
	public void setRandomTestsEnabled(boolean randomTestsEnabled) {
		this.randomTestsEnabled = randomTestsEnabled;
	}

	@Before
	public abstract void instantiateSorter();
	
	@Test
	public void testSort(){
		
		List<Integer> list = Arrays.asList(3, 9, 6, 1, 4, 9, 3, 4, 3, 8);
		sorter.sort(list);
		assertThat(list, contains(1, 3, 3, 3, 4, 4, 6, 8, 9, 9));
		
	}
	
	@Test
	public void testSortRandom(){
		
		if (!randomTestsEnabled){
			logger.info("Random tests are disabled");
			return;
		}
		for(int t = 0; t <= MAX_TEST; t++){
			
			List<Integer> random = RandomGenerators.generateList(MAX_SIZE, MAX_SIZE);
			logger.debug(Arrays.toString(random.stream().toArray()));
			Instant start = Instant.now();
			sorter.sort(random);
			long nanos = Duration.between(start, Instant.now()).toNanos();
			logger.debug("elapsed (nano) " + String.valueOf(nanos) + " or " + BigDecimal.valueOf(nanos).divide(BigDecimal.valueOf(10E6)) + " ms");
			logger.debug(Arrays.toString(random.stream().toArray()));
			for(int i = 0; i < MAX_SIZE - 1; i++){
				assertTrue("Position " + i + " has " + random.get(i) + " > " + random.get(i + 1), random.get(i) <= random.get(i + 1));
			}
			random.clear();
			
		}
		
	}
	
}
