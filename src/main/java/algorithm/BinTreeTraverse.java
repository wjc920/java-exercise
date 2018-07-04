package algorithm;

import java.util.Stack;

/**
 * 二叉树遍历
 * 
 * @author wjc920
 */
public class BinTreeTraverse {

	private Node<Integer> root;

	public BinTreeTraverse() {
		root = new Node<Integer>(1);
		root.left = new Node<Integer>(2);
		root.right = new Node<Integer>(3);
		root.left.left = new Node<Integer>(4);
		root.right.left = new Node<Integer>(5);
		root.right.right = new Node<Integer>(6);
	}

	/**
	 * @Description: 先序遍历
	 *    
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	public void preTraverseNoRecursion() {
		Stack<Node<Integer>> s = new Stack<>();
		if (root != null) {
			s.push(root);
		}
		Node<Integer> tmp = null;
		while (!s.isEmpty()) {
			tmp = s.peek();
			System.out.print(tmp.t);
			while (tmp.left != null) {
				tmp = tmp.left;
				System.out.print(tmp.t);
				s.push(tmp);
			}

			while (!s.empty()) {
				tmp = s.pop();
				if (tmp.right != null) {
					s.push(tmp.right);
					break;
				}
			}
		}
		System.out.println();
	}

	/**
	 * @Description: 中序遍历
	 *    
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	public void midTraverseNoRecursion() {
		Stack<Node<Integer>> s = new Stack<>();
		if (root != null) {
			s.push(root);
		}
		Node<Integer> tmp = null;
		while (!s.isEmpty()) {
			tmp = s.peek();
			while (tmp.left != null) {
				tmp = tmp.left;
				s.push(tmp);
			}

			while (!s.empty()) {
				tmp = s.pop();
				System.out.print(tmp.t);
				if (tmp.right != null) {
					s.push(tmp.right);
					break;
				}
			}
		}
		System.out.println();
	}

	/**
	 * @Description:后续遍历
	 * 
	 * @author wjc920
	 * @date 2018年7月4日
	 */
	public void postTraverseNoRecursion() {
		Stack<Node<Integer>> s = new Stack<>();
		Stack<Node<Integer>> s1 = new Stack<>();
		if (root != null) {
			s.push(root);
		}
		Node<Integer> tmp = null;
		while (!s.isEmpty()) {
			tmp = s.peek();
			while (tmp.left != null) {
				tmp = tmp.left;
				s.push(tmp);
			}

			while (!s.empty()) {
				tmp = s.peek();
				//当一个节点沿着左子树向下遍历到底部时，开始向上回退，每次回退都会遍历其右子树：
				//1.如果右子树不空：
				if (tmp.right != null) {
					//1.1如果该节点在s1中出现过，则说明右子树已经遍历了，弹出并打印
					if (!s1.isEmpty() && tmp == s1.peek()) {
						System.out.print(s1.pop().t);
						s.pop();
					} else {//1.2如果没有出现过，则需要遍历右子树
						s1.push(tmp);
						s.push(tmp.right);
						break;
					}
				} else {//2.如果右子树为空则，直接弹出栈，并打印
					System.out.print(s.pop().t);
				}
			}
		}
		System.out.println();
	}

	public void print() {
		preTraverseNoRecursion();
		midTraverseNoRecursion();
		postTraverseNoRecursion();
	}

	public static void main(String[] args) {
		new BinTreeTraverse().print();
	}

}
