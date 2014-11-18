import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Version2 extends Version {	
	
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	
	public Version2(CensusData census, int x, int y) {
		super(census, x, y);
		init();
	}

	private void init() {
		Rectangle tmp = fjPool.invoke(new Version2Init(0, census.data_size, census));
		minY = tmp.bottom;
		maxY = tmp.top;
		minX = tmp.left;
		maxX = tmp.right;
		population = tmp.population;
	}
	
	public void query(int west, int south, int east, int north) {
		Rectangle tmp = new Rectangle(minX, maxX, maxY, minY, 0);
		Rectangle qTmp = new Rectangle(west, east, north, south, 0);
		int currPop = fjPool.invoke(new Version2Query(0, census.data_size, census, tmp, qTmp, x, y));
		double ratio = (100.0 * currPop) / population;
		print(currPop, currPop);
	}
}
