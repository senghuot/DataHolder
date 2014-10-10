package hw1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

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
		this(null);
	}
	/**
	* @param root as a root of the tree.
	* @effect construct a new Tree with user given root.
	*/
	public Tree(Node root) {
		this.root = root;
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
	 * @return left subtree.
	 */
	public Tree getLeft() {
		return new Tree(root.left);
	}
	
	/**		
	 * @return right subtree.
	 */
	public Tree getRight() {
		return new Tree(root.right);
	}
	
	/**
	 * traverse tree using stack data structure to get key in sorted order 
	 * then print out result to filename.
	 * @param filename to be output the result.
	 */
	public void output(String filename) throws FileNotFoundException {
		PrintStream output = new PrintStream(new File(filename));
		Stack stack = new Stack();
		Tree curr = this;
		while(!stack.isEmpty() || !curr.isEmpty()) {
			if(!curr.isEmpty()) {
				stack.push(curr);
				curr = curr.getLeft();
			} else {
				curr = stack.pop();
				output.println(curr.getKey());
				curr = curr.getRight();
			}
		}
		output.close();		
	}
}
