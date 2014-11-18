import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class Version4 extends Version2 {
	
	private int[][] popGrid;
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	
	public Version4(CensusData c, int x, int y) {
		super(c, x, y);
		Rectangle tmp = new Rectangle(minX, maxX, maxY, minY, 0);
		popGrid = fjPool.invoke(new Version4init(0, census.data_size, x, y, tmp, census));
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

    public void query(int west, int south, int east, int north) {
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
        System.out.println("population of rectangle: " + total);
        System.out.printf("percent of total: %.2f \n", ratio);
    }
}
