
public class Version1 extends Version0 {
	
	public Version1(CensusData census, int x, int y) {
		super(census, x, y);
		rec = Version1s.init(0, census.data_size, census);
	}
	
	public void query(int west, int south, int east, int north) {
		int currPop = getPopulation(west, south, east, north);
		print(currPop);
	}
   
	private int getPopulation(int west, int south, int east, int north) {
		int res = 0;
		for (int i = 0; i < census.data_size; i++) {
			Rectangle queryRec = new Rectangle(west, east, north, south, 0);
			if (Version1s.contains(census.data[i], rec, queryRec, x, y))
				res += census.data[i].population;
		}
		return res;
	}
}
