import java.util.concurrent.RecursiveTask;

public class Version2Query extends RecursiveTask<Integer> {
	private int lo;
	private int hi;
	private int x;
	private int y;
	private CensusData census;
	private Rectangle rec;
	private Rectangle queryRec;
	
	public static final int SEQUENCTIAL_CUTOFF = 1000;
	
	public Version2Query(int l, int h, CensusData c, Rectangle r, Rectangle q, int x, int y) {
		lo = l;
		hi = h;
		census = c;
		rec = r;
		queryRec = q;
		this.x = x;
		this.y = y;
	}
	
	
	protected Integer compute() {
		if (hi - lo < SEQUENCTIAL_CUTOFF) {
			int res = 0;
			for (int i = lo; i < hi; i++) {
				CensusGroup tmp = census.data[i];
				if (contains(tmp))
					res += tmp.population;
			}
			return res;
		} else {
			Version2Query left = new Version2Query(lo, (hi + lo) / 2, census, rec, queryRec, x, y);
			Version2Query right = new Version2Query((hi + lo) / 2, hi, census, rec, queryRec, x, y);
			left.fork();
			int rightAns = right.compute();
			int leftAns = left.join();
			return rightAns + leftAns;
		}
	}
	
	private boolean contains(CensusGroup census) {		
		// calculate the width of a chunk for x and y
		float widthXChunk = (rec.right - rec.left) / x;
		float widthYChunk = (rec.top - rec.bottom) / y;

		float boundWest = (queryRec.left - 1) * widthXChunk + rec.left;
		float boundEast = queryRec.right * widthXChunk + rec.left;
		float boundSouth = (queryRec.bottom - 1) * widthYChunk + rec.bottom;
		float boundNorth = queryRec.top * widthYChunk + rec.bottom;
      
		return(census.longitude >= boundWest && census.longitude <= boundEast &&
               census.latitude >= boundSouth && census.latitude <= boundNorth);
	}
}
