import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PopulationQueryTest {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE  = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX   = 5;
	public static final int LONGITUDE_INDEX  = 6;
	public static final int WARM_UP = 5;
	public static final int ITERATION = 50; 
	
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
		// shut down the problem if user doesn't provide at least 4 arguments
		if (args.length < 4)
			System.exit(0);
		
		// start timing
		long start = 0;
		long end = 0;
		
		// parsing to get X & Y coordinates and other variable
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		CensusData census = parse(args[0]);
		
		for (int i = 1; i <= ITERATION; i++) {
			if (i == WARM_UP + 1)
				start = System.nanoTime();
				
			// might have to re-design the class. possibly having a superclass then
			// four different version of class
			Version0 v = null;
			String version = args[3];
			if (version.equals("-v1")) {
				v = new Version1(census, x, y);
			} else if (version.equals("-v2")) {
				v = new Version2(census, x, y);
			} else if (version.equals("-v3")) {
				v = new Version3(census, x, y);
			} else if (version.equals("-v4")) {
				v = new Version4(census, x, y);
			}
		
			int west = random(1, x);
			int south = random(1, y);
			int east = random(west, x);
			int north = random(south, y);
			
			if ((west < 1 || west > x) || 
					(north < south || south > y) ||
					(east < west || east > x) ||
					(north < south || south > y)) 
				throw new IllegalArgumentException("Incorrect bounds!");
				
			// query then print out the result
			v.query(west, south, east, north);
		}
		end = System.nanoTime();
		System.out.println("avg: " + ((end - start) / (ITERATION - WARM_UP)));
	}
	
	public static int random(int min, int max) {
		int range = max - min + 1;
		return (int)(Math.random() * range) + min;
	}
}
