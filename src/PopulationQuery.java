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
		String[] test = new String[4];
		test[0] = "CenPop2010.txt";
		test[1] = "100";
		test[2] = "50";
		test[3] = "-v1";
		args = test;
		
		// shut down the problem if user doesn't provide at least 4 arguments
		if (args.length < 4)
			System.exit(0);
		
		// might have to re-design the class. possibly having a superclass then
		// four different version of class
		if (args[3].equals("-v1")) {
			CensusData census = parse(args[0]);
			Scanner console = new Scanner(System.in);
			
			// break and read the input from user.
			int[][] pop = getCensusGrid(100, 500, census);
			System.out.println("Please give west, south, east, north coordinates of your query");
			String[] input = console.nextLine().split(" ");
			while (input.length == 4) {
				int minX = Integer.parseInt(input[0]) - 1;
				int maxX = Integer.parseInt(input[2]);
				int minY = Integer.parseInt(input[1]) - 1;
				int maxY = Integer.parseInt(input[3]);
				
				int total = 0;
				for (int i=minX; i<maxX; i++) {
					for (int j=minY; j<maxY; j++)
						total += pop[i][j];
				}
				System.out.println(total);
				
				System.out.println("Please give west, south, east, north coordinates of your query");
				input = console.nextLine().split(" ");
			}
		}
	}
	
	/**
	 * create a two dimensional array of int for storing population
	 */
	public static int[][] getCensusGrid(int x, int y, CensusData census) {
		int[][] grid = new int[x][y];
		
		// find the Min/Max for longitude and latitude 
		if (census.data_size > 0) {
			float minX = census.data[0].latitude; 
			float maxX = census.data[0].latitude;
			float minY = census.data[0].longitude;
			float maxY = census.data[0].longitude;

			for (int i = 1; i < census.data_size; i++) {
				minX = Math.min(minX, census.data[i].latitude);
				maxX = Math.max(maxX, census.data[i].latitude);
				minY = Math.min(minY, census.data[i].longitude);
				maxY = Math.max(maxY, census.data[i].longitude);
			}
			
			// inserting the population into the population grid
			float widthX = (maxX - minX) / x;
			float widthY = (maxY - minY) / y;
			for (int i = 0; i < census.data_size; i++) {
				int gridX = (int) Math.ceil((census.data[i].latitude - minX) / widthX);
				int gridY = (int) Math.ceil((census.data[i].longitude - minY) / widthY);
				gridX = Math.min(gridX, x - 1);
				gridY = Math.min(gridY, y - 1);
				grid[gridX][gridY] += census.data[i].population;
			}
		}

		return grid;
	}
}
