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
	    Rectangle ans = new Rectangle(0, 0, 0, 0, 0);
        if (hi - lo < SEQUENCTIAL_CUTOFF) {
            if (hi - lo > 0) {
            	ans.left = census.data[lo].longitude; 
      			ans.right = census.data[lo].longitude;
      			ans.bottom = census.data[lo].latitude;
      			ans.top = census.data[lo].latitude;
      			ans.population += census.data[lo].population;
               
      			for (int i = lo + 1; i < hi; i++) {
      				CensusGroup tmp = census.data[i];
      				ans.population += tmp.population;
   				
      				// calculating the bound
      				ans.left = Math.min(ans.left, tmp.longitude);
      				ans.right = Math.max(ans.right, tmp.longitude);
      				ans.top = Math.max(ans.top, tmp.latitude);
      				ans.bottom = Math.min(ans.bottom, tmp.latitude);
      			}
            }   
			return ans;
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
