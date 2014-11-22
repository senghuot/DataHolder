/**
 * Version 1 using a sequential O(n) algorithm where n is the number of census
 * block groups.
 * @author senghuot
 */
public class Version1 extends Version0 {
	
	// construct version 1 object
	public Version1(CensusData census, int x, int y) {
		super(census, x, y);
		Version1s.init(census, rec, 0, census.data_size);
	}
	
	// using query bounded rectangle then print out the population and its ratio
	public void query(int west, int south, int east, int north) {
		int currPop = getPopulation(west, south, east, north);
		print(currPop);
	}
   
	// helper method to get the population
	private int getPopulation(int west, int south, int east, int north) {
		int res = 0;
		for (int i = 0; i < census.data_size; i++)
			if (contains(census.data[i], west, south, east, north))
				res += census.data[i].population;
		return res;
	}
	
   // pass Inclusive X and Y bound chunks - 1 based indexing.
	private boolean contains(CensusGroup census, int west, int south, int east, int north) {
		Rectangle queryRec = new Rectangle(west, east, north, south, 0);
		return Version1s.contains(census, rec, queryRec, x, y);
	}
}
