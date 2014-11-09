package hw2;
/**
 * Act as a storage for each node of a tree.
 * @author senghuot
 */
public class Node {
	
	private int key;
	Node left;
	Node right;
	
	/**
	 * @param key to be stored for the newly constructed node.
	 * @effect construct a new node base on given key.
	 */
	public Node(int key) {
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