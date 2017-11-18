package it.hella.codility.magnanum;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import it.hella.codility.magnanum.Solution.PointShadows;

public class GetPointShadowTests {
	
	@Test
	public void testClosedShadow(){
		
		Set<Solution.Point> closers = Arrays.asList(new Solution.Point(99, 1), new Solution.Point(101, 1)).stream().collect(Collectors.toSet());
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(70, 91, 98, 99, 100, 101, 102, 103, 111, 107), closers);
		assertEquals(0, pointShadow.getLeftShadow().length);
		assertEquals(0, pointShadow.getRightShadow().length);
		
	}
	
	@Test
	public void testLeftClosedShadow(){
		
		Set<Solution.Point> closers = Arrays.asList(new Solution.Point(99, 1)).stream().collect(Collectors.toSet());
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(70, 91, 98, 99, 100, 101, 102, 103, 111, 107), closers);
		assertEquals(0, pointShadow.getLeftShadow().length);
		
	}
	
	@Test
	public void testRightClosedShadow(){
		
		Set<Solution.Point> closers = Arrays.asList(new Solution.Point(101, 1)).stream().collect(Collectors.toSet());
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(70, 91, 98, 99, 100, 101, 102, 103, 111, 107), closers);
		assertEquals(0, pointShadow.getRightShadow().length);
		
	}
	
	@Test
	public void testInternalAndBothBoundaryShadowOneInternal(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(7, 11, 67, 88, 90, 106, 110, 111, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(4,5));
		assertThat(Arrays.asList(right[0], right[1]), contains(5,6));
		
	}
	
	@Test
	public void testInternalAndBothBoundaryShadowMultipleInternal(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(7, 11, 67, 88, 90, 92, 107, 108, 110, 111, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(4,7));
		assertThat(Arrays.asList(right[0], right[1]), contains(5,8));
		
	}
	
	@Test
	public void testInternalAndRightBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(7, 11, 67, 88, 92, 107, 108, 110, 111, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(4, 6));
		assertThat(Arrays.asList(right[0], right[1]), contains(4, 7));
		
	}
	
	@Test
	public void testInternalAndLeftBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(7, 11, 67, 88, 90, 92, 107, 108, 111, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(4, 7));
		assertThat(Arrays.asList(right[0], right[1]), contains(5, 7));
		
	}
	
	@Test
	public void testInternalEmptyBothBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(7, 11, 67, 88, 90, 110, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(4, 4));
		assertThat(Arrays.asList(right[0], right[1]), contains(5, 5));
		
	}
	
	@Test
	public void testInternalEmptyRightBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(8, 12, 68, 88, 110, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertEquals(0, left.length);
		assertThat(Arrays.asList(right[0], right[1]), contains(4, 4));
		
	}
	
	@Test
	public void testInternalEmptyLeftBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(8, 12, 68, 88, 89, 90, 112, 114, 120, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(5, 5));
		assertEquals(0, right.length);
		
	}
	
	@Test
	public void testInternalEmptyShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(7, 11, 67, 88, 89, 91, 109, 120, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertEquals(0, left.length);
		assertEquals(0, right.length);
		
	}
	
	@Test
	public void testInnerDisjointLeftRightBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(110, 120, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertEquals(0, left.length);
		assertThat(Arrays.asList(right[0], right[1]), contains(0, 0));
		
	}
	
	@Test
	public void testInnerDisjointLeftNoRightBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(112, 120, 130, 140), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertEquals(0, left.length);
		assertEquals(0, right.length);
		
	}
	
	@Test
	public void testInnerDisjointRightLeftBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(10, 20, 30, 40, 50, 90), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(5, 5));
		assertEquals(0, right.length);
		
	}
	
	@Test
	public void testInnerDisjointRightNoLeftBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(10, 20, 30, 40, 50, 88), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertEquals(0, left.length);
		assertEquals(0, right.length);
		
	}
	
	@Test
	public void testInnerJustLeftBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(10, 20, 30, 40, 50, 88, 90, 92, 110), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(6, 7));
		assertThat(Arrays.asList(right[0], right[1]), contains(7, 8));
		
		
	}
	
	@Test
	public void testInnerJustRightBoundaryShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(10, 20, 30, 40, 50, 88, 90, 108, 110), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(6, 7));
		assertThat(Arrays.asList(right[0], right[1]), contains(7, 8));
		
		
	}
	
	@Test
	public void testInnerEmptyTightBoundariesShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 10, Arrays.asList(90, 110), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(0, 0));
		assertThat(Arrays.asList(right[0], right[1]), contains(1, 1));
		
		
	}
	
	@Test
	public void testNearestLevelFullShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 2, Arrays.asList(1, 2, 3, 98, 100, 102, 110, 115), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(3, 4));
		assertThat(Arrays.asList(right[0], right[1]), contains(4, 5));
		
		
	}
	
	@Test
	public void testNearestLevelNoLeftShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 2, Arrays.asList(1, 2, 3, 95, 100, 102, 110, 115), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(4, 4));
		assertThat(Arrays.asList(right[0], right[1]), contains(4, 5));
		
		
	}
	
	@Test
	public void testNearestLevelNoRightShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 2, Arrays.asList(1, 2, 3, 95, 98, 100, 105, 110, 115), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(4, 5));
		assertThat(Arrays.asList(right[0], right[1]), contains(5, 5));
		
		
	}
	
	@Test
	public void testNearestLevelOnlyLeftShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 2, Arrays.asList(1, 2, 3, 95, 98, 103, 105, 110, 115), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(4, 4));
		assertEquals(0, right.length);
		
		
	}
	
	@Test
	public void testNearestLevelOnlyRightShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 2, Arrays.asList(1, 2, 3, 95, 96, 102, 103, 105, 110, 115), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertEquals(0, left.length);
		assertThat(Arrays.asList(right[0], right[1]), contains(5, 5));
		
	}
	
	@Test
	public void testNearestLevelOnlyInnerShadow(){
		
		Set<Solution.Point> closers = new HashSet<>();
		PointShadows pointShadow = Solution.getPointShadows(new Solution.Point(100, 0), 2, Arrays.asList(1, 2, 3, 95, 96, 100, 103, 105, 110, 115), closers);
		int[] left = pointShadow.getLeftShadow();
		int[] right = pointShadow.getRightShadow();
		assertThat(Arrays.asList(left[0], left[1]), contains(5, 5));
		assertThat(Arrays.asList(right[0], right[1]), contains(5, 5));
		
	}

}
