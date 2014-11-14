import java.util.concurrent.RecursiveTask;

public class Version2Thread extends RecursiveTask<Rectangle> {
	private int lo;
	private int hi;
	private CensusData census;
	
	public static final int SEQUENCTIAL_CUTOFF = 1000;
	
	public Version2Thread(int l, int h, CensusData c) {
		census = c;
		hi = h;
		l = lo;
	}
	
	//float l, float r, float t, float b, int p
	@Override
	protected Rectangle compute() {
		if (hi - lo < SEQUENCTIAL_CUTOFF) {
			Rectangle ans = new Rectangle(Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MAX_VALUE, 0);
			for (int i = lo; i < hi; i++) {
				CensusGroup tmp = census.data[i];
				ans.population += tmp.population;
				
				// calculating the bound
				ans.left = Math.min(ans.left, tmp.latitude);
				ans.right = Math.max(ans.right, tmp.latitude);
				ans.top = Math.max(ans.top, tmp.longitude);
				ans.bottom = Math.min(ans.bottom, tmp.longitude);
			}
			return ans;
		} else {
			Version2Thread left = new Version2Thread(lo, (hi + lo) / 2, census);
			Version2Thread right = new Version2Thread((hi+ lo) / 2, hi, census);
			left.fork();
			Rectangle rightAns = right.compute();
			Rectangle leftAns = left.join();
			return rightAns.encompass(leftAns);
		}
	}
}
