
public class Version1s {
	
	public static Rectangle init(int lo, int hi, CensusData census) {
		Rectangle ans = new Rectangle(0, 0, 0, 0, 0);
		if (hi - lo > 0) {
			ans.bottom = census.data[lo].latitude; 
			ans.top = census.data[lo].latitude;
			ans.left = census.data[lo].longitude;
			ans.right = census.data[lo].longitude;
			
			// set population
			ans.population += census.data[lo].population;
			for (int i = 1; i < hi; i++) {
				ans.bottom = Math.min(ans.bottom, census.data[i].latitude);
				ans.top = Math.max(ans.top, census.data[i].latitude);
				ans.left = Math.min(ans.left, census.data[i].longitude);
				ans.population += census.data[i].population;
			}
		}
		return ans;
	}
	
	public static boolean contains(CensusGroup census, Rectangle rec, Rectangle queryRec, int x, int y) {
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
