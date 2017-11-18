package it.hella.codility.magnanum;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

	private static final XComparator xComparator = new XComparator();
	private static final YComparator yComparator = new YComparator();

	private List<Integer> levels;
	private Map<Point, Integer> scores = new HashMap<>();
	private Map<Integer, LinkedList<Integer>> board = new HashMap<>();
	private Map<Integer, LinkedList<Integer>> queensAtLevel = new HashMap<>();
	private Point startPosition;

	int solution(int X[], int Y[], int N, String T) {
		buildDataStructure(X, Y, N, T);
		return 0;
	}

	private void buildDataStructure(int X[], int Y[], int N, String T) {

		Point point;
		for (int i = 0; i < Y.length; i++) {

			char piece = T.charAt(i);
			point = new Point(X[i], Y[i]);
			scores.put(point, score(piece));
			if (piece != 'X') {
				add(X[i], Y[i], board);
				if (piece == 'q') {
					add(X[i], Y[i], queensAtLevel);
				}
			} else {
				startPosition = point;
			}
		}
		xOrder(board);
		xOrder(queensAtLevel);
		levels = board.keySet().parallelStream().collect(Collectors.toList());
		Collections.sort(levels);

	}

	private Integer score(char piece) {
		switch (piece) {
		case 'p':
			return 1;
		case 'q':
			return 10;
		case 'X':
			return 0;
		default:
			throw new IllegalArgumentException("Unrecognized piece: " + piece);
		}
	}

	private int evaluateScore() {

		int levelIndex = levels.size() - 2;
		int level = levels.get(levelIndex);
		while (level > startPosition.getY()) {
			evaluateScore(levelIndex);
			level = levels.get(--levelIndex);
		}
		evaluateScore(startPosition);
		return scores.get(startPosition);

	}

	private void evaluateScore(Point point) {
		// TODO Auto-generated method stub

	}

	private void evaluateScore(int levelIndex) {

		List<Integer> points = board.get(levelIndex);
		int level = levels.get(levelIndex);
		Map<Point, int[]> intervals = new HashMap<>();
		for (Integer x : points) {
			Point point = new Point(x, level);
			for (int currentLevelIndex = levelIndex + 1; currentLevelIndex < levels.size() - 1; currentLevelIndex++) {
				int[] pointsInterval;
				int toLevel = levels.get(currentLevelIndex);
				if (queensAtLevel.containsKey(toLevel)) {
					pointsInterval = pointsInShadow(point, toLevel, queensAtLevel.get(toLevel));
					if (pointsInterval.length > 0) {
						scores.put(point, scores.get(point) + 10);
					}
				}
				pointsInterval = pointsInShadow(point, toLevel, board.get(toLevel));
				if (pointsInterval.length == 0) {
					scores.put(point, scores.get(point) + 0);
				}
				scores.put(point, scores.get(point) + 1);

			}
		}

	}

	private void evaluateScore(List<Integer> levelPoints) {
		// TODO Auto-generated method stub

	}

	private void add(int x, int y, Map<Integer, LinkedList<Integer>> list) {
		if (!list.containsKey(y)) {
			board.put(y, new LinkedList<>());
		}
		board.get(y).add(x);
	}

	private void xOrder(Map<Integer, LinkedList<Integer>> list) {
		for (Entry<Integer, LinkedList<Integer>> level : list.entrySet()) {
			Collections.sort(level.getValue());
		}
	}

	static final int[] pointsInShadow(Point point, Integer level, List<Integer> pointsAtLevel) {

		if (point.getY() >= level) {
			return new int[] {};
		}
		int[] shadow = point.shadowAtLevel(level);
		if (shadow[1] < pointsAtLevel.get(0)) {
			return new int[] { -1, -1 };
		}
		if (shadow[0] > pointsAtLevel.get(pointsAtLevel.size() - 1)) {
			return new int[] { -pointsAtLevel.size() - 1, -pointsAtLevel.size() - 1 };
		}
		int lower = Collections.binarySearch(pointsAtLevel, shadow[0]);
		int upper = Collections.binarySearch(pointsAtLevel, shadow[1]);
		int start = lower >= 0 ? lower : -lower - 1;
		int end = upper >= 0 ? upper : -upper - 2;
		return new int[] { start, end };

	}

	static int[] rightClose(int[] interval, int rightLimit, List<Integer> pointsAtLevel) {

		if (interval[0] == -pointsAtLevel.size() - 1){
			return new int[]{};
		}
		if (interval[0] == -1) {
			if (pointsAtLevel.get(0) == rightLimit){
				return new int[] { 0, 0 };
			}else{
				return new int[]{};
			}
		}
		if (interval[0] > interval[1]) {
			int rightBoundary = interval[0] + 1;
			if(pointsAtLevel.get(rightBoundary) == rightLimit){
				return new int[] { rightBoundary, rightBoundary };
			}else{
				return new int[]{};
			}
		}
		int index = interval[1] + 1;
		return (index < pointsAtLevel.size() - 1 && pointsAtLevel.get(index) == rightLimit) ? new int[]{interval[0], index} : interval;

	}

	static int[] leftClose(int[] interval, int leftLimit, List<Integer> pointsAtLevel) {

		if (interval[0] == -1) {
			return new int[] {};
		}
		int leftBoundary = pointsAtLevel.size() - 1;
		if (interval[0] == -leftBoundary - 2) {
			if(pointsAtLevel.get(leftBoundary) == leftLimit){
				return new int[] {leftBoundary, leftBoundary };
			}else{
				return new int[]{};
			}
		}
		if (interval[0] > interval[1]) {
			leftBoundary = interval[1] - 1;
			if(pointsAtLevel.get(leftBoundary) == leftLimit){
				return new int[] { leftBoundary, leftBoundary };
			}else{
				return new int[]{};
			}
		}
		int index = interval[0] - 1;
		return (index > 0 && pointsAtLevel.get(index) == leftLimit) ? new int[]{index, interval[1]} : interval;

	}

	static final PointShadows getPointShadow(Point point, int level, List<Integer> pointsAtLevel,
			Set<Point> points) {

		if (point.getY() >= level - 1) {
			return PointShadows.EMPTY;
		}
		boolean rightClosure = points.contains(new Point(point.getX() + 1, point.getY() + 1));
		boolean leftClosure = points.contains(new Point(point.getX() - 1, point.getY() + 1));
		if (rightClosure && leftClosure) {
			return PointShadows.EMPTY;
		}
		int[] leftShadow = new int[] {};
		int[] rightShadow = new int[] {};
		if (point.getY() == level - 2) {
			if (!rightClosure) {	
				rightShadow = nearestBoundary(pointsAtLevel, point.getX() + 2);
			}
			if (!leftClosure) {
				leftShadow = nearestBoundary(pointsAtLevel, point.getX() - 2);
			}
		} else {
			int[] interval = pointsInShadow(new Point(point.getX(), point.getY() + 2), level, pointsAtLevel);
			int deltaLevel = level - point.getY();
			if (!rightClosure) {
				rightShadow = rightClose(interval, point.getX() + deltaLevel, pointsAtLevel);
			}
			if (!leftClosure) {
				leftShadow = leftClose(interval, point.getX() - deltaLevel, pointsAtLevel);
			}
		}
		return new PointShadows(leftShadow, rightShadow);

	}

	static final int[] nearestBoundary(List<Integer> pointsAtLevel, int limit) {

		int boundary = Collections.binarySearch(pointsAtLevel, limit);
		return (boundary > 0) ? new int[] { boundary, boundary } : new int[] {};

	}

	final int maxScore(Point point, Integer level) {

		int[] pointsInterval;
		if (queensAtLevel.containsKey(level)) {
			pointsInterval = pointsInShadow(point, level, queensAtLevel.get(level));
			if (pointsInterval.length > 0) {
				return 10;
			}
		}
		pointsInterval = pointsInShadow(point, level, board.get(level));
		if (pointsInterval.length == 0) {
			return 0;
		}
		return 1;

	}

	private static class XComparator implements Comparator<Point> {

		@Override
		public int compare(Point o1, Point o2) {
			return o1.x - o2.x;
		}

	}

	private static class YComparator implements Comparator<Point> {

		@Override
		public int compare(Point o1, Point o2) {
			return o1.y - o2.y;
		}

	}

	static class Point {

		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int[] shadowAtLevel(int level) {
			if (level < y) {
				throw new IllegalArgumentException(
						"Cannot evaluate shadow for levels lower than the current point: " + y + " > " + level);
			}
			int delta = level - this.y;
			return new int[] { x - delta, x + delta };
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		public int compare(Point point) {

			int[] shadow;
			if (y == point.getY()) {
				return 0;
			}
			Point less = y > point.getY() ? point : this;
			Point up = y > point.getY() ? this : point;
			shadow = less.shadowAtLevel(up.getY());
			if (up.getX() >= shadow[0] && up.getX() < shadow[1]) {
				return y - point.getY();
			} else {
				return 0;
			}

		}

	}

	static class PointShadows {

		static final PointShadows EMPTY = new PointShadows(new int[] {}, new int[] {});

		int[] leftShadow;
		int[] righthadow;

		public PointShadows(int[] leftShadow, int[] righthadow) {
			super();
			this.leftShadow = leftShadow;
			this.righthadow = righthadow;
		}

		public int[] getLeftShadow() {
			return leftShadow;
		}

		public void setLeftShadow(int[] leftShadow) {
			this.leftShadow = leftShadow;
		}

		public int[] getRightShadow() {
			return righthadow;
		}

		public void setRighthadow(int[] righthadow) {
			this.righthadow = righthadow;
		}

	}
	
	

}
