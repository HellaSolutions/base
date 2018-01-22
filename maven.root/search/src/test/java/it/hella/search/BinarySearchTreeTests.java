package it.hella.search;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import it.hella.random.RandomGenerators;

public class BinarySearchTreeTests {

	private static final Logger logger = LoggerFactory.getLogger(BinarySearchTreeTests.class);

	protected static final int NUM_RANDOM_TESTS = 100;
	private BinarySearchTree<Integer> tree;

	@Before
	public void before() {

		tree = new BinarySearchTree<>();

	}

	@Test
	public void testBase() {

		List<Integer> elements = Arrays.asList(30, 21, 5, 48, 15, 34, 3, 17, 35, 43);
		for (Integer e : elements) {
			tree.add(e);
		}
		assertBinarySearchTree(tree);
		logger.debug(tree.toString());
		for (Integer e : elements) {
			logger.debug("removing: " + e);
			Optional<Node<Integer>> n = tree.search(e);
			assertTrue("element not found: " + e, n.isPresent());
			n.get().toString();
			tree.remove(n.get());
			logger.debug(tree.toString());
			assertBinarySearchTree(tree);
		}
		tree.clear();

	}

	@Test
	public void testRandomAddSearchAndThenRemove() {

		for (int i = 0; i < NUM_RANDOM_TESTS; i++) {
			List<Integer> elements = RandomGenerators.generateDistinctList(100, 10000);
			logger.debug("Random list: " + Arrays.toString(elements.toArray()));
			for (Integer e : elements) {
				tree.add(e);
			}
			assertBinarySearchTree(tree);
			logger.debug("tree\n" + tree);
			for (Integer e : elements) {
				Optional<Node<Integer>> n = tree.search(e);
				assertTrue("element not found: " + e, n.isPresent());
				logger.debug("removing: " + e);
				tree.remove(n.get());
				logger.debug("tree\n" + tree);
				assertBinarySearchTree(tree);
			}
			tree.clear();
			logger.debug("test n." + (i + 1) + " -> OK");
		}

	}

	public static <N extends NodeInterface<Integer, N>> void assertBinarySearchTree(
			AbstractBinarySearchTree<Integer, N> tree) {

		Optional<N> root = tree.getRoot();
		LinkedList<Integer> list = new LinkedList<>();
		buildList(root, list);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size() - 1; i++) {
				assertTrue("Order violated at index: " + i + " - " + list.get(i) + " > " + list.get(i + 1),
						list.get(i) <= list.get(i + 1));
			}
		}
	}

	public static <N extends NodeInterface<Integer, N>> void buildList(Optional<N> root, LinkedList<Integer> list) {

		if (root.isPresent()) {
			N x = root.get();
			if (x.getLeftChild().isPresent()) {
				assertTrue("Invalid parent for " + x.getLeftChild().get() + " should be " + x,
						x.getLeftChild().get().getParent().get().equals(x));
				buildList(x.getLeftChild(), list);
			}
			list.addLast(x.getKey());
			if (x.getRightChild().isPresent()) {
				assertTrue("Invalid parent for " + x.getRightChild().get() + " should be " + x,
						x.getRightChild().get().getParent().get().equals(x));
				buildList(x.getRightChild(), list);
			}

		}

	}

}
