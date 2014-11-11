
public class VersionII extends VersionI{
	static final int SEQUENTIAL_THRESHOLD = 5000;
	
	private int low;
	private int high;
	
	public VersionII(CensusData census, int x, int y) {
		super(census, x, y);
	}

	protected int compute() {
		if (high - low <= SEQUENTIAL_THRESHOLD) {
			
		}
	}
	
	
}
