import java.util.concurrent.RecursiveTask;

public class Version4init extends RecursiveTask<int[][]> {
	private int lo;
	private int hi;
	private int x;
	private int y;
	private Rectangle rec;
	private CensusData census;
	
	public static final int SEQUENCTIAL_CUTOFF = 1000;
	public Version4init(int l, int h, int x, int y, Rectangle r, CensusData c) {
		census = c;
		hi = h;
		lo = l;
		rec = r;
		this.x = x;
		this.y = y;
	}
	
	//float l, float r, float t, float b, int p
	@Override
	protected int[][] compute() {      
        if (hi - lo < SEQUENCTIAL_CUTOFF) {
        	int[][] ans = new int[x][y];
        	
    		float widthX = (rec.right - rec.left) / x;
    		float widthY = (rec.top - rec.bottom) / y;
    		int indexX;
    		int indexY;
    		
        	for (int i = lo; i < hi; i++) {
        		// calculate out the index
    			indexX = (int)((census.data[i].latitude - rec.left) / widthX);
    			indexY = (int)((census.data[i].longitude - rec.bottom) / widthY);
    			indexX = Math.min(indexX, x - 1);
    			indexY = Math.min(indexY, y - 1);
    			
    			// update population grid
    			ans[indexX][indexY] += census.data[i].population;
        	}
			return ans;
   		} else {
   			Version4init left = new Version4init(lo, (hi + lo) / 2, x, y, rec, census);
   			Version4init right = new Version4init((hi+ lo) / 2, hi, x, y, rec, census);
   			left.fork();
   			int[][] rightAns = right.compute();
   			int[][] leftAns = left.join();
            
   			return leftAns;
   		}
	}
}
