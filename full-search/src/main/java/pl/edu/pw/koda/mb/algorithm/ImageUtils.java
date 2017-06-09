package pl.edu.pw.koda.mb.algorithm;

import org.opencv.core.Mat;

public class ImageUtils {

	public static double computeEntropy(Mat histogram) {
		double entropy = 0.0;
		double sum = sum(histogram);
		for (int i=0; i<histogram.rows(); i++)
		{
		    double[] binEntry = histogram.get(i,0);
		        entropy -= (binEntry[0]/sum) * log(binEntry[0]/sum, 2);
		}
		
		return entropy;
	}
	
    public static double sum(Mat histogram) {
    	double sum = 0.0;
    	for (int i=0; i<histogram.rows(); i++)
		{
		    sum += histogram.get(i,0)[0];
		}
    	
    	return sum;
    }
	
	static double log(double x, int base)
	{
	    return (Math.log(x) / Math.log(base));
	}
}