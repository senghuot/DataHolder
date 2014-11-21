package hw6;


public class Version1 extends Version0 {
	
	public Version1(CensusData census, int x, int y) {
		super(census, x, y);
		Version1s.init(census, rec, 0, census.data_size);
	}
	
	public void query(int west, int south, int east, int north) {
		int currPop = getPopulation(west, south, east, north);
		print(currPop);
	}
   
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
