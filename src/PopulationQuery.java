import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PopulationQuery {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE  = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX   = 5;
	public static final int LONGITUDE_INDEX  = 6;
	
	// parse the input file into a large array held in a CensusData object
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();
		
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(filename));
            
            // Skip the first line of the file
            // After that each line has 7 comma-separated numbers (see constants above)
            // We want to skip the first 4, the 5th is the population (an int)
            // and the 6th and 7th are latitude and longitude (floats)
            // If the population is 0, then the line has latitude and longitude of +.,-.
            // which cannot be parsed as floats, so that's a special case
            //   (we could fix this, but noisy data is a fact of life, more fun
            //    to process the real data as provided by the government)
            
            String oneLine = fileIn.readLine(); // skip the first line

            // read each subsequent line and add relevant data to a big array
            while ((oneLine = fileIn.readLine()) != null) {
                String[] tokens = oneLine.split(",");
                if(tokens.length != TOKENS_PER_LINE)
                	throw new NumberFormatException();
                int population = Integer.parseInt(tokens[POPULATION_INDEX]);
                if(population != 0)
                	result.add(population,
                			   Float.parseFloat(tokens[LATITUDE_INDEX]),
                		       Float.parseFloat(tokens[LONGITUDE_INDEX]));
            }

            fileIn.close();
        } catch(IOException ioe) {
            System.err.println("Error opening/reading/writing input or output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
        return result;
	}

	// argument 1: file name for input data: pass this to parse
	// argument 2: number of x-dimension buckets
	// argument 3: number of y-dimension buckets
	// argument 4: -v1, -v2, -v3, -v4, or -v5
	public static void main(String[] args) {
		
		// this is for testing purpose redirection only
		String[] test = new String[5];
		test[1] = "CenPop2010.txt";
		test[2] = "100";
		test[3] = "500";
		test[4] = "-v1";
		args = test;
		
		// shut down the problem if user doesn't provide at least 4 arguments
		if (args.length < 4)
			System.exit(0);
		
		// parsing to get X & Y coordinates and other variable
		int x = Integer.parseInt(args[2]);
		int y = Integer.parseInt(args[3]);
		CensusData census = parse(args[1]);
      
		// might have to re-design the class. possibly having a superclass then
		// four different version of class
		Version v = null;
		if (args[4].equals("-v1")) {
			v = new Version1(census, x, y);
		} else if (args[4].equals("-v2")) {
			v = new Version2(census, x, y);
		} else if (args[4].equals("-v3")) {
			v = new Version3(census, x, y);
		} else if (args[4].equals("-v4")) {
         v = new Version4(census, x, y);
      }
			
		Scanner console = new Scanner(System.in);

		// break and read the input from user.
		System.out.println("Please give west, south, east, north coordinates of your query");
		String[] input = console.nextLine().split(" ");
		while (input.length == 4) {
			int west = Integer.parseInt(input[0]);
			int south = Integer.parseInt(input[1]);
			int east = Integer.parseInt(input[2]);
			int north = Integer.parseInt(input[3]);

			v.query(west, south, east, north); 

			System.out.println("Please give west, south, east, north coordinates of your query");
			input = console.nextLine().split(" ");
		}
	}
}
