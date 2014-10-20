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
	 * 
	 * @param key
	 */
	public void splay(int key) {
		root = splay(root, key);
	}
	
	/**
	 * 
	 * @param root
	 * @param key
	 * @return
	 */
	private SplayNode splay(SplayNode root, int key) {
		if(root == null)
			return null;
		
		// going into left subtree
		if(key < root.getKey()) {
			if(root.left == null)
				return root;
			
		// going into right subtree
		} else if(key > root.getKey()) {
			if(root.right == null)
				return root;
		}
		
		return root;
	}
	
	/**
	 * 
	 * @param root
	 * @return
	 */
	private SplayNode rotate(SplayNode root, char dir) {
		
		return root;
	} 
	
	/**
	 * add a new node to the tree.
	 * @param int value stores as a node.
	 */
	public void insert(int value) {
		// the tree is initially empty.
		if(root == null)
			root = new SplayNode(value);
		
		// splaying will bring the key to root to only then decide 
		// weather duplicate occur else we would simply insert a new key.
		root = splay(root, value);
		if(value > root.getKey()) {
			SplayNode tmp = new SplayNode(value);
			tmp.left = root;
			tmp.right = root.right;
			root.right = null;
			root = tmp;
		} else if(value < root.getKey()) {
			SplayNode tmp = new SplayNode(value);
			tmp.right = root;
			tmp.left = root.left;
			root.left = null;
			root = tmp;
		}
	}
	
	/**
	 * Print out the current state of the tree using preorder tra
	 */
	public void display() {
		display(root);
	}
	
	private void display(SplayNode root) {
		
	}
}
