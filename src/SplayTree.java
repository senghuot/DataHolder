import java.io.PrintStream;

/**
 * 
 * @author Senghuot Lim
 */

public class SplayTree {

	private SplayNode root;
	private SplayNode splayNode;
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
		splayNode = null;
	}
   
	/**
	 * 
	 * @param key
	 */
	public void splay(int key) {
		if (root == null)
			return;
		root = splay(root, key);
		if (splayNode.getKey() < root.getKey() && root.left != null)
			root = rotate(root, 'l');
		else if (splayNode.getKey() > root.getKey() && root.right != null)
			root = rotate(root, 'r');      
		splayNode = null;
	}
	
	/**
	 * 
	 * @param root
	 * @param key
	 * @return
	 */
	private SplayNode splay(SplayNode root, int key) {		
		// going into left subtree
		if (key < root.getKey()) {
			if(root.left == null) {
				splayNode = root;
				return root;
			}
			// hoop into left subtree
			root.left = splay(root.left, key);
         
			// check if current node is a parent
			if(root.left == splayNode)
				return root;
			// check if current node is a grandparent
			if(key < root.left.getKey()) {
				root = rotate(root, 'l');
				root = rotate(root, 'l');
			}else {
				root.left = rotate(root.left, 'r');
				root = rotate(root, 'l');
			}

		// going into right subtree
		} else if (key > root.getKey()) {
			if (root.right == null) {
				splayNode = root;
				return root;
			}
			// hoop into right subtree
			root.right = splay(root.right, key);

			// check if current node is a parent
			if (root.right == splayNode)
				return root; 
			// check if current node is a grandparent
			if (key > root.right.getKey()) {
				root = rotate(root, 'r');
				root = rotate(root, 'r');
			} else {
				root.right = rotate(root.right, 'l');
				root = rotate(root, 'r');
			}
		}

		// set our node to be splayed
		splayNode = root;
		return root;
	}
	
	/**
	 * 
	 * @param root
	 * @return
	 */
	private SplayNode rotate(SplayNode root, char dir) {
		if (dir == 'l') {
			SplayNode tmpRoot = root.left;
			SplayNode subB = tmpRoot.right;
			root.left = subB;
			tmpRoot.right = root;
			return tmpRoot;
		} else {
			SplayNode tmpRoot = root.right;
			SplayNode subB = tmpRoot.left;
			tmpRoot.left = root;
			root.right = subB;
			return tmpRoot;
      }
	} 
	
	/**
	 * add a new node to the tree.
	 * @param int value stores as a node.
	 */
	public void insert(int value) {		    
		if (!lookup(value)) {
			if (root == null)
				root = new SplayNode(value);
			else if (value > root.getKey()) {
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
	}
   
	/**
	 *
	*/
	public boolean lookup(int key) {
		splay(key);
		return (root != null && key == root.getKey());
	}
   
	/**
	 * 
	 * @param key
	 */
	public void delete(int key) {
		splay(key);
		if(root.getKey() == key) {
			SplayTree t1 = new SplayTree(root.left);
			SplayTree t2 = new SplayTree(root.right);
			this.root = concat(t1, t2).root;
		}
	}

	/*
	 * 
	 */
	public SplayTree concat(SplayTree t1, SplayTree t2) {
		// check if t2 is empty
		if (t2 == null)
			return t1;
		
		//splay the first tree around +oo
		t1.splay(Integer.MAX_VALUE);
		SplayNode tmpNode = t1.root;
	   
		// then make the second tree the right subtree
		tmpNode.right = t2.root;
		return new SplayTree(tmpNode);
	}
   
	public void display(String command, PrintStream output) {
		output.println(command);
		display(root, "", output);
	}
   
	private void display(SplayNode root, String inden, PrintStream output) {
		if (root == null)
			return;
	   
		// print out the root
		output.println(inden + root.getKey());
      
		// print left subtree
		if (root.left == null && root.right != null)
			output.println(inden + "  -");
		else
			display(root.left, inden + "  ", output);

		// print right subtree
		if (root.left != null && root.right == null)
			output.println(inden + "  -");
		else
			display(root.right, inden + "  ", output);
	}
}
