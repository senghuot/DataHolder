
public abstract class Version {
	protected CensusData census;
	protected int x;
	protected int y;
	protected int population;
	protected float minX;
	protected float maxX;
	protected float minY;
	protected float maxY;
	
	public Version(CensusData c, int x, int y) {
		census = c;
		this.x = x;
		this.y = y;
	}
	
	public abstract void query(int west, int south, int east, int north);
}
