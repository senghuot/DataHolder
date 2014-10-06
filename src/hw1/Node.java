package hw1;

public class Node {
	
	Node next;
	private int data;
	
	public Node(int data) {
		next = null;
		this.data = data; 
	}
	
	public int getData() {
		return data;
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	public Node getNext() {
		return next;
	}
}
