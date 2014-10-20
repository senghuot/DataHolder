/**
 * 
 * @author Senghuot Lim
 */

public class SplayTree {

	private SplayNode root;
	
	/**
	 * @effect construct a new Tree with root sets to null
	 */
	public SplayTree() {
		this(null);
	}
	/**
	* @param root as a root of the tree.
	* @effect construct a new Tree with user given root.
	*/
	public SplayTree(SplayNode root) {
		this.root = root;
	}
   
	/**
	 * add a new node to the tree.
	 * @param int value stores as a node.
	 */
	public void insert(int value) {
		root = insert(root, value);
	}
	
	/**
	 * helper method to help adding new node to the tree.
	 * @param curr as a root of the tree.
	 * @param value as a node to be inserted.
	 * @return a node representation of the new tree.
	 */
	private SplayNode insert(SplayNode root, int value) {
		if(root == null)
			return new SplayNode(value);
		
		if(value <= root.getKey())
			root.left = insert(root.left, value);
		else
			root.right = insert(root.right, value);
		return root;
	}
}
