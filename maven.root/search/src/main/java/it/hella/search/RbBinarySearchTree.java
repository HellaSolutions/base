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

		Optional<CriticalPoint<T>> oCriticalPoint = removeAndTargetCriticalPoint(z);
		rebalance(oCriticalPoint);
		if (root.isPresent()) {
			root.get().setColor(COLOR.BLACK);
		}

	}

	protected Optional<CriticalPoint<T>> removeAndTargetCriticalPoint(RbNode<T> z) {

		CriticalPoint<T> criticalPoint;
		Optional<RbNode<T>> optSubstitute;
		boolean leftPresent = z.getLeftChild().isPresent();
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
			RbNode<T> criticalNode = oCriticalPoint.get().getNode().get();
			boolean leftHeavy = oCriticalPoint.get().isLeftHeavy();
			Optional<RbNode<T>> lighterChild = leftHeavy ? criticalNode.getRightChild() : criticalNode.getLeftChild();
			if (lighterChild.isPresent()) {
				lighterChild.get().setColor(COLOR.BLACK);
				return;
			}
			if (leftHeavy) {
				leftRebalance(criticalNode);
			} else {
				rightRebalance(criticalNode);
			}

		}
	}

	private void rightRebalance(RbNode<T> criticalNode) {

		Optional<RbNode<T>> oHeavierChild = criticalNode.getRightChild();
		if (!oHeavierChild.isPresent()) {
			throw new IllegalStateException("Critical point without heavier child: " + criticalNode);
		}
		RbNode<T> heavierChild = oHeavierChild.get();
		boolean leftPresent = heavierChild.getLeftChild().isPresent();
		boolean rightPresent = heavierChild.getRightChild().isPresent();
		COLOR criticalNodeColor = criticalNode.getColor();
		if (leftPresent || rightPresent) {
			if (heavierChild.getColor() == COLOR.RED) {
				heavierChild.setColor(COLOR.BLACK);
				leftRotate(criticalNode);
				heavierChild = criticalNode.getRightChild().get();
				leftPresent = heavierChild.getLeftChild().isPresent();
				rightPresent = heavierChild.getRightChild().isPresent();
				if (!(leftPresent || rightPresent)) {
					heavierChild.setColor(COLOR.RED);
					return;
				}
				criticalNodeColor = COLOR.RED;
			}
			if (!rightPresent) {
				rightRotate(heavierChild);
				heavierChild = heavierChild.getParent().get();
			}
			leftRotate(criticalNode);
			heavierChild.setColor(criticalNodeColor);
			criticalNode.setColor(COLOR.BLACK);
			heavierChild.getRightChild().get().setColor(COLOR.BLACK);
			if (criticalNode.getRightChild().isPresent()) {
				criticalNode.getRightChild().get().setColor(COLOR.RED);
			}
		} else {
			heavierChild.setColor(COLOR.RED);
			if (criticalNode.getColor() == COLOR.RED) {
				criticalNode.setColor(COLOR.BLACK);
			} else {
				iterativeRebalance(new CriticalPoint<>(criticalNode.getParent(), !criticalNode.isLeftChild()));
			}
		}

	}

	private void leftRebalance(RbNode<T> criticalNode) {

		Optional<RbNode<T>> oHeavierChild = criticalNode.getLeftChild();
		if (!oHeavierChild.isPresent()) {
			throw new IllegalStateException("Critical point without heavier child: " + criticalNode);
		}
		RbNode<T> heavierChild = oHeavierChild.get();
		boolean leftPresent = heavierChild.getLeftChild().isPresent();
		boolean rightPresent = heavierChild.getRightChild().isPresent();
		COLOR criticalNodeColor = criticalNode.getColor();
		if (leftPresent || rightPresent) {
			if (heavierChild.getColor() == COLOR.RED) {
				heavierChild.setColor(COLOR.BLACK);
				rightRotate(criticalNode);
				heavierChild = criticalNode.getLeftChild().get();
				leftPresent = heavierChild.getLeftChild().isPresent();
				rightPresent = heavierChild.getRightChild().isPresent();
				if (!(leftPresent || rightPresent)) {
					heavierChild.setColor(COLOR.RED);
					return;
				}
				criticalNodeColor = COLOR.RED;
			}
			if (!leftPresent) {
				leftRotate(heavierChild);
				heavierChild = heavierChild.getParent().get();
			}
			rightRotate(criticalNode);
			heavierChild.setColor(criticalNodeColor);
			criticalNode.setColor(COLOR.BLACK);
			heavierChild.getLeftChild().get().setColor(COLOR.BLACK);
			if (criticalNode.getLeftChild().isPresent()) {
				criticalNode.getLeftChild().get().setColor(COLOR.RED);
			}
		} else {
			heavierChild.setColor(COLOR.RED);
			if (criticalNode.getColor() == COLOR.RED) {
				criticalNode.setColor(COLOR.BLACK);
			} else {
				iterativeRebalance(new CriticalPoint<>(criticalNode.getParent(), !criticalNode.isLeftChild()));
			}
		}
	}

	private void iterativeRebalance(CriticalPoint<T> criticalNode) {

		if (criticalNode.node.isPresent()) {
			if (criticalNode.leftHeavy) {
				leftIterativeRebalance(criticalNode.node.get());
			} else {
				rightIterativeRebalance(criticalNode.node.get());
			}
		}

	}

	private void leftIterativeRebalance(RbNode<T> criticalNode) {

		Optional<RbNode<T>> oLighterChild = criticalNode.getRightChild();
		if (oLighterChild.isPresent() && oLighterChild.get().getColor() == COLOR.RED) {
			oLighterChild.get().setColor(COLOR.BLACK);
			return;
		}
		Optional<RbNode<T>> oHeavierChild = criticalNode.getLeftChild();
		if (!oHeavierChild.isPresent()) {
			throw new IllegalStateException("Critical point without heavier child: " + criticalNode);
		}
		RbNode<T> heavierChild = oHeavierChild.get();
		if (heavierChild.getColor() == COLOR.BLACK) {
			COLOR originalCriticalNodeColor = criticalNode.getColor();
			Optional<RbNode<T>> optHeavierChildRightChild = heavierChild.getRightChild();
			if ((!optHeavierChildRightChild.isPresent()) || optHeavierChildRightChild.get().getColor() == COLOR.BLACK) {
				rightRotate(criticalNode);
				criticalNode.setColor(COLOR.RED);
				criticalNode = heavierChild;
			} else {
				leftRotate(heavierChild);
				rightRotate(criticalNode);
				criticalNode.setColor(COLOR.BLACK);
				criticalNode = optHeavierChildRightChild.get();
				criticalNode.setColor(COLOR.RED);
			}
			if (originalCriticalNodeColor == COLOR.BLACK) {
				iterativeRebalance(new CriticalPoint<>(criticalNode.getParent(), !criticalNode.isLeftChild()));
			}
		} else {
			rightRotate(criticalNode);
			heavierChild.setColor(COLOR.BLACK);
			criticalNode.setColor(COLOR.RED);
			leftIterativeRebalance(criticalNode);
		}

	}

	private void rightIterativeRebalance(RbNode<T> criticalNode) {

		Optional<RbNode<T>> oLighterChild = criticalNode.getLeftChild();
		if (oLighterChild.isPresent() && oLighterChild.get().getColor() == COLOR.RED) {
			oLighterChild.get().setColor(COLOR.BLACK);
			return;
		}
		Optional<RbNode<T>> oHeavierChild = criticalNode.getRightChild();
		RbNode<T> heavierChild = oHeavierChild.get();
		if (heavierChild.getColor() == COLOR.BLACK) {
			COLOR originalCriticalNodeColor = criticalNode.getColor();
			Optional<RbNode<T>> optHeavierChildLeftChild = heavierChild.getLeftChild();
			if ((!optHeavierChildLeftChild.isPresent()) || optHeavierChildLeftChild.get().getColor() == COLOR.BLACK) {
				leftRotate(criticalNode);
				criticalNode.setColor(COLOR.RED);
				criticalNode = heavierChild;
			} else {
				rightRotate(heavierChild);
				leftRotate(criticalNode);
				criticalNode.setColor(COLOR.BLACK);
				criticalNode = optHeavierChildLeftChild.get();
				criticalNode.setColor(COLOR.RED);
			}
			if (originalCriticalNodeColor == COLOR.BLACK) {
				iterativeRebalance(new CriticalPoint<>(criticalNode.getParent(), !criticalNode.isLeftChild()));
			}
		} else {
			leftRotate(criticalNode);
			heavierChild.setColor(COLOR.BLACK);
			criticalNode.setColor(COLOR.RED);
			rightIterativeRebalance(criticalNode);
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
