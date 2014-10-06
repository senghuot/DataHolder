package hw1;

import java.io.*;
import java.util.*;

public class SortInts {

	public static void main(String[] args) throws FileNotFoundException {
// 		if(args.length != 1)
// 			throw new IllegalArgumentException("Please provide correct inputs.");
		
		Tree tree = null;
		try {
		Scanner input = new Scanner(new File("infile.txt"));
		tree = new Tree();
		while(input.hasNextInt())
			tree.add(input.nextInt());
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
      PrintStream output = new PrintStream(new File("outfile.txt"));
		Stack stack = new Stack();
		stack.push(tree.getKey());
		while(!stack.isEmpty()) {
			while(stack.top() != null)
				stack.push(stack.top().left);
			stack.pop();
         if(!stack.isEmpty()) {
            Node tmp = stack.pop();
			   output.println(tmp.getData());
			   stack.push(tmp.right);
         }
		}
	}
}
