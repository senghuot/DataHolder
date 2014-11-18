
public class Version1 extends Version {
	
	public Version1(CensusData census, int x, int y) {
		super(census, x, y);
		init();
	}
	
	public void init() {
		if (census.data_size > 0) {
			minY = census.data[0].latitude; 
			maxY = census.data[0].latitude;
			minX = census.data[0].longitude;
			maxX = census.data[0].longitude;
			
			// set population
			population += census.data[0].population;
			for (int i = 1; i < census.data_size; i++) {
				minY = Math.min(minY, census.data[i].latitude);
				maxY = Math.max(maxY, census.data[i].latitude);
				minX = Math.min(minX, census.data[i].longitude);
				maxX = Math.max(maxX, census.data[i].longitude);
				population += census.data[i].population;
         }
		}
	}
	
	public void query(int west, int south, int east, int north) {
		int currPop = getPopulation(west, south, east, north);
		double ratio = (100.0 * currPop) / population;
		print(currPop, ratio);
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
		// calculate the width of a chunk for x and y
		float widthXChunk = (maxX - minX) / x;
		float widthYChunk = (maxY - minY) / y;

		float boundWest = (west - 1) * widthXChunk + minX;
		float boundEast = (east) * widthXChunk + minX;
		float boundSouth = (south - 1) * widthYChunk + minY;
		float boundNorth = (north) * widthYChunk + minY;
      
		return(census.longitude >= boundWest && census.longitude <= boundEast &&
               census.latitude >= boundSouth && census.latitude <= boundNorth); 
	}
}
