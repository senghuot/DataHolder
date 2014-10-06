package hw1;

public class Tree {
	
	private Node root;
	
	public Tree() {
		this(null);
	}
	
	public Tree(Node value) {
		root = value;
	}
	
	public void add(int value) {
		root = add(root, value);
	}
	
	private Node add(Node curr, int value) {
		if(curr == null)
			return new Node(value);
		
		if(value <= curr.getData())
			curr.left = add(curr.left, value);
		else
			curr.right = add(curr.right, value);
		return curr;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public Node getKey() {
		return root;
	}
	
	public Node leftSubTree() {
		return root.left;
	}
	
	public Node rightSubTree() {
		return root.right;
	}
}
