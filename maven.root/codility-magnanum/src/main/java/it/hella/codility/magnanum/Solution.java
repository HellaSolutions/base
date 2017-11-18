package it.hella.codility.magnanum;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

	private List<Integer> levels;
	private Map<Point, Integer> scores = new HashMap<>();
	private Map<Integer, LinkedList<Point>> board = new HashMap<>()
	private Point startPosition;

	int solution(int X[], int Y[], int N, String T) {
		buildDataStructure(X, Y, N, T);
		return 0;
	}

	private void buildDataStructure(int X[], int Y[], int N, String T) {

		Point point;
		Set<Integer> uniqueLevels = new HashSet<>();
		for (int i = 0; i < Y.length; i++) {

			char piece = T.charAt(i);
			point = new Point(X[i], Y[i]);
			point.setScore(score(piece));
			scores.put(point, point.getScore());
			if (piece != 'X') {
				add(point, board);
				uniqueLevels.add(Y[i]);
			} else {
				startPosition = point;
			}
		}
		xOrder(board);
		levels = uniqueLevels.parallelStream().collect(Collectors.toList());
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

	private void evaluateScoresLastLevel() {

		int startLevel = levels.size() == 1 ? startPosition.getY() : levels.get(levels.size() - 2);
		int endLevel = levels.get(levels.size() - 1);
		List<Point> queensAtEndLevel = queensAtLevel.get(endLevel);
		List<Point> pawnsAtEndLevel = pawnsAtLevel.get(endLevel);
		int[] shadow;
		for(Point point : queensAtLevel.get(startLevel)){
			shadow = getPointInnerShadow(point, endLevel, queensAtEndLevel);
			if (shadow.length > 0){
				int score = point.getScore() + 10;
				point.setLeftScore(score);
				point.setRightScore(score);
			}else{
				shadow = getPointInnerShadow(point, endLevel, pawnsAtEndLevel);
				if (shadow.length > 0){
					int score = point.getScore() + 1;
					point.setLeftScore(score);
					point.setRightScore(score);
				}else{
					int[] xBounds = point.shadowAtLevel(endLevel);
					Point leftBound
					scores.containsKey()
				}
			}
		}
		

	}

	private int[] getPointInnerShadow(Point point, int endLevel, List<Point> queensAtEndLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	private void add(Point point, Map<Integer, LinkedList<Point>> queensAtLevel2) {

		int y = point.getY();
		if (!queensAtLevel2.containsKey(y)) {
			queensAtLevel2.put(y, new LinkedList<>());
		}
		queensAtLevel2.get(y).add(point);
	}

	private void xOrder(Map<Integer, LinkedList<Point>> levelMap) {
		for (Entry<Integer, LinkedList<Point>> level : levelMap.entrySet()) {
			Collections.sort(level.getValue(), (p, q) -> p.getX() - q.getX());
		}
	}

	static final int[] pointsInShadow(Point point, Integer level, List<Integer> pointsAtLevel) {

		if (point.getY() > level) {
			return new int[] {};
		}
		int[] shadow = point.shadowAtLevel(level);
		if (shadow[1] < pointsAtLevel.get(0)) {
			return new int[] { -1, -1 };
		}
		if (shadow[0] > pointsAtLevel.get(pointsAtLevel.size() - 1)) {
			return new int[] { -pointsAtLevel.size() - 1, -pointsAtLevel.size() - 1 };
		}
		int lower = BinarySearch.search(shadow[0], pointsAtLevel);
		int start = lower >= 0 ? lower : -lower - 1;
		if (shadow[0] == shadow[1]) {
			return lower > 0 ? new int[] { start, start } : new int[] { start, start - 1 };
		}
		int upper = BinarySearch.search(shadow[1], pointsAtLevel, start, pointsAtLevel.size() - 1);
		int end = upper >= 0 ? upper : -upper - 2;
		return new int[] { start, end };

	}

	static int[] rightClose(int[] interval, int rightLimit, List<Integer> pointsAtLevel) {

		if (interval[0] == -pointsAtLevel.size() - 1) {
			return new int[] {};
		}
		if (interval[0] == -1) {
			if (pointsAtLevel.get(0) == rightLimit) {
				return new int[] { 0, 0 };
			} else {
				return new int[] {};
			}
		}
		if (interval[0] > interval[1]) {
			int rightBoundary = interval[0];
			if (pointsAtLevel.get(rightBoundary) == rightLimit) {
				return new int[] { rightBoundary, rightBoundary };
			} else {
				return new int[] {};
			}
		}
		int index = interval[1] + 1;
		return (index <= pointsAtLevel.size() - 1 && pointsAtLevel.get(index) == rightLimit)
				? new int[] { interval[0], index } : interval;

	}

	static int[] leftClose(int[] interval, int leftLimit, List<Integer> pointsAtLevel) {

		if (interval[0] == -1) {
			return new int[] {};
		}
		int leftBoundary = pointsAtLevel.size() - 1;
		if (interval[0] == -leftBoundary - 2) {
			if (pointsAtLevel.get(leftBoundary) == leftLimit) {
				return new int[] { leftBoundary, leftBoundary };
			} else {
				return new int[] {};
			}
		}
		if (interval[0] > interval[1]) {
			leftBoundary = interval[1];
			if (pointsAtLevel.get(leftBoundary) == leftLimit) {
				return new int[] { leftBoundary, leftBoundary };
			} else {
				return new int[] {};
			}
		}
		int index = interval[0] - 1;
		return (index >= 0 && pointsAtLevel.get(index) == leftLimit) ? new int[] { index, interval[1] } : interval;

	}

	static final PointShadows getPointShadows(Point point, int level, List<Integer> pointsAtLevel, Set<Point> points) {

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
		int[] interval = pointsInShadow(new Point(point.getX(), point.getY() + 2), level, pointsAtLevel);
		int deltaLevel = level - point.getY();
		if (!rightClosure) {
			rightShadow = rightClose(interval, point.getX() + deltaLevel, pointsAtLevel);
		}
		if (!leftClosure) {
			leftShadow = leftClose(interval, point.getX() - deltaLevel, pointsAtLevel);
		}
		return new PointShadows(leftShadow, rightShadow);

	}

	static class Point {

		int x;
		int y;
		int leftScore;
		int rightScore;
		int score;

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

		public int getLeftScore() {
			return leftScore;
		}

		public void setLeftScore(int leftScore) {
			this.leftScore = leftScore;
		}

		public int getRightScore() {
			return rightScore;
		}

		public void setRightScore(int rightScore) {
			this.rightScore = rightScore;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
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

	static class BinarySearch {

		private BinarySearch() {
		}

		@SuppressWarnings("rawtypes")
		public static <T extends Comparable> int search(T e, List<T> orderedList) {
			return search(e, orderedList, 0, orderedList.size() - 1);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static <T extends Comparable> int search(T e, List<T> orderedList, int lowLimit, int upLimit) {

			if (lowLimit < 0 || lowLimit > upLimit || upLimit > orderedList.size() - 1) {
				throw new IllegalArgumentException("Invalid bounds (" + lowLimit + ", " + upLimit + ")");
			}
			if (e.compareTo(orderedList.get(upLimit)) > 0) {
				return -upLimit - 2;
			}
			if (e.compareTo(orderedList.get(lowLimit)) < 0) {
				return -lowLimit - 1;
			}
			int a = lowLimit;
			int b = upLimit;
			int m = (a + b) / 2;
			int comparison;
			while (m > a) {
				comparison = orderedList.get(m).compareTo(e);
				if (comparison == 0) {
					return m;
				}
				if (comparison > 0) {
					b = m;
				} else {
					a = m;
				}
				m = (a + b) / 2;
			}
			if (e.compareTo(orderedList.get(a)) == 0) {
				return a;
			}
			if (e.compareTo(orderedList.get(a + 1)) == 0) {
				return a + 1;
			}
			return -a - 2;

		}

	}

}
