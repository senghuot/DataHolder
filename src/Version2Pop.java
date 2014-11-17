import java.util.concurrent.RecursiveTask;

public class Version2Pop extends RecursiveTask<Integer> {
	private int lo;
	private int hi;
	private CensusData census;
	private Rectangle rec;
	private Rectangle queryRec;
	private int x;
	private int y;
	
	
	public static final int SEQUENCTIAL_CUTOFF = 1000;
	
	public Version2Pop(int l, int h, CensusData c, Rectangle r, Rectangle q, int x, int y) {
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
			Version2Pop left = new Version2Pop(lo, (hi + lo) / 2, census, rec, queryRec, x, y);
			Version2Pop right = new Version2Pop((hi + lo) / 2, hi, census, rec, queryRec, x, y);
			left.fork();
			int rightAns = right.compute();
			int leftAns = left.join();
			return rightAns + leftAns;
		}
	}
	
	private boolean contains(CensusGroup census) {		
		// calculate the width for x and y
		float widthX = (rec.right - rec.left) / x;
		float widthY = (rec.top - rec.bottom) / y;
		
		int currX = (int)((census.latitude - rec.left) / widthX);
		int currY = (int)((census.longitude - rec.bottom) / widthY);

		currX = Math.min(currX, x - 1);
		currY = Math.min(currY, y - 1);
      
		return(currX >= queryRec.left && currX <= queryRec.right && 
				currY >= queryRec.bottom && currY <= queryRec.top);
	}
}
