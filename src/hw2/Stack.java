package hw2;
/**
 * A stack data structure to store data in a fashion of last in first out.
 * @author senghuot
 */

import java.util.EmptyStackException;

public class Stack {

	private int top;
	private Tree[] storage;
	private int capacity;

	/**
	 * @effect construct a new Stack
	 */
	public Stack() {
		capacity = 1000;
		storage = new Tree[capacity];
		top = 0;
	}
	
	/**
	 * push a node onto the stack. The size of the stack will be double when the stack
	 * is getting full.
	 * @param value to be push into the stack.
	 */
	public void push(Tree value) {
		if(top == storage.length) {
			capacity *= 2;
			Tree[] largerStorage = new Tree[capacity];
			for(int i = 0; i < storage.length; i++)
				largerStorage[i] = largerStorage[i];
			storage = largerStorage;
		}
		storage[top++] = value;
	}
	
	/**
	 * return the most recent push data without any pop operation.
	 * @return the most recent data.
	 */
	public Tree top() {
		if(top == 0)
			throw new EmptyStackException();
		return storage[top - 1];
	}
	
	/**
	 * @return most recently added node.
	 * @throws EmptyStackException if attempt to pop an empty stack.
	 */
	public Tree pop() {
		if(top == 0)
			throw new EmptyStackException();
		return storage[--top];
	}
	
	/**
	 * @return true iff the stack is empty else false.
	 */
	public boolean isEmpty() {
		return top == 0;
	}
}