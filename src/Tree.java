/**
 * A data structure use to represent a binary tree represent where left subtree contains
 * nodes left than or equals to parents and right subtree contains nodes larger than parent
 * node.
 * @author senghuot
 */
public class Tree {
	
	private Node root;
	
	/**
	 * @effect construct a new Tree with root sets to null
	 */
	public Tree() {
		root = null;
	}
   
	/**
	 * add a new node to the tree.
	 * @param int value stores as a node.
	 */
	public void add(int value) {
		root = add(root, value);
	}
	
	/**
	 * helper method to help adding new node to the tree.
	 * @param curr as a root of the tree.
	 * @param value as a node to be inserted.
	 * @return a node representation of the new tree.
	 */
	private Node add(Node curr, int value) {
		if(curr == null)
			return new Node(value);
		
		if(value <= curr.getKey())
			curr.left = add(curr.left, value);
		else
			curr.right = add(curr.right, value);
		return curr;
	}
   
	/**
	 * @return true iff tree is empty else return false.
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * @return key of the node.
	 */
	public int getKey() {
		return root.getKey();
	}
	
	/**
	 * @return the root node of the tree.
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * @return left subtree.
	 */
	public Node getLeft() {
		return root.left;
	}
	
	/**		
	 * @return right subtree.
	 */
	public Node getRight() {
		return root.right;
	}
}
