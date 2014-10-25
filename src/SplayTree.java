import java.io.PrintStream;

/**
 * SplayTree is a self-adjusting binary search tree which bring the most recent
 * access node as root for faster future access
 * @author Senghuot Lim
 */

public class SplayTree {

	private int rotations;       // keep track of the number of single tree rotations
	private SplayNode root;	     // root of the tree
	private SplayNode splayNode; // node to be splayed on
	
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
		rotations = 0;
	}
   
	/**
	 * @param key to be splayed
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
	 * helper method to splay the tree based on input key
	 * @param root the current root to be splayed
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
	 * rotate 'l' means rotate root with left subtree
	 * rotate 'r' means rotate root with right subtree
	 * @param root to be rotate
	 * @param dir direction of the subtree to be rotate
	 * @return rotated SlayTree
	 */
	private SplayNode rotate(SplayNode root, char dir) {
		// enable for testing mode only 
		// rotations++;
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
	 * @param key for looking up
	 * @return true iff the key is found. return false otherwise.
	 */
	public boolean lookup(int key) {
		splay(key);
		return (root != null && key == root.getKey());
	}
   
	/**
	 * delete a node containing key. ignore if key is not found.
	 * @param key to be splayed
	 */
	public void delete(int key) {
		splay(key);
		if(root != null && root.getKey() == key) {
			SplayTree t1 = new SplayTree(root.left);
			SplayTree t2 = new SplayTree(root.right);
			this.root = concat(t1, t2).root;
		}
	}

	/**
	 * concatenating t1 and t2 while under the assumption that the 
	 * larger element in t1 is smaller than the smallest element in t2
	 * @param t1 first SplayTree
	 * @param t2 second SplayTree
	 * @return merged SplayTree 
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

	/**
	 * print out the triggered command and the current stage of the tree based
	 * on preorder traversal.
	 * @param command
	 * @param output
	 */
	public void display(String command, PrintStream output) {
		output.println(command);
		display(root, "", output);
	}

	/**
	 * helper method for printing out the output t
	 * @param root, the current node 
	 * @param inden, the amount of spaces before root output
	 * @param output, 
	 */
	private void display(SplayNode root, String inden, PrintStream output) {
		// print root is "-" if null
		if (root == null) {
			output.println(inden + "-");
			return;
		}
			
		// print out keys the using in-order traversal
		output.println(inden + root.getKey());
		if(root.left == null && root.right == null)
			return;
      
		// its not a leaf then go left then right subtree   
		display(root.left, inden + "  ", output);
		display(root.right, inden + "  ", output);
	}
	
	/**
	 * @return int number of rotation of a single tree. 
	 */
	public int getRotations() {
		int tmp = rotations;
		rotations = 0;
		return tmp;
	}
}
