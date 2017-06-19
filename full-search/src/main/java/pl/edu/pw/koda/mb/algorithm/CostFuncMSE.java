package pl.edu.pw.koda.mb.algorithm;

public class CostFuncMSE implements CostFunction {

	public double compute(double p1, double p2) {
		return Math.pow((p1 - p2), 2.0);
	}

}
