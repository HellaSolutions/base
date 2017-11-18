package it.hella.search;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class BinarySearchTests {
	
	@Test
	public void testOneElement() {

		assertEquals(0, BinarySearch.search(Integer.valueOf(1), Arrays.asList(1)));

	}
	
	@Test
	public void testOneElementLeft() {

		assertEquals(-1, BinarySearch.search(Integer.valueOf(1), Arrays.asList(2)));

	}
	
	@Test
	public void testOneElementRight() {

		assertEquals(-2, BinarySearch.search(Integer.valueOf(3), Arrays.asList(2)));

	}
	
	
	@Test
	public void testTwoElementsLow() {

		assertEquals(0, BinarySearch.search(Integer.valueOf(1), Arrays.asList(1, 3)));

	}
	
	@Test
	public void testTwoElementsUp() {

		assertEquals(1, BinarySearch.search(Integer.valueOf(3), Arrays.asList(1, 3)));

	}
	
	@Test
	public void testTwoElementsMiddle() {

		assertEquals(-2, BinarySearch.search(Integer.valueOf(2), Arrays.asList(1, 3)));

	}
	
	@Test
	public void testLeftElement() {

		assertEquals(1, BinarySearch.search(Integer.valueOf(2), Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

	}
	
	@Test
	public void testFirstElement() {

		assertEquals(0, BinarySearch.search(Integer.valueOf(1), Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

	}
	
	@Test
	public void testLastElement() {

		assertEquals(9, BinarySearch.search(Integer.valueOf(10), Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

	}
	
	@Test
	public void testInBetweenElement() {

		assertEquals(-5, BinarySearch.search(Integer.valueOf(6), Arrays.asList(1, 2, 3, 4, 7, 8, 9, 10)));

	}
	
	@Test
	public void testInBetweenLastElement() {

		assertEquals(-8, BinarySearch.search(Integer.valueOf(10), Arrays.asList(1, 2, 3, 4, 7, 8, 9, 11)));

	}
	
	@Test
	public void testInBetweenFirstElement() {

		assertEquals(-2, BinarySearch.search(Integer.valueOf(2), Arrays.asList(1, 4, 7, 8, 9, 10, 11, 12, 13)));

	}
	
	@Test
	public void testIntervalLeftElement() {

		assertEquals(-3, BinarySearch.search(Integer.valueOf(3), Arrays.asList(1, 2, 5, 6, 7, 10, 11, 12), 2, 4));

	}
	
	@Test
	public void testIntervalRightElement() {

		assertEquals(-6, BinarySearch.search(Integer.valueOf(9), Arrays.asList(1, 2, 5, 6, 7, 10, 11, 12), 2, 4));

	}
	
	@Test
	public void testIntervalIn() {

		assertEquals(3, BinarySearch.search(Integer.valueOf(7), Arrays.asList(1, 2, 5, 7, 9, 10, 11, 12), 2, 4));

	}
	
	@Test
	public void testIntervalInBetweenRight() {

		assertEquals(-5, BinarySearch.search(Integer.valueOf(8), Arrays.asList(1, 2, 5, 7, 9, 10, 11, 12), 2, 4));

	}
	
	@Test
	public void testIntervalInBetweenLeft() {

		assertEquals(-4, BinarySearch.search(Integer.valueOf(6), Arrays.asList(1, 2, 5, 7, 9, 10, 11, 12), 2, 4));

	}

	@Test
	public void testIntervalInBetween() {

		assertEquals(-6, BinarySearch.search(Integer.valueOf(11), Arrays.asList(1, 2, 5, 7, 9, 10, 11, 12), 2, 4));

	}
	
	@Test
	public void testBug() {

		assertEquals(-6, BinarySearch.search(Integer.valueOf(110), Arrays.asList(0, 1, 2, 3, 89, 130, 140, 150), 5, 7));

	}
	
}
