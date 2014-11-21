package hw6;

/**
 * Code refactoring for Version 1 & 2 due to their similar behaviour.
 * @author senghuot
 */
public class Version1s {
	
	// 
	public static void init(CensusData census, Rectangle rec, int lo, int hi) {
		if (hi - lo > 0) {
			rec.bottom = census.data[lo].latitude; 
			rec.top = census.data[lo].latitude;
			rec.left = census.data[lo].longitude;
			rec.right = census.data[lo].longitude;
			
			// set population
			rec.population += census.data[lo].population;
			for (int i = lo + 1; i < hi; i++) {
				rec.bottom = Math.min(rec.bottom, census.data[i].latitude);
				rec.top = Math.max(rec.top, census.data[i].latitude);
				rec.left = Math.min(rec.left, census.data[i].longitude);
				rec.right = Math.max(rec.right, census.data[i].longitude);
				rec.population += census.data[i].population;
			}
		}
	}
	
	public static boolean contains(CensusGroup census, Rectangle rec, Rectangle queryRec, int x, int y) {
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
