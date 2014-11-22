import java.util.concurrent.ForkJoinPool;

/**
 * Much like version 1 except both initial corner-finding and data traversal
 * for each query should is done in parallel
 * @author senghuot
 */
public class Version2 extends Version0 {	
	
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	
	// construct version 2 object then finding all the corner in parallel
	public Version2(CensusData census, int x, int y) {
		super(census, x, y);
		rec = fjPool.invoke(new Version2Init(0, census.data_size, census));
	}
	
	// using query bounded rectangle then print out the population and its ratio
	public void query(int west, int south, int east, int north) {
		Rectangle qTmp = new Rectangle(west, east, north, south, 0);
		int currPop = fjPool.invoke(new Version2Query(0, census.data_size, census, rec, qTmp, x, y));
		print(currPop);
	}
}
