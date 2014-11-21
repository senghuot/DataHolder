package hw6;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Version2 extends Version0 {	
	
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	
	public Version2(CensusData census, int x, int y) {
		super(census, x, y);
		rec = fjPool.invoke(new Version2Init(0, census.data_size, census));
	}
	
	public void query(int west, int south, int east, int north) {
		Rectangle qTmp = new Rectangle(west, east, north, south, 0);
		int currPop = fjPool.invoke(new Version2Query(0, census.data_size, census, rec, qTmp, x, y));
		print(currPop);
	}
}
