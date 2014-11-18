
public class Version3 extends Version1 {

	private int[][] popGrid;
	
	public Version3(CensusData census, int x, int y) {
		super(census, x, y);
		fillPopGrid();
	}
	
    public void query(int west, int south, int east, int north) {
      // convert the query coordinate to base zero
      west--;
      south--;
      east--;
      north--;
      int total = popGrid[east][north];
      
      // remove top
      if (south - 1 >= 0)
         total -= popGrid[east][south - 1];
      // remove left
      if (west - 1 >= 0)
         total -= popGrid[west - 1][south];
      // add some of it back in
      if (south - 1 >=0 && west - 1 >= 0)
         total += popGrid[west - 1][south - 1];
      
      double ratio = (100.0 * total) / population;
      print(total, ratio);
   }

	private void fillPopGrid() {
		popGrid = new int[x][y];
		
		// get the width of each grid in X and Y
		float widthX = (maxX - minX) / x;
		float widthY = (maxY - minY) / y;
		int indexX;
		int indexY;
		
		// read the population into population grid
		for (int i = 0; i < census.data_size; i++) {
			// calculate out the index
			indexX = (int)((census.data[i].longitude - minX) / widthX);
			indexY = (int)((census.data[i].latitude - minY) / widthY);
			indexX = Math.min(indexX, x - 1);
			indexY = Math.min(indexY, y - 1);
			
			// update population grid
			popGrid[indexX][indexY] += census.data[i].population;
		}
		
		// convert population grid into cumulative grid
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
	            int total = popGrid[i][j];
	            // grab top
	            if (j - 1 >= 0)
	               total += popGrid[i][j -1];
	            // grab left
	            if (i - 1 >= 0)
	               total += popGrid[i-1][j];
	            if (j - 1 >=0 && i - 1 >= 0)
	               total -= popGrid[i -1][j - 1];
	          
	            popGrid[i][j] = total;
			}
		} 
	}
}
