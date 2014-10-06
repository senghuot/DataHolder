package hw1;

import java.util.EmptyStackException;

public class Stack {

	// reference to the top of the stack.
	private int top;
	private Node[] storage;
	private int capacity;

	public Stack() {
		capacity = 1000;
		storage = new Node[capacity];
		top = 0;
	}
	
	public void push(Node value) {
		if(top == storage.length) {
			capacity *= 2;
			Node[] largerStorage = new Node[capacity];
			for(int i = 0; i < storage.length; i++)
				largerStorage[i] = largerStorage[i];
			storage = largerStorage;
		}
		storage[top++] = value;
	}
	
	public Node top() {
		if(top == 0)
			throw new EmptyStackException();
		return storage[top - 1];
	}
	
	public Node pop() {
		if(top == 0)
			throw new EmptyStackException();
		return storage[--top];
	}
	
	public boolean isEmpty() {
		return top == 0;
	}
}
