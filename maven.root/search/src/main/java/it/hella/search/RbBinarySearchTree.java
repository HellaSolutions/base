package it.hella.search;

import com.google.common.base.Optional;

import it.hella.search.RbNode.COLOR;

public class RbBinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T, RbNode<T>> {

	@Override
	protected RbNode<T> getNode(T key) {
		return new RbNode<>(key);
	}

	@Override
	public boolean add(T key) {

		Optional<RbNode<T>> inserted = super.insertNode(key);
		if (!inserted.isPresent()) {
			return false;
		}
		redPropertyRebuild(inserted.get());
		return true;

	}

	private void redPropertyRebuild(RbNode<T> added) {

		added.setColor(COLOR.RED);
		Optional<RbNode<T>> oParent = added.getParent();
		while (oParent.isPresent()) {

			RbNode<T> parent = oParent.get();
			if (parent.getColor() == COLOR.BLACK) {
				break;
			}
			RbNode<T> granParent = parent.getParent().get();
			if (granParent.getColor() != COLOR.BLACK) {
				throw new IllegalStateException("Red node with red parent: " + parent);
			}
			if (parent.isRightChild()) {
				added = rightAdd(granParent, parent, added);
			} else {
				added = leftAdd(granParent, parent, added);
			}
			oParent = added.getParent();

		}
		if (root.isPresent()) {
			root.get().setColor(COLOR.BLACK);
		}

	}

	private RbNode<T> leftAdd(RbNode<T> parent, RbNode<T> leftChild, RbNode<T> added) {

		Optional<RbNode<T>> oRightChild = parent.getRightChild();
		if (oRightChild.isPresent()) {
			RbNode<T> rightChild = oRightChild.get();
			if (rightChild.getColor() == COLOR.RED) {
				rightChild.setColor(COLOR.BLACK);
				leftChild.setColor(COLOR.BLACK);
				parent.setColor(COLOR.RED);
				return parent;
			}
		}
		if (added.isRightChild()) {
			leftRotate(leftChild);
			RbNode<T> temp = added;
			added = leftChild;
			leftChild = temp;
		}
		rightRotate(parent);
		added.setColor(COLOR.BLACK);
		return leftChild;

	}

	private RbNode<T> rightAdd(RbNode<T> parent, RbNode<T> rightChild, RbNode<T> added) {

		Optional<RbNode<T>> oLeftChild = parent.getLeftChild();
		if (oLeftChild.isPresent()) {
			RbNode<T> leftChild = oLeftChild.get();
			if (leftChild.getColor() == COLOR.RED) {
				leftChild.setColor(COLOR.BLACK);
				rightChild.setColor(COLOR.BLACK);
				parent.setColor(COLOR.RED);
				return parent;
			}
		}
		if (added.isLeftChild()) {
			rightRotate(rightChild);
			RbNode<T> temp = added;
			added = rightChild;
			rightChild = temp;
		}
		leftRotate(parent);
		added.setColor(COLOR.BLACK);
		return rightChild;

	}

	@Override
	public void remove(RbNode<T> z) {

		Optional<CriticalPoint<T>> oCriticalPoint = removeTargetCriticalPoint(z);
		rebalance(oCriticalPoint);

	}

	protected Optional<CriticalPoint<T>> removeTargetCriticalPoint(RbNode<T> z) {

		CriticalPoint<T> criticalPoint;
		Optional<RbNode<T>> optSubstitute;
		boolean leftPresent = z.getRightChild().isPresent();
		boolean rightPresent = z.getRightChild().isPresent();
		if (!(leftPresent && rightPresent)) {
			criticalPoint = new CriticalPoint<>(z.getParent(), !z.isLeftChild());
			optSubstitute = rightPresent ? z.getRightChild() : z.getLeftChild();
			transplant(z, optSubstitute);
			if (!optSubstitute.isPresent()) {
				optSubstitute = Optional.of(z);
			}
		} else {
			optSubstitute = getMinimum(z.getRightChild().get());
			if (!optSubstitute.isPresent()) {
				throw new IllegalStateException("getMinimum returned an empty value");
			}
			RbNode<T> substitute = optSubstitute.get();
			RbNode<T> parent = substitute.getParent().get();
			if (!parent.equals(z)) {
				criticalPoint = new CriticalPoint<>(Optional.of(parent), false);
				parent.setLeftChild(substitute.getRightChild());
				substitute.setRightChild(z.getRightChild());
			} else {
				criticalPoint = new CriticalPoint<>(Optional.of(substitute), true);
			}
			substitute.setLeftChild(z.getLeftChild());
			transplant(z, optSubstitute);

		}
		COLOR substituteColor = COLOR.RED;
		if (optSubstitute.isPresent()) {
			substituteColor = optSubstitute.get().getColor();
			optSubstitute.get().setColor(z.getColor());
		}
		return substituteColor == COLOR.BLACK ? Optional.of(criticalPoint) : Optional.absent();

	}

