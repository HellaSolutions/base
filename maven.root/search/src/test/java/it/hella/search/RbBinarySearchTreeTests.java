package it.hella.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import it.hella.random.RandomGenerators;
import it.hella.search.RbBinarySearchTree.CriticalPoint;
import it.hella.search.RbNode.COLOR;

public class RbBinarySearchTreeTests extends BinarySearchTreeTests {

	private static final Logger logger = LoggerFactory.getLogger(RbBinarySearchTreeTests.class);

	private RbBinarySearchTree<Integer> tree;

	@Override
	@Before
	public void before() {

		tree = new RbBinarySearchTree<>();

	}

	@Override
	@Test
	public void testBase() {

		List<Integer> elements = Arrays.asList(30, 21, 5, 48, 15, 34, 3, 17, 35, 43);
		for (Integer e : elements) {
			tree.add(e);
		}
		assertRedAndBlackTree(tree);
		logger.debug(tree.toString());
		for (Integer e : elements) {
			logger.debug("removing: " + e);
			Optional<RbNode<Integer>> n = tree.search(e);
			assertTrue("element not found: " + e, n.isPresent());
			tree.remove(n.get());
			logger.debug(tree.toString());
			assertRedAndBlackTree(tree);
		}
		tree.clear();

	}

	@Test
	public void testBaseCriticalPointProperties() {

		List<Integer> elements = Arrays.asList(30, 21, 5, 48, 15, 34, 3, 17, 35, 43);
		for (Integer e : elements) {
			tree.clear();
			for (Integer x : elements) {
				tree.add(x);
			}
			logger.debug(tree.toString());
			logger.debug("removing: " + e);
			Optional<RbNode<Integer>> n = tree.search(e);
			assertTrue("element not found: " + e, n.isPresent());
			Optional<CriticalPoint<Integer>> critical = tree.removeTargetCriticalPoint(n.get());
			logger.debug(tree.toString());
			assertCriticalPointProperties(critical);
			assertBinarySearchTree(tree);
		}
		tree.clear();

	}

	public static void assertRedAndBlackTree(RbBinarySearchTree<Integer> tree) {
		assertBinarySearchTree(tree);
		assertEquals("Root is not colored BLACK", COLOR.BLACK, tree.getRoot().get().getColor());
		assertRedBlackRules(tree.getRoot());
	}

	public static int assertRedBlackRules(Optional<RbNode<Integer>> node) {

		if (!node.isPresent()) {
			return 0;
		}
		RbNode<Integer> n = node.get();
		if (n.getColor() == COLOR.RED) {
			assertTrue(n + " is colored RED but its right child in not BLACK",
					(!n.getRightChild().isPresent()) || n.getRightChild().get().getColor() == COLOR.BLACK);
			assertTrue(n + " is colored RED but its left child in not BLACK",
					(!n.getLeftChild().isPresent()) || n.getLeftChild().get().getColor() == COLOR.BLACK);
		}
		int rightBlackWeight = assertRedBlackRules(n.getRightChild());
		int leftBlackWeight = assertRedBlackRules(n.getLeftChild());
		assertTrue(n + " presents unbalanced BLACK weights: " + "(" + leftBlackWeight + ", " + rightBlackWeight + ")",
				rightBlackWeight == leftBlackWeight);
		return n.getColor() == COLOR.BLACK ? rightBlackWeight + 1 : rightBlackWeight;

	}

	public static void assertCriticalPointProperties(Optional<CriticalPoint<Integer>> criticalPoint) {

		if (!criticalPoint.isPresent()) {
			return;
		}
		Optional<RbNode<Integer>> n = criticalPoint.get().getNode();
		boolean leftHeavy = criticalPoint.get().isLeftHeavy();
		if (!n.isPresent()) {
			return;
		}
		RbNode<Integer> point = n.get();
		Optional<RbNode<Integer>> heavierChild = leftHeavy ? point.getLeftChild() : point.getRightChild();
		Optional<RbNode<Integer>> lighterChild = leftHeavy ? point.getRightChild() : point.getLeftChild();
		assertEquals(0, assertRedBlackRules(lighterChild));
		assertTrue(!lighterChild.isPresent() || lighterChild.get().getColor() == COLOR.RED);
		int blackDeep = assertRedBlackRules(heavierChild);
		assertEquals("Critical point with black deep not equals to one", 1, blackDeep);

	}

	@Test
	public void testRandomAddSearchAndThenRemoveAndTargetCriticalPoint() {

		for (int i = 0; i < NUM_RANDOM_TESTS; i++) {
			List<Integer> elements = RandomGenerators.generateDistinctList(100, 10000);
			logger.debug("Random list: " + Arrays.toString(elements.toArray()));
			logger.debug("tree\n" + tree);
			for (Integer e : elements) {
				tree.clear();
				for (Integer u : elements) {
					tree.add(u);
				}
				Optional<RbNode<Integer>> n = tree.search(e);
				assertTrue("element not found: " + e, n.isPresent());
				logger.debug("removing: " + e);
				Optional<CriticalPoint<Integer>> critical = tree.removeTargetCriticalPoint(n.get());
				assertCriticalPointProperties(critical);
				logger.debug("tree\n" + tree);
				assertBinarySearchTree(tree);
			}
			tree.clear();
			logger.debug("test n." + (i + 1) + " -> OK");
		}

	}

	@Override
	@Test
	public void testRandomAddSearchAndThenRemove() {

		for (int i = 0; i < NUM_RANDOM_TESTS; i++) {
			List<Integer> elements = RandomGenerators.generateDistinctList(100, 10000);
			logger.debug("Random list: " + Arrays.toString(elements.toArray()));
			for (Integer e : elements) {
				tree.add(e);
			}
			assertRedAndBlackTree(tree);
			logger.debug("tree\n" + tree);
			for (Integer e : elements) {
				Optional<RbNode<Integer>> n = tree.search(e);
				assertTrue("element not found: " + e, n.isPresent());
				logger.debug("removing: " + e);
				tree.remove(n.get());
				logger.debug("tree\n" + tree);
				assertRedAndBlackTree(tree);
			}
			tree.clear();
			logger.debug("test n." + (i + 1) + " -> OK");
		}

	}

}
