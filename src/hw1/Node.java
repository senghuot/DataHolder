package hw1;

public class Node {
	
	private int data;
	Node left;
	Node right;
	
	public Node(int value) {
		data = value;
		left = null;
		right = null;
	}
	
	public int getData() {
		return data;
	}
	
	public void setData(int value) {
		data = value;
	}
}
