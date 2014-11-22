/**
 * This is an abstract class to utilize polymorphism for Version 1, 2, 3, 4.
 * All the fields are being set to be protected and print method to be shared through
 * all the versions.
 * @author senghuot
 */
public abstract class Version0 {
	
	// each field stores all the info for each query operation 
	protected CensusData census;
	protected int x;
	protected int y;
	protected Rectangle rec;
	
	// just 
	public Version0(CensusData c, int x, int y) {
		census = c;
		this.x = x;
		this.y = y;
		rec = new Rectangle(0, 0, 0, 0, 0);
	}
	
	// print out the bounded total population and its ratio to the total population.
	public void print(int currPop) {
		//double ratio = (100.0 * currPop) / rec.population;
		//System.out.println("population of rectangle: " + currPop);
		//System.out.printf("percent of total: %.2f \n", ratio);		
	}

	// force each version to have query method.
	public abstract void query(int west, int south, int east, int north);
}
