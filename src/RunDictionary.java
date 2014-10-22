import java.io.*;
import java.util.*;

/**
 * This program is taking two filenames as arguments. The first one as input, second one as output file.
 * This software support three operations: 'insert n', 'delete n', and 'lookup n'. The main goal of this
 * program is to test the implementation of the our SplayTree implementation.
 * @author Senghuot Lim
 */
public class RunDictionary {
   
	public static void main(String[] args) {
		// throw exception if user doesn't provide correct inputs
 		if(args.length != 2)
 			throw new IllegalArgumentException("Please provide correct inputs.");

 		// read and construct a new tree based on input file 
 		SplayTree tree = new SplayTree();
		try {
			// preparing input and output files
			Scanner input = new Scanner(new File(args[0]));
			PrintStream output = new PrintStream(new File(args[1]));
			
			// loop over the input file
			while (input.hasNextLine()) {
				String command = input.next();
				int num = input.nextInt();
				if (command.equals("insert"))
					tree.insert(num);
				else if (command.equals("delete"))
					tree.delete(num);
	            else if (command.equals("lookup"))
	            	tree.lookup(num);
	            else {
	            	output.println("Unkown command line");
	            	return;
	            }
				
				// write out the current state of the tree
				tree.display(command + num, output);
			}
			
			// closing scanner port
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
   }  
}