package algorithm;

import algorithm.BST.Node;

public class RedBlackTree<K extends Comparable<K>, V> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private class Node {
		private K key;
		private V value;
		private Node left, right;
		private int n;
		private boolean color;

		public Node(K key, V value, int n) {
			this.key = key;
			this.value = value;
			this.n = n;
			this.color = RED;
		}
		
		@Override
		public String toString() {
			StringBuffer sb=new StringBuffer();
			sb.append("{\"key\":" + key + ",\"value\":" + value + ",\"color\":" + color);
			sb.append(",\"left\":" + (left == null ? "null" : left.toString()));
			sb.append(",\"right\":" + (right == null ? "null" : right.toString()));//此处?:运算如果不带括号，会报错，优先级低于+
			sb.append("}");
			return sb.toString();
		}
	}

	private Node root;

	public void put(K key, V value) {
		root=put(root, key, value);
		root.color=BLACK;
	}

	/**
	 * @Description: 
	 * 插入一个节点时有以下几种情况：
	 * 1.不需要做什么
	 *         E(BLACK)
	 *        /  \
	 *  new(RED)  S(BLACK)
	 * 2.x=rotateLeft(E);
	 *         E(BLACK)
	 *        /  \
	 *  S(BLACK)  new(RED)
	 * 3.flipColor(E);
	 *         E(BLACK)
	 *        /  \
	 *  S(RED)  new(RED)
	 * 4.x=rotateLeft(E);
	 *         E(RED)
	 *        /  \
	 *  S(BLAK)  new(RED)
	 * 5.x=rotateRight(P);flipColor(x);
	 *            P
	 *           /
	 *         E(RED)
	 *        /  \
	 *  new(RED)  S(BLACK)
	 *         
	 * @param node
	 * @param key
	 * @param value
	 * @return   
	 * @author wjc920  
	 * @date 2018年6月27日
	 */
	private Node put(Node node, K key, V value) {
		if (node == null)
			return new Node(key, value, 1);
		int cmp = key.compareTo(node.key);
		if (cmp < 0)
			node.left = put(node.left, key, value);
		else if (cmp > 0)
			node.right = put(node.right, key, value);
		else
			node.value = value;
		if (!isRed(node.left) && isRed(node.right))
			node = rotateLeft(node);
		if (isRed(node.left) && isRed(node.left.left))
			node = rotateRight(node);
		if (isRed(node.left) && isRed(node.right))
			flipColor(node);
		node.n = size(node.left) + size(node.right) + 1;
		return node;
	}

	/**
	 * @Description: 左旋（e的右连接逆时针旋转） 
	 * 将下面的图和代码进行比对很容易理解
	 * （注释中变量和代码中变量名称一致）：
	 * before rotate:
	 * 		 |
	 *       E
	 *      / \
	 *     El   S(RED)
	 *        / \
	 *       Sl  Sr
	 * after rotate:
	 * 		 |
	 *       S(E.color)
	 *      / \
	 *  E(RED) Sr
	 *   / \
	 *  El  Sl
	 * @param e
	 * @return   
	 * @author wjc920  
	 * @date 2018年6月27日
	 */
	private Node rotateLeft(Node e) {
		Node s = e.right;
		e.right = s.left;
		s.left = e;
		s.n = e.n;
		e.n = size(e.left) + size(e.right) + 1;
		s.color = e.color;
		e.color = RED;
		return s;
	}
	
	/**
	 * @Description: 右旋（e的左连接顺时针旋转） 
	 * 将下面的图和代码进行比对很容易理解
	 * （注释中变量和代码中变量名称一致）：
	 * 
	 * （注：E.color只能是BLACK，因为不可能出现连续三个节点RED的情况）
	 * 
	 * before rotate:
	 * 		    |
	 *          E
	 *         / \
	 *     S(RED) Er
	 *      / \
	 * A(RED)  Sr
	 *  / \
	 * Al  Ar
	 * 
	 * after rotate:
	 * 		  |
	 *     S(E.color) 
	 *      /   \
	 * A(RED)    E(RED)
	 *  / \       /  \
	 * Al  Ar    Sr   Er
	 * 
	 * @param e
	 * @return   
	 * @author wjc920  
	 * @date 2018年6月27日
	 */
	private Node rotateRight(Node e) {
		Node s=e.left;
		e.left=s.right;
		s.right=e;
		s.color=e.color;
		e.color=RED;
		s.n=e.n;
		e.n=size(e.left)+size(e.right)+1;
		return s;
		
	}
	
	/**
	 * @Description: 颜色变换（node的两个子节点均为RED时）
	 * before:
	 *         |
	 *         E(BLACK)
	 *        / \
	 *   A(RED)  S(RED)
	 * after:
	 *         |
	 *         E(RED)
	 *        / \
	 * A(BLACK)  S(BLACK)
	 * @param node
	 * @return   
	 * @author wjc920  
	 * @date 2018年6月27日
	 */
	private void flipColor(Node node) {
		boolean c = node.color;
		node.color = RED;
		node.left.color = BLACK;
		node.right.color = BLACK;
	}

	public int size() {
		return size(root);
	}

	private int size(Node node) {
		if (node == null)
			return 0;
		else
			return node.n;
	}

	public boolean isRed(Node node) {
		if (node == null)
			return BLACK;
		return node.color;
	}
	
	public static RedBlackTree<Integer, Integer> buildRedBlackTree() {
		RedBlackTree<Integer, Integer> rbt = new RedBlackTree<>();
		int[] elements = new int[] { 6, 2, 1, 4, 3, 9, 7, 8, 5, 10, 17 };
		for (int i : elements) {
			rbt.put(i, i);
		}
		//打印树
		System.out.println(rbt.toString());
		return rbt;
	}
	
	@Override
	public String toString() {
		return root.toString();
	}
	
	
	public static void main(String[] args) {
		buildRedBlackTree();
	}

}
