import java.util.concurrent.RecursiveTask;

public class Version2Init extends RecursiveTask<Rectangle> {
	private int lo;
	private int hi;
	private CensusData census;
	
	public static final int SEQUENCTIAL_CUTOFF = 1000;
	
	public Version2Init(int l, int h, CensusData c) {
		census = c;
		hi = h;
		lo = l;
	}
	
	//float l, float r, float t, float b, int p
	@Override
	protected Rectangle compute() {      
        if (hi - lo < SEQUENCTIAL_CUTOFF) {
        	return Version1s.init(lo, hi, census);
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
