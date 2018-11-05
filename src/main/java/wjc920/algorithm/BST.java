package wjc920.algorithm;

/**
 * 二叉查找树（binary search tree）
 * 
 * @author wjc920
 */
public class BST<K extends Comparable<K>, V> {

	private Node root;

	/**
	 * @author wjc920
	 */
	protected class Node {
		protected K key;
		protected V value;
		protected Node left, right;

		public Node() {}
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		@Override
		public String toString() {
			StringBuffer sb=new StringBuffer();
			sb.append("{\"key\":" + key);
			sb.append(",\"left\":" + (left == null ? "null" : left.toString()));
			sb.append(",\"right\":" + (right == null ? "null" : right.toString()));//此处?:运算如果不带括号，会报错，优先级低于+
			sb.append("}");
			return sb.toString();
		}

	}


/**
 * @Description: 插入节点
 * @param key
 * @param value
 * @author wjc920
 * @date 2018年6月26日
 */
public void put(K key, V value) {
	Node toPut=new Node(key, value);
	Node cur=root;
	//在遍历树之前，先判断树是否为空，若空着执行插入，结束
	if(root==null) {
		root=toPut;
		return;
	}
	while(true) {
		int cmp=key.compareTo(cur.key);               
		if (cmp < 0){
			//如果代码执行到这里，下一步将对cur的左子树遍历，
			//在遍历左子树之前先判断是否为空，若空着执行插入，结束
			if(cur.left==null) {       
				cur.left=toPut;
				break;
			}
			cur=cur.left;                             
		}else if (cmp > 0) {  
			//如果代码执行到这里，下一步将对cur的右子树遍历，
			//在遍历右子树之前先判断是否为空，若空着执行插入，结束
			if(cur.right==null) {
				cur.right=toPut;
				break;
			}
			cur=cur.right;                            
		}else {                                             
			cur.value=toPut.value;
			break;
		}
	}
}

	/**
	 * @Description: 删除操作的非递归实现
	 * @param key
	 * @return   
	 * @author wjc920  
	 * @date 2018年6月30日
	 */
	public V remove(K key) {
		Node cur=root,target,replace,p=null;
		V result;
		while(cur!=null) {                                //寻找target，target.key==key
			int cmp=key.compareTo(cur.key);               //寻找target，target.key==key
			if (cmp < 0){                                 //寻找target，target.key==key
				p=cur;                                    //寻找target，target.key==key
				cur=cur.left;                             //寻找target，target.key==key
			}else if (cmp > 0) {                          //寻找target，target.key==key
				p=cur;                                    //寻找target，target.key==key 
				cur=cur.right;                            //寻找target，target.key==key
			}else                                         //寻找target，target.key==key    
				break;                                    //寻找target，target.key==key
		}
		target=cur;
		if(target==null)                                  //如果target==null，则不需要删除，返回null
			return null;                                  //如果target==null，则不需要删除，返回null
		result=target.value;                              //如果target!=null，则需要删除，先将target的value保存，作为返回结果
		if(target.left!=null) {
			if(target.right!=null) {                      //1 如果target.left!=null且target.right!=null
				p=target;                                 //找后继节点，即大于target的最小节点
				cur=target.right;                         //找后继节点，即大于target的最小节点
				while(cur.left!=null) {                   //找后继节点，即大于target的最小节点
					p=cur;								  //找后继节点，即大于target的最小节点
					cur=cur.left;                         //找后继节点，即大于target的最小节点
				}                                         //找后继节点，即大于target的最小节点
				
				replace=cur;                              //将后继节点替换tagert
				target.key=replace.key;                   //将后继节点替换tagert
				target.value=replace.value;               //将后继节点替换tagert
				if(replace.right!=null) {                 //1.1替换后，如果replace.right!=null
					replace.key=replace.right.key;        //用右节点替换replace，删除右节点
					replace.value=replace.right.value;    //用右节点替换replace，删除右节点
					replace.right=null;                   //用右节点替换replace，删除右节点
				}else {                                   //1.2 替换后，如果replace.right==null
					if(p.left==replace) {                 //删除replace
						p.left=null;                      //删除replace
					}else {                               //删除replace
						p.right=null;                     //删除replace
					}                                     //删除replace
				}
			}else {                                       //2 如果target.left!=null且target.right==null
				target.key=target.left.key;               //用左节点替换target
				target.value=target.left.value;           //用左节点替换target
				target.right=target.left.right;           //用左节点替换target
				target.left=target.left.left;             //用左节点替换target
			}
		}else {
			if(target.right!=null) {                     //3 如果target.left==null且target.right!=null
				target.key=target.right.key;             //用右节点替换target
				target.value=target.right.value;         //用右节点替换target
				target.left=target.right.left;           //用右节点替换target
				target.right=target.right.right;         //用右节点替换target
				
			}else {                                      //4 如果target.left==null且target.right==null
				if(p.left==target) {                     //删除target
					p.left=null;                         //删除target
				}else {                                  //删除target
					p.right=null;                        //删除target
				}
			}
		}
		return result;
	}

/**
 * @Description: 获取键key的值
 * @param key
 * @return
 * @author wjc920
 * @date 2018年6月26日
 */
public V get(K key) {
	return get(root, key).value;
}

private Node get(Node node, K key) {
	if (node == null)
		return null;
	int cmp = key.compareTo(node.key);
	if (cmp < 0)
		return get(node.left, key);
	else if (cmp > 0)
		return get(node.right, key);
	else
		return node;
}




