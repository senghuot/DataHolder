/**
 * Version 3 performs extra pre-processing before attempting any query. Pre-processing allows
 * version 3 to answer queries in constant time. 
 * @author senghuot
 */
public class Version3 extends Version1 {

	private int[][] popGrid; // to group and store cummulative population
	
	// constructor for version 3
	public Version3(CensusData census, int x, int y) {
		super(census, x, y);
		fillPopGrid();
	}
	
	// using query bounded rectangle then print out the population and its ratio
    public void query(int west, int south, int east, int north) {
    	// convert the query coordinate to base zero
    	int currPop = Version3s.query(west, south, east, north, popGrid);
    	print(currPop);
   }

    // sequentially build a population grid then convert it into a cummulative grid
	private void fillPopGrid() {
		popGrid = new int[x][y];
		
		// get the width of each grid in X and Y
		float widthX = (rec.right - rec.left) / x;
		float widthY = (rec.top - rec.bottom) / y;
		int indexX;
		int indexY;
		
		// read the population into population grid
		for (int i = 0; i < census.data_size; i++) {
			// calculate out the index
			indexX = (int)((census.data[i].longitude - rec.left) / widthX);
			indexY = (int)((census.data[i].latitude - rec.bottom) / widthY);
			indexX = Math.min(indexX, x - 1);
			indexY = Math.min(indexY, y - 1);
			
			// update population grid
			popGrid[indexX][indexY] += census.data[i].population;
		}
		
		Version3s.cumulativeGrid(popGrid);
	}
}
