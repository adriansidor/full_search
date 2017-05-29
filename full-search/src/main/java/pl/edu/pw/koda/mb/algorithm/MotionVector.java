package pl.edu.pw.koda.mb.algorithm;

public class MotionVector {

	private int x;
	private int y;
	private double cost;
	
	public MotionVector(int x, int y, double cost) {
		this.x = x;
		this.y = y;
		this.cost = cost;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
