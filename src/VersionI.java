
public class VersionI {

	public CensusData census;
	public int x;
	public int y;
	public int population;
	public float minX;
	public float maxX;
	public float minY;
	public float maxY;
	
	public VersionI(CensusData census, int x, int y) {
		this.census = census;
		this.x = x;
		this.y = y;
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
      System.out.println("population of rectangle: " + currPop);
      System.out.printf("percent of total: %.2f \n", ratio);
   }
   
	public int getPopulation(int west, int south, int east, int north) {
		int res = 0;
		for (int i = 0; i < census.data_size; i++)
			if (contains(census.data[i], west, south, east, north))
				res += census.data[i].population;
		return res;
	}
	
	public boolean contains(CensusGroup census, int west, int south, int east, int north) {
		// users use index base 1 instead of 0
		west--;
		south--;
		east--;
		north--;
		
		// calculate the width for x and y
		float widthX = (maxX - minX) / x;
		float widthY = (maxY - minY) / y;
		
		int currX = (int)((census.longitude - minX) / widthX);
		int currY = (int)((census.latitude - minY) / widthY);

      currX = Math.min(currX, x - 1);
      currY = Math.min(currY, y - 1);
      
		return(currX >= west && currX <= east && currY >= south && currY <= north);
	}
}