	private void rebalance(Optional<CriticalPoint<T>> oCriticalPoint) {

		if (oCriticalPoint.isPresent() && oCriticalPoint.get().node.isPresent()) {
			RbNode<T> criticalPoint = oCriticalPoint.get().getNode().get();
			boolean leftHeavy = oCriticalPoint.get().isLeftHeavy();
			Optional<RbNode<T>> lighterChild = leftHeavy ? criticalPoint.getRightChild() : criticalPoint.getLeftChild();
			if (lighterChild.isPresent()) {
				lighterChild.get().setColor(COLOR.BLACK);
				return;
			}
			if (leftHeavy) {
				leftRebalance(criticalPoint);
			} else {
				rightRebalance(criticalPoint);
			}

		}
	}

	private void rightRebalance(RbNode<T> criticalPoint) {

		Optional<RbNode<T>> oHeavierChild = criticalPoint.getRightChild();
		if (!oHeavierChild.isPresent()) {
			throw new IllegalStateException("Critical point without heavier child: " + criticalPoint);
		}
		RbNode<T> heavierChild = oHeavierChild.get();
		boolean leftPresent = heavierChild.getLeftChild().isPresent();
		boolean rightPresent = heavierChild.getRightChild().isPresent();
		if (!(leftPresent || rightPresent)) {
			heavierChild.setColor(COLOR.RED);
			redPropertyRebuild(heavierChild);
			return;
		}
		if (!rightPresent) {
			rightRotate(heavierChild);
			heavierChild = heavierChild.getParent().get();
		}
		leftRotate(criticalPoint);
		heavierChild.setColor(criticalPoint.getColor());
		criticalPoint.setColor(COLOR.BLACK);
		heavierChild.getRightChild().get().setColor(COLOR.BLACK);
		if (criticalPoint.getRightChild().isPresent()) {
			criticalPoint.getRightChild().get().setColor(COLOR.RED);
		}

	}

	private void leftRebalance(RbNode<T> criticalPoint) {

		Optional<RbNode<T>> oHeavierChild = criticalPoint.getLeftChild();
		if (!oHeavierChild.isPresent()) {
			throw new IllegalStateException("Critical point without heavier child: " + criticalPoint);
		}
		RbNode<T> heavierChild = oHeavierChild.get();
		boolean leftPresent = heavierChild.getLeftChild().isPresent();
		boolean rightPresent = heavierChild.getRightChild().isPresent();
		if (!(leftPresent || rightPresent)) {
			heavierChild.setColor(COLOR.RED);
			redPropertyRebuild(heavierChild);
			return;
		}
		if (!leftPresent) {
			leftRotate(heavierChild);
			heavierChild = heavierChild.getParent().get();
		}
		rightRotate(criticalPoint);
		heavierChild.setColor(criticalPoint.getColor());
		criticalPoint.setColor(COLOR.BLACK);
		heavierChild.getLeftChild().get().setColor(COLOR.BLACK);
		if (criticalPoint.getLeftChild().isPresent()) {
			heavierChild.getLeftChild().get().setColor(COLOR.RED);
		}

	}

	protected static class CriticalPoint<T extends Comparable<T>> {

		private Optional<RbNode<T>> node;
		private boolean leftHeavy = false;

		public CriticalPoint(Optional<RbNode<T>> node, boolean leftHeavy) {
			super();
			this.node = node;
			this.leftHeavy = leftHeavy;
		}

		protected Optional<RbNode<T>> getNode() {
			return node;
		}

		protected void setNode(Optional<RbNode<T>> node) {
			this.node = node;
		}

		protected boolean isLeftHeavy() {
			return leftHeavy;
		}

		protected void setLeftHeavy(boolean leftHeavy) {
			this.leftHeavy = leftHeavy;
		}

	}

}