	public static void main(String[] args) {
		BST<Integer, Integer> bst = buildBST();
		/**
		 *                 6
		 *               /   \
		 *              2      9
		 *             /\     / \  
		 *            1   4  7   10
		 *           /   / \ \     \
		 *          0   3   5 8     17
		 *{"key":6,
		 * "left":{"key":2,
		 *         "left":{"key":1,
		 *                 "left":{"key":0,
		 *                         "left":null,
		 *                         "right":null},
		 *                 "right":null},
		 *         "right":{"key":4,
		 *                  "left":{"key":3,
		 *                          "left":null,
		 *                          "right":null
		 *                         },
		 *                  "right":{"key":5,
		 *                           "left":null,
		 *                           "right":null}
		 *                 }
		 *         },
		 * "right":{"key":9,
		 *          "left":{"key":7,
		 *                  "left":null,
		 *                  "right":{"key":8,
		 *                           "left":null,
		 *                           "right":null
		 *                          }
		 *                 },
		 *          "right":{"key":10,
		 *                    "left":null,
		 *                    "right":{"key":17,
		 *                             "left":null,
		 *                             "right":null
		 *                            }
		 *                  }
		 *          }
		 *}

		 */
		System.out.println(bst.root.toString());
		//删除左子树为null的节点
		bst.remove(1);
		System.out.println("bst.remove(1);");
		System.out.println(bst.root.toString());
		/**
		 *                 6
		 *               /   \
		 *              2      9
		 *             /\     / \  
		 *            0   4  7   10
		 *               / \ \     \
		 *              3   5 8     17
		 */
		//删除右子树为null的节点
		bst.remove(7);
		System.out.println("bst.remove(7);");
		System.out.println(bst.root.toString());
		/**
		 *                 6
		 *               /   \
		 *              2      9
		 *             /\     / \  
		 *            0   4  8   10
		 *               / \       \
		 *              3   5       17
		 */
		//删除左右子树均为null的节点
		bst.remove(0);
		System.out.println("bst.remove(0);");
		System.out.println(bst.root.toString());
		/**
		 *                 6
		 *               /   \
		 *              2      9
		 *              \     / \  
		 *                4  8   10
		 *               / \       \
		 *              3   5       17
		 */
		//删除根节点
		bst.remove(6);
		System.out.println("bst.remove(6);");
		System.out.println(bst.root.toString());
		/**
		 *                 8
		 *               /   \
		 *              2      9
		 *              \       \  
		 *                4      10
		 *               / \       \
		 *              3   5       17
		 */
		bst.put(8, 9);
	}

	public static BST<Integer, Integer> buildBST() {
		BST<Integer, Integer> bst = new BST<>();
		int[] elements = new int[] { 6, 2, 1,0, 4, 3, 9, 7, 8, 5, 10, 17 };
		for (int i : elements) {
			bst.put(i, i);
		}
		return bst;
	}

}
