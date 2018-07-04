package algorithm;

import java.util.Stack;

/**
 * 红黑树
 * 
 * @author wjc920
 */
public class RBT<K extends Comparable<K>, V> {

	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	private Node root;

	class Node {
		private K key;
		private V value;
		private Node parent, left, right;
		private boolean red;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.red = RED;
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("{\"key\":" + key + ",\"color\":" + red + ",\"parent\":" + (parent == null ? "null" : parent.value));
			sb.append(",\"left\":" + (left == null ? "null" : left.toString()));
			sb.append(",\"right\":" + (right == null ? "null" : right.toString()));// 此处?:运算如果不带括号，会报错，优先级低于+
			sb.append("}");
			return sb.toString();
		}
		
	}
	@Override
	public String toString() {
		return root.toString();
	}
	
	/**
	 * @Description: 插入节点
	 * 1.空树
	 * 2.如果添加到黑色节点下，无需调整，结束；
	 * 3.如果添加到红色节点下，需调增，调用调增方法
	 * @param key
	 * @param value   
	 * @author wjc920  
	 * @date 2018年6月28日
	 */
	public void put(K key, V value) {
		Node cur=root;
		Node x=new Node(key, value);
		if(cur==null) {
			root=x;
			root.red=false;
			return;
		}
		while(true) {
			int cmp = key.compareTo(cur.key);
			if (cmp < 0) {
				if(cur.left==null) {
					cur.left=x;
					x.parent=cur;
					if(cur.red)
						balanceAfterInsert(cur.left);
					break;
				}
				cur=cur.left;
			}else if (cmp > 0) {
				if(cur.right==null) {
					cur.right=x;
					x.parent=cur;
					if(cur.red)
						balanceAfterInsert(cur.right);
					break;
				}
				cur=cur.right;
			} else {
				cur.value = value;
				break;
			}
		}		
	}
	
	/**
	 * @Description: 对插入的红色点做平衡处理
	 *   3.1 grandpa.left==papa.left==x     (==表示引用指向的意思)
	 *      3.1.1 grandpa.right的颜色为黑色
	 *      3.1.2 grandpa.right的颜色为红色
	 *   3.2 grandpa.left==papa.right==x    
	 *      3.2.1 grandpa.right的颜色为黑色
	 *      3.2.2 grandpa.right的颜色为红色
	 *   3.3 grandpa.right==papa.left==x  
	 *      3.3.1 grandpa.left的颜色为黑色
	 *      3.3.2 grandpa.left的颜色为红色   
	 *   3.4 grandpa.right==papa.right==x     
	 *      3.4.1 grandpa.left的颜色为黑色
	 *      3.4.2 grandpa.left的颜色为红色   
	 * @param x   
	 * @author wjc920  
	 * @date 2018年6月30日
	 */
	public void balanceAfterInsert(Node x) {
		Node papa,grandpa,uncle;
		papa=parentOf(x);
		grandpa=parentOf(papa);
		while(x!=null&&x!=root&&isRed(papa)) {
			if(papa==leftOf(grandpa)) {
				uncle=rightOf(grandpa);
				if(isRed(uncle)) {//3.1.2&3.2.2 这种情况无需判别x是papa的左还是右，将papa和uncle变黑，grandpa变红
					setColor(uncle, BLACK);
					setColor(papa, BLACK);
					setColor(grandpa, RED);
					x=grandpa;
					papa=parentOf(x);
					grandpa=parentOf(papa);
				}else {
					if(x==rightOf(papa)) {//3.2.1 这种情况通过左旋papa，然后交换x和papa即可变3.1.1
						rotateLeft(papa);
						x=papa;
						papa=parentOf(x);
					}
					
					if(x==leftOf(papa)) {//3.1.1 右旋grandpa之前先将papa变为黑，grandpa变红，目的是将红节点变到右边
						setColor(grandpa, RED);
						setColor(papa, BLACK);
						rotateRight(grandpa);
						x=root;//由于papa已经为black，下次while条件判断将会跳出
					}
				}
			}else {//和上面的情况类似，对称的
				uncle=leftOf(grandpa);
				if(isRed(uncle)) {
					setColor(uncle, BLACK);
					setColor(papa, BLACK);
					setColor(grandpa, RED);
					x=grandpa;
					papa=parentOf(x);
					grandpa=parentOf(papa);
				}else {
					if(x==leftOf(papa)) {
						rotateRight(papa);
						x=papa;
						papa=parentOf(x);
					}
					
					if(x==rightOf(papa)) {
						setColor(grandpa, RED);
						setColor(papa, BLACK);
						rotateLeft(grandpa);
						x=root;//由于papa已经为black，下次while条件判断将会跳出
					}
				}
			}
		}
		setColor(root, BLACK);
	}
	
	/**
	 * @Description: 删除节点
	 * @param key
	 * @return   
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	public V remove(K key) {
		V resultV = null;
		Node cur = get(root, key);
		if (cur != null) {
			resultV = cur.value;
			if (cur.right != null && cur.left != null) {
				Node next = nextNodeForBinTree(cur);
				cur.key = next.key;
				cur.value = next.value;
				cur = next;
			}
			Node replace = cur.left != null ? cur.left : cur.right;
			if (replace != null) {
				if (cur.parent == null) {
					root = replace;
					replace.parent = null;
				} else {
					replace.parent = cur.parent;
					if (cur.parent.left == cur) {
						cur.parent.left = replace;
					} else {
						cur.parent.right = replace;
					}
					cur.left = cur.right = cur.parent = null;
					if (!cur.red) {
						balanceAfterRemove(replace);
					}
				}
			} else {
				if (cur == root) {
					root = null;
				} else {
					if (!cur.red) {
						balanceAfterRemove(cur);
					}
					if (cur.parent != null) {
						if (cur.parent.left == cur)
							cur.parent.left = null;
						else
							cur.parent.right = null;
						cur.parent = null;
					}
				}
			}
		}
		return resultV;
	}
	

	/**
	 * @Description: 查找节点
	 * @param key
	 * @return   
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	public V get(K key) {
		Node target = get(root, key);
		if (target != null)
			return target.value;
		return null;
	}

	private Node get(Node x, K key) {
		Node cur = root;
		while (cur != null) {
			int cmp = key.compareTo(cur.key);
			if (cmp < 0)
				cur = cur.left;
			else if (cmp > 0)
				cur = cur.right;
			else
				break;
		}
		return cur;
	}
	
	/**
	 * @Description: 删除后的平衡处理
	 * @param x   
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	private void balanceAfterRemove(Node x) {
		Node papa, sib;
		while (x != null && x != root && !isRed(x)) {
			papa = parentOf(x);
			if (leftOf(papa) == x) {
				sib = rightOf(papa);
				if (isRed(sib)) {   //对应删除节点图片的第三排
					setColor(papa, RED);
					setColor(sib, BLACK);
					rotateLeft(papa);
					papa = parentOf(x);
					sib = rightOf(papa);
				}
				if (!isRed(leftOf(sib)) && !isRed(rightOf(sib))) {//对应删除节点图片的第五排
					setColor(sib, RED);
					x = papa;
				} else {
					if (isRed(leftOf(sib))) {//对应删除节点图片的第二排
						setColor(leftOf(sib), BLACK);
						setColor(sib, RED);
						rotateRight(sib);
						sib = rightOf(papa);
					}
					setColor(rightOf(sib), BLACK);//对应删除节点图片的第四排
					setColor(sib, isRed(papa));
					setColor(papa, BLACK);
					rotateLeft(papa);
					x = root;
				}
			} else {
				sib = leftOf(papa);
				if (isRed(sib)) {
					setColor(papa, RED);
					setColor(sib, BLACK);
					rotateRight(papa);
					papa = parentOf(x);
					sib = leftOf(papa);
				}
				if (!isRed(leftOf(sib)) && !isRed(rightOf(sib))) {
					setColor(sib, RED);
					x = papa;
				} else {
					if (isRed(rightOf(sib))) {
						setColor(rightOf(sib), BLACK);
						setColor(sib, RED);
						rotateLeft(sib);
						sib = leftOf(papa);
					}
					setColor(leftOf(sib), BLACK);
					setColor(sib, isRed(papa));
					setColor(papa, BLACK);
					rotateRight(papa);
					x = root;

				}
			}
		}
		setColor(x, BLACK);
	}
	

	/**
	 * @Description: 左旋
	 * @param x   
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	private void rotateLeft(Node x) {
		if(x!=null&&x.right!=null) {
			Node right=x.right,papa=x.parent;
			right.parent=papa;
			if (papa != null) {
				if (papa.left == x)
					papa.left = right;
				else
					papa.right = right;
			}else {
				root=right;
			}
			
			x.right=right.left;
			if(right.left!=null)
				right.left.parent=x;
			right.left=x;
			x.parent=right;
		}
		
	}
	
	/**
	 * @Description: 右旋
	 * @param x   
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	private void rotateRight(Node x) {
		if(x!=null&&x.left!=null) {
			Node left=x.left,papa=x.parent;
			left.parent=papa;
			if (papa != null) {
				if (papa.left == x)
					papa.left = left;
				else
					papa.right = left;
			}else {
				root=left;
			}
			
			x.left=left.right;
			if(left.right!=null)
				left.right.parent=x;
			left.right=x;
			x.parent=left;
		}
	}
	
	private boolean isRed(Node x) {
		if(x!=null)
			return x.red;
		return BLACK;
	}
	
	private Node parentOf(Node x) {
		if(x!=null)
			return x.parent;
		return null;
	}
	
	private Node leftOf(Node x) {
		if(x!=null)
			return x.left;
		return null;
	}
	
	private Node rightOf(Node x) {
		if(x!=null)
			return x.right;
		return null;
	}
	
	private void setColor(Node x,boolean color) {
		if(x!=null)
			x.red=color;
	}

	
	/**
	 * @Description: 寻找后继节点
	 * @param x
	 * @return   
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	private Node nextNodeForBinTree(Node x) {
		if(x==null)
			return null;
		else if(x.right!=null) {
			Node t=x.right;
			while(t.left!=null) 
				t=t.left;
			return t;
		}else {
			Node ch=x;
			Node p=ch.parent;
			while(p!=null&&ch==p.right) {
				ch=p;
				p=p.parent;
			}
			return p;
		}
	}
	
	/**
	 * @Description: 遍历树，并判断每个节点是否符合红黑树的要求
	 * @return   
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	public boolean isRBT() {
		Stack<Node> s = new Stack<>();
		if (root != null)
			s.push(root);
		while (!s.isEmpty()) {
			Node tmp = s.peek();
			while (tmp.left != null) {
				s.push(tmp.left);
				tmp = tmp.left;
			}
			while (!s.empty()) {
				tmp = s.pop();
				if(!isRBTNode(tmp))
					return false;
				if (tmp.right != null) {
					s.push(tmp.right);
					break;
				}
			}
		}
		return true;

	}
	
	/**
	 * @Description: 判断节点是否符合红黑树标准
	 * 1.x.key>x.left.key
	 * 2.x.key<x.right.key
	 * 3.if x.red==true,then x.left.red!=true and x.right.red!=null
	 * @param x
	 * @return   
	 * @author wjc920  
	 * @date 2018年7月4日
	 */
	private boolean isRBTNode(Node x) {
		if (isRed(x)) {
			if (isRed(x.left) || isRed(x.right)) {
				return false;
			}
		}
		int p1 = 1, p2 = -1;
		if (x.left != null)
			p1 = x.key.compareTo(x.left.key);
		if (x.right != null)
			p2 = x.key.compareTo(x.right.key);

		if (p1 < 0 || p2 > 0) {
			return false;
		}
		return true;
	}
	
	

}
