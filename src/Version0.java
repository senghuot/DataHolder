
public abstract class Version0 {
	
	protected CensusData census;
	protected int x;
	protected int y;
	protected Rectangle rec;
	
	public Version0(CensusData c, int x, int y) {
		census = c;
		this.x = x;
		this.y = y;
		rec = new Rectangle(0, 0, 0, 0, 0);
	}
	
	public void print(int currPop) {
//		double ratio = (100.0 * currPop) / rec.population;
//		System.out.println("population of rectangle: " + currPop);
//		System.out.printf("percent of total: %.2f \n", ratio);		
	}
	
	public abstract void query(int west, int south, int east, int north);
}
