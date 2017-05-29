package pl.edu.pw.koda.mb.algorithm;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class FullSearchTest {
	
	static {System.loadLibrary( Core.NATIVE_LIBRARY_NAME );}

	@Test
	public void test() {
		Mat imgA = Imgcodecs.imread("src\\main\\resources\\db2.jpg");
		Mat imgR = Imgcodecs.imread("src\\main\\resources\\db1.jpg");
		int mbSize = 16;
		int p = 32;
		FullSearch algorithm = new FullSearch();
		MotionCompensation mc = new MotionCompensation();
		
		MotionVector[][] motionVectors = algorithm.execute(imgA, imgR, mbSize, p, new CostFuncMAD());
		
		Mat newImage = mc.compensate(imgR, motionVectors, mbSize);
		Imgcodecs.imwrite("src\\main\\resources\\db3.jpg", newImage);
	}
}
