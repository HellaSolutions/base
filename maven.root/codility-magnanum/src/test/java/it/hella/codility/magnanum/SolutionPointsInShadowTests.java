package it.hella.codility.magnanum;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class SolutionPointsInShadowTests {
	
	@Test
	public void testPointInShadowBothIn(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(7, 11, 67, 90, 91, 92, 110, 130, 140));
		assertEquals(3, interval[0]);
		assertEquals(6, interval[1]);
		
	}
	
	@Test
	public void testPointInShadowBothInFromStart(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(90, 91, 92, 93, 110, 111, 112));
		assertEquals(0, interval[0]);
		assertEquals(4, interval[1]);
		
	}
	
	@Test
	public void testPointInShadowBothInToEnd(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(4, 5, 6, 7, 90, 91, 92, 93, 110));
		assertEquals(4, interval[0]);
		assertEquals(8, interval[1]);
		
	}
	
	@Test
	public void testPointInShadowBothInAll(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(90, 91, 100, 101, 102, 103, 104, 105, 110));
		assertEquals(0, interval[0]);
		assertEquals(8, interval[1]);
		
	}
	
	@Test
	public void testPointInLowerBoundOut(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(98, 99, 100, 101, 102, 103, 104, 105, 110));
		assertEquals(0, interval[0]);
		assertEquals(8, interval[1]);
		
	}
	
	@Test
	public void testPointUpperBoundOut(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(90, 98, 99, 100, 101, 102, 103, 104, 107));
		assertEquals(0, interval[0]);
		assertEquals(8, interval[1]);
		
	}
	
	@Test
	public void testPointFirstNotIn(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(70, 91, 98, 99, 100, 101, 102, 103, 110, 107));
		assertEquals(1, interval[0]);
		assertEquals(8, interval[1]);
		
	}
	
	@Test
	public void testPointSecondNotIn(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(70, 90, 98, 99, 100, 101, 102, 103, 111, 107));
		assertEquals(1, interval[0]);
		assertEquals(7, interval[1]);
		
	}
	
	@Test
	public void testPointBothNotIn(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(70, 91, 98, 99, 100, 101, 102, 103, 111, 107));
		assertEquals(1, interval[0]);
		assertEquals(7, interval[1]);
		
	}
	
	@Test
	public void testPointNoPoints(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(0, 1, 2, 3, 120, 1300, 140, 150));
		assertEquals(4, interval[0]);
		assertEquals(3, interval[1]);
		
	}
	
	@Test
	public void testPointDisjointUpper(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(0, 1, 2, 3, 5, 6, 7));
		assertThat(Arrays.asList(interval[0], interval[1]), contains(-8,-8));
		
	}
	
	@Test
	public void testPointDisjointLower(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(120, 130, 140, 150));
		assertThat(Arrays.asList(interval[0], interval[1]), contains(-1,-1));
		
	}
	
	@Test
	public void testPointOnePoint(){
		
		int[] interval = Solution.pointsInShadow(new Solution.Point(100, 0), 10, Arrays.asList(0, 1, 2, 90, 120, 130, 140));
		assertEquals(3, interval[0]);
		assertEquals(3, interval[1]);
		
	}

}
