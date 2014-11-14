import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class VersionII extends Version {	
	
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	
	public VersionII(CensusData census, int x, int y) {
		super(census, x, y);
		init();
	}

	private void init() {
		Rectangle tmp = fjPool.invoke(new Version2Thread(census, 0, census.data_size));
	}
}
