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
			Scanner input = new Scanner(new File("infile.txt"));
			tree = new Tree( );
			while(input.hasNextInt())
				tree.add(input.nextInt());
			input.close();
			tree.output("outfile.txt");
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
}
