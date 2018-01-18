package it.hella.search;

public class RbNode<T extends Comparable<T>> extends AbstractNode<T, RbNode<T>> {

	enum COLOR {
		RED, BLACK
	}

	private COLOR color = COLOR.BLACK;

	public RbNode(T key) {
		super(key);
	}

	public RbNode(T key, COLOR color) {
		super(key);
		this.color = color;
	}

	public COLOR getColor() {
		return color;
	}

	public void setColor(COLOR color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Node [key=" + key + ", color=" + color + ", leftChild="
				+ (leftChild.isPresent() ? leftChild.get().getKey() : "nil") + ", rightChild="
				+ (rightChild.isPresent() ? rightChild.get().getKey() : "nil") + ", parent="
				+ (parent.isPresent() ? parent.get().getKey() : "nil") + "]";
	}

}
