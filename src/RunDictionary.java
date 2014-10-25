import java.io.*;
import java.util.*;

/**
 * This program is taking two filenames as arguments. The first one as input, second one as output file.
 * This software support three operations: 'insert n', 'delete n', and 'lookup n'. The main goal of this
 * program is to test the implementation of the our SplayTree implementation.
 * @author Senghuot Lim
 */
public class RunDictionary {
   
	// acts as the power, of 2^POWER. used for testing. Anything above 20 will slow down significantly.
	public static final int POWER = 20;
	
	public static void main(String[] args) throws FileNotFoundException {
		reads(args);
		// enable for testing mode only 
		// tests();
	}
	
	/**
	 * reading input file contains insert, delete, lookup command.
	 * if unknown command appears then exit the program.
	 * @param args contains input and output filename
	 * @throws FileNotFoundException if input file does not exist 
	 */
	public static void reads(String[] args) throws FileNotFoundException {
		// throw exception if user doesn't provide correct inputs
 		if(args.length != 2)
 			throw new IllegalArgumentException("Please provide correct inputs.");

 		// read and construct a new tree based on input file 
 		SplayTree tree = new SplayTree();
		try {
			// preparing input and output files
			// ***************************************************************** //
			Scanner input = new Scanner(new File(args[0]));
			PrintStream output = new PrintStream(new File(args[1]));
			// ***************************************************************** //
         
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
				tree.display(command + " " + num, output);
			}
			
			// closing scanner and output ports
			input.close();
			output.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File does not exist.");
		}
	}
	
	/**
	 *  to run some experiments testing the performance of the splay tree.
	 *  We're hoping to exploit the O(n) run time despite the amortized O(log n). 
	 */
	public static void tests() {
		SplayTree tree = new SplayTree();
		Random rand = new Random();

		// automatic testing
		System.out.println("N\t M\t T\t");
		for (int i = 0; i <= POWER; i++) {
			int n = (int)Math.pow(2, i);
			int m = 2 * n;
			
			// inserting nodes into our tree
			for (int j = 0; j < n; j++)
				tree.insert(j);

			// lookup nodes using our tree
			for (int k = n; k < m; k++) {
				int num = rand.nextInt(n + 1);
				tree.lookup(num);
			}
			System.out.print(n);
			System.out.print("\t " + m);
			System.out.println("\t " + tree.getRotations());      
		}
	}
}