package it.hella.sorting;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.hella.heap.Heap;
import it.hella.random.RandomGenerators;

public class HeapSortTests extends SorterTests<HeapSort<Integer>> {
	
	private static final Logger logger = LoggerFactory.getLogger(HeapSortTests.class);

	@Override
	public void instantiateSorter() {

		sorter = new HeapSort<>();

	}

	@Test
	public void testHeapifyThree() {

		Heap<Integer> heap = new Heap<>(Arrays.asList(1, 3, 2));
		Heap.maxHeapify(heap, 0);
		assertHeap(heap);

	}

	@Test
	public void testHeapify() {

		Heap<Integer> heap = new Heap<>(Arrays.asList(5, 16, 14, 10, 8, 7, 9, 3, 2, 4, 1));
		Heap.maxHeapify(heap, 0);
		assertHeap(heap);

	}
	
	@Test
	public void testBuilHeap() {

		Heap<Integer> heap = new Heap<>(Arrays.asList(3, 45, 6, 1, 8, 21, 67, 8, 10, 3, 7, 5, 3, 2, 4, 1));
		Heap.buildMaxHeap(heap);
		assertHeap(heap);

	}
	
	@Test
	public void testRandomBuildHeap() {

		for(int t = 0; t <= MAX_TEST; t++){
		
			List<Integer> list = RandomGenerators.generateList(MAX_SIZE, MAX_SIZE);
			Heap<Integer> heap = new Heap<>(list);
			logger.debug(Arrays.toString(heap.stream().toArray()));
			Instant start = Instant.now();
			Heap.buildMaxHeap(heap);
			long nanos = Duration.between(start, Instant.now()).toNanos();
			logger.debug("elapsed (nano) " + String.valueOf(nanos) + " or " + BigDecimal.valueOf(nanos).divide(BigDecimal.valueOf(10E6)) + " ms");
			logger.debug(Arrays.toString(heap.stream().toArray()));
			assertHeap(heap);
			list.clear();
			
		}
		
	}

	private void assertHeap(Heap<Integer> heap) {

		logger.debug(Arrays.toString(heap.stream().toArray()));
		for (int i = heap.size() - 1; i >= 0; i--) {

			Optional<Integer> left = heap.leftChild(i);
			Optional<Integer> right = heap.rightChild(i);
			if (left.isPresent()){
				assertTrue("Error at position " + i + ": " + heap.get(i) + " has left child " + heap.get(left.get()), heap.get(i) >= heap.get(left.get()));
			}
			if (right.isPresent()){
				assertTrue("Error at position " + i + ": " + heap.get(i) + " has right child " + heap.get(right.get()), heap.get(i) >= heap.get(right.get()));
			}

		}

	}

}
