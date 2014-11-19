
public class Version1 extends Version0 {
	
	public Version1(CensusData census, int x, int y) {
		super(census, x, y);
		init();
	}
	
	public void init() {
		if (census.data_size > 0) {
			rec.bottom = census.data[0].latitude; 
			rec.top = census.data[0].latitude;
			rec.left = census.data[0].longitude;
			rec.right = census.data[0].longitude;
			
			// set population
			rec.population += census.data[0].population;
			for (int i = 1; i < census.data_size; i++) {
				rec.bottom = Math.min(rec.bottom, census.data[i].latitude);
				rec.top = Math.max(rec.top, census.data[i].latitude);
				rec.left = Math.min(rec.left, census.data[i].longitude);
				rec.right = Math.max(rec.right, census.data[i].longitude);
				rec.population += census.data[i].population;
         }
		}
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
		// calculate the width of a chunk for x and y
		float widthXChunk = (rec.right - rec.left) / x;
		float widthYChunk = (rec.top - rec.bottom) / y;

		float boundWest = (west - 1) * widthXChunk + rec.left;
		float boundEast = (east) * widthXChunk + rec.left;
		float boundSouth = (south - 1) * widthYChunk + rec.bottom;
		float boundNorth = (north) * widthYChunk + rec.bottom;
      
		return(census.longitude >= boundWest && census.longitude <= boundEast &&
               census.latitude >= boundSouth && census.latitude <= boundNorth); 
	}
}
