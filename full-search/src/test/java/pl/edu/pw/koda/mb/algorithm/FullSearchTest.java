package pl.edu.pw.koda.mb.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FullSearchTest {
	
	static {System.loadLibrary( Core.NATIVE_LIBRARY_NAME );}

	@Test
	public void test() {
		Mat img1 = Imgcodecs.imread("src\\main\\resources\\db2.jpg");
		Mat img2 = Imgcodecs.imread("src\\main\\resources\\db1.jpg");
		Mat imgA = new Mat();
		Mat imgR = new Mat();
		Imgproc.cvtColor(img1, imgA, Imgproc.COLOR_RGB2YUV);
		Imgproc.cvtColor(img2, imgR, Imgproc.COLOR_RGB2YUV);
		int mbSize = 16;
		int p = 32;
		FullSearch algorithm = new FullSearch();
		MotionCompensation mc = new MotionCompensation();
		
		MotionVector[][] motionVectors = algorithm.execute(imgA, imgR, mbSize, p, new CostFuncMAD());
		
		Mat newImage = mc.compensate(img2, motionVectors, mbSize);
		Imgcodecs.imwrite("src\\main\\resources\\db3.jpg", newImage);
	}
	
	@Test
	public void calculateHistogram() throws IOException {
		// nasz histogram, na razie pusty
		Mat histogram = new Mat();
		// wczytujemy obraz ktorego histogram chcemy uzyskac
		Mat img1 = Imgcodecs.imread("src\\main\\resources\\db2.jpg");
		Mat imgA = new Mat();
		Imgproc.cvtColor(img1, imgA, Imgproc.COLOR_RGB2YUV);
		List<Mat> images = new ArrayList<Mat>(1);
		images.add(imgA);
		// obliczamy histogram
		Imgproc.calcHist(images, new MatOfInt(0), new Mat(), histogram, new MatOfInt(256), new MatOfFloat(0, 256));
		// zapisujemy histogram do pliku, mozna go potem wczytać do excela i zrobic wykres
		File file = new File("src\\main\\resources\\hist.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i = 0; i<256; i++) {
			double[] p = histogram.get(i, 0);
			bw.write(String.valueOf(p[0]));
			bw.newLine();
		}
		bw.close();
		fw.close();
	}
	
	@Test
	public void calculateEntropy() {
		// nasz histogram, na razie pusty
		Mat histogram = new Mat();
		// wczytujemy obraz ktorego histogram chcemy uzyskac
		Mat img1 = Imgcodecs.imread("src\\main\\resources\\db2.jpg");
		Mat imgA = new Mat();
		Imgproc.cvtColor(img1, imgA, Imgproc.COLOR_RGB2YUV);
		List<Mat> images = new ArrayList<Mat>(1);
		images.add(imgA);
		// obliczamy histogram
		Imgproc.calcHist(images, new MatOfInt(0), new Mat(), histogram, new MatOfInt(256), new MatOfFloat(0, 256));
		// obliczamy entropie
		double entropy = ImageUtils.computeEntropy(histogram);
		System.out.println(entropy);
	}
}
