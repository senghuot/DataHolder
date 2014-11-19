import java.util.concurrent.ForkJoinPool;

public class Version4 extends Version2 {
	
	private int[][] popGrid;
	private Version3s v3s;
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	
	public Version4(CensusData c, int x, int y) {
		super(c, x, y); // use version constructor then the rec will get completed in parallel
		popGrid = fjPool.invoke(new Version4init(0, census.data_size, x, y, rec, census));
		Version3s.cumulativeGrid(popGrid);
	}

    public void query(int west, int south, int east, int north) {
    	int currPop = Version3s.query(west, south, east, north, popGrid);
    	print(currPop);
     }
}
