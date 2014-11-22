import java.util.concurrent.RecursiveTask;

/**
 * A thread to find the corners in parallel.
 * @author senghuot
 */
public class Version2Init extends RecursiveTask<Rectangle> {
	private int lo;
	private int hi;
	private CensusData census;
	
	public static final int SEQUENCTIAL_CUTOFF = 500000;
	
	
	// to construct the thread
	public Version2Init(int l, int h, CensusData c) {
		census = c;
		hi = h;
		lo = l;
	}
	
	// return a rectangle represent corner according to 'hi' and 'lo' indexes
	protected Rectangle compute() {      
        if (hi - lo < SEQUENCTIAL_CUTOFF) {
        	Rectangle rec = new Rectangle(0, 0, 0, 0, 0);
        	Version1s.init(census, rec, lo, hi);
			return rec;
   		} else {
   			Version2Init left = new Version2Init(lo, (hi + lo) / 2, census);
   			Version2Init right = new Version2Init((hi+ lo) / 2, hi, census);
   			left.fork();
   			Rectangle rightAns = right.compute();
   			Rectangle leftAns = left.join();
   			return rightAns.encompass(leftAns);
   		}
	}
}
