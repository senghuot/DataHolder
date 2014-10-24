
/**
 * Act as a storage for each node of a tree. Each node is a data structure consisting of
 * a key value. A single node has enough information to act as a tree.
 * @author senghuot
 */

public class SplayNode {
	private int key;	// node's key
	SplayNode left; 	// reference to left subtree
	SplayNode right;	// reference to right subtree
	
	/**
	 * @param key to be stored for the newly constructed node.
	 * @effect construct a new node base on given key.
	 */
	public SplayNode(int key) {
		this.key = key;
		left = null;
		right = null;
	}
	
	/**
	 * @return key, int of the current node.
	 */
	public int getKey() {
		return key;
	}
}
