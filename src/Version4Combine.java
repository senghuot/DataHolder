import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class Version4Combine extends RecursiveAction {
	private int lo;
	private int hi;
	private int[][] left;
	
	public static final int SEQUENCTIAL_CUTOFF = 1000;
	
	public Version4Combine(int l, int h, int[][] le) {
		lo = l;
		hi = h;
		left = le;
	}
	
	protected void compute() {
		if (hi - lo < SEQUENCTIAL_CUTOFF) {
			
		} else {
			
		}
	}
}
