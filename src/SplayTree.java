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
          root = tmpRoot;
          return tmpRoot;
      } else {
          SplayNode tmpRoot = root.right;
          SplayNode subB = tmpRoot.left;
          tmpRoot.left = root;
          root.right = subB;
          root = tmpRoot;
          return tmpRoot;
      }
	} 
	
	/**
	 * add a new node to the tree.
	 * @param int value stores as a node.
	 */
	public void insert(int value) {
		// the tree is initially empty.
		if (root == null)
		    root = new SplayNode(value);
		else {   
			// splaying will bring the key to root to only then decide 
			// weather duplicate occur else we would simply insert a new key.
			splay(value);
			if (value > root.getKey()) {
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
      return (key == root.getKey());
   }
   
   
}
