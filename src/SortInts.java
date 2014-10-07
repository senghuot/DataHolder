/**
 * Read and attempt to sort inputs from user using a tree data structure.
 * Traverse the tree using stack instead of recursively backtracking to keep
 * track of the parent nodes. Output a file with sorted inputs.
 */
import java.io.*;
import java.util.*;

public class SortInts {

	public static void main(String[] args) throws FileNotFoundException {
		
		// throw exception if user doesn't provide correct inputs.
 		if(args.length != 2)
 			throw new IllegalArgumentException("Please provide correct inputs.");
		
 		// read and construct a new tree based on user input. 
		Tree tree = null;
		try {
			Scanner input = new Scanner(new File(args[0]));
			tree = new Tree( );
			while(input.hasNextInt())
				tree.add(input.nextInt());
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
		// traverse tree using stack data structure then print output to a file.
		PrintStream output = new PrintStream(new File(args[1]));
		Stack stack = new Stack();
		stack.push(tree.getRoot());
		while(!stack.isEmpty()) {
			while(stack.top() != null)
				stack.push(stack.top().left);
			stack.pop();
			if(!stack.isEmpty()) {
				Node tmp = stack.pop();
				output.println(tmp.getKey());
				stack.push(tmp.right);
			}
		}
		output.close();
	}
}
