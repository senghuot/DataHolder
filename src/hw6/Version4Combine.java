package hw6;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class Version4Combine extends RecursiveAction {
	private int lowX;
	private int highX;
	private int lowY;
	private int highY;
	protected int[][] left;
	protected int[][] right;
	
	public static final int SEQUENCTIAL_CUTOFF = 50000;
	
	public Version4Combine(int loX, int hiX, int loY, int hiY, int[][] le, int[][] ri) {
		lowX = loX;
		highX = hiX;
		lowY = loY;
		highY = hiY;
		left = le;
		right = ri;
	}
	
	protected void compute() {
		if (((highX - lowX) * (highY - lowY)) < SEQUENCTIAL_CUTOFF) {
			for (int i = lowX; i < highX; i++) {
				for (int j = lowY; j < highY; j++)
					left[i][j] += right[i][j];
			}
		} else {
			int halfX = (highX + lowX) / 2;
			int halfY = (highY + lowY) / 2;
			
			// top halves
			Version4Combine topLeft = new Version4Combine(lowX, halfX, halfY, highY, left, right);
			Version4Combine topRight = new Version4Combine(halfX , highX, halfY, highY, left, right);
			// bottom halves
			Version4Combine bottomLeft = new Version4Combine(lowX, halfX, lowY, halfY, left, right);
			Version4Combine bottomRight = new Version4Combine(halfX, highX, lowY, halfY, left, right);
			
			topLeft.fork();
			topRight.fork();
			bottomLeft.fork();
			bottomRight.compute();
			topLeft.join();
			topRight.join();
			bottomLeft.join();
		}
	}
}
