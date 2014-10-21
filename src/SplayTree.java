/**
 * 
 * @author Senghuot Lim
 */

public class SplayTree {

	private SplayNode root;
	private SplayNode splayRoot;
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
		splayRoot = null;
	}
   
	/**
	 * 
	 * @param key
	 */
	public void splay(int key) {
		root = splay(root, key);
      if(key < root.getKey()) {
         if(root.left == null)
            return;
         SplayNode tmpRoot = root.left;
         SplayNode subB = tmpRoot.right;
         root.left = subB;
         tmpRoot.right = root;
         root = tmpRoot;
         
      }else if(key > root.getKey()) {
         if(root.right == null)
            return;
         SplayNode tmpRoot = root.right;
         SplayNode subC = tmpRoot.left;
         root.right = subC;
         tmpRoot.left = root;
         root = tmpRoot;
      }   
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
         if(root.left == null) {
            splayRoot = root;
            return root;
         }
         root.left = splay(root.left, key);
         // current node is a parent
			if(root.left == splayRoot)
				return root;
         
         // current node is a grandparent
         if(root.left != null && key < root.left.getKey())
				root = rotate(root, "ll");
			else if(root.right != null)
				root = rotate(root, "lr");
            
		// going into right subtree
		} else if(key > root.getKey()) {
         if(root.right == null) {
            splayRoot = root;
            return root;
         }           
			root.right = splay(root.right, key);
         // current node is a parent
			if(root.right == splayRoot)
				return root; 
            
         // current node is a grandparent
         if(root.left != null && key > root.left.getKey())
				root = rotate(root, "rr");
			else if(root.right != null)
				root = rotate(root, "rl");	   
		}
		
		return root;
	}
	
	/**
	 * 
	 * @param root
	 * @return
	 */
	private SplayNode rotate(SplayNode root, String dir) {
		if(dir.equals("ll")) {
         SplayNode tmpRoot = root.left.left;
         SplayNode subB = tmpRoot.right;
         SplayNode subC = root.left.right;
         tmpRoot.right = root.left;
         tmpRoot.right.left = subB;
         root = tmpRoot;
         return tmpRoot;
         
      }else if(dir.equals("rr")) {
         SplayNode tmpRoot = root.right.right;
         SplayNode subB = root.right.left;
         SplayNode subC = tmpRoot.left;
         tmpRoot.left = root.right;
         tmpRoot.left.right = subC;
         root.right = subB;
         return tmpRoot;
         
      }else if(dir.equals("rl")) {
         SplayNode tmpRoot = root.left.right;
         SplayNode subA = root.left.left; 
         SplayNode subB = tmpRoot.left;
         SplayNode subC = tmpRoot.right;
         tmpRoot.left = root.right;
         tmpRoot.right = root;
         root.left = subC;
         tmpRoot.left.right = subB;
         return tmpRoot;      
      
      }else {
         SplayNode tmpRoot = root.right.left;
         SplayNode subB = tmpRoot.right;
         SplayNode subC = tmpRoot.left; 
         tmpRoot.left = root;
         tmpRoot.right = root.right;
         tmpRoot.right.left = subC;
         tmpRoot.left.right = subB;
         return tmpRoot;     
      }
	} 
	
	/**
	 * add a new node to the tree.
	 * @param int value stores as a node.
	 */
	public void insert(int value) {
		// the tree is initially empty.
		if(root == null)
			root = new SplayNode(value);
		else {   
   		// splaying will bring the key to root to only then decide 
   		// weather duplicate occur else we would simply insert a new key.
   		splay(value);
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
