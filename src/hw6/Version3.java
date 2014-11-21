package hw6;


public class Version3 extends Version1 {

	private int[][] popGrid;
	
	public Version3(CensusData census, int x, int y) {
		super(census, x, y);
		fillPopGrid();
	}
	
    public void query(int west, int south, int east, int north) {
    	// convert the query coordinate to base zero
    	int currPop = Version3s.query(west, south, east, north, popGrid);
    	print(currPop);
   }

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
