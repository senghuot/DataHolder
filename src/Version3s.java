
public class Version3s {
	
	public static void cumulativeGrid(int[][] popGrid) {
		// convert population grid into cumulative grid
		for (int i = 0; i < popGrid.length; i++) {
			for (int j = 0; j < popGrid[0].length; j++) {
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

    public static int query(int west, int south, int east, int north, int[][] popGrid) {
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

        return total;
     }
}
