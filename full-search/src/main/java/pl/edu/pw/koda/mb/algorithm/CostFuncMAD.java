package pl.edu.pw.koda.mb.algorithm;

public class CostFuncMAD implements CostFunction {

	public double compute(double p1, double p2) {
		return Math.abs(p1 - p2);
	}

}
