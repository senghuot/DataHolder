import hw1.Node;

/**
 * This string
 * @author senghuot
 */

public class SplayNode {
	private int key;
	SplayNode left;
	SplayNode right;
	
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
