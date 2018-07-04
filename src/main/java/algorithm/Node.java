package algorithm;

public class Node<T> {
	public T t;
	public Node<T> left,right;
	
	public Node() {}
	public Node(T t) {
		this.t=t;
	}
}
