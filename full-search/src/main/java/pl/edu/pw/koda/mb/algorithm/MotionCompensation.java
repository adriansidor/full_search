package pl.edu.pw.koda.mb.algorithm;

import org.opencv.core.Mat;

public class MotionCompensation {

	/**
	 * Kompensacja ruchu na podstawie wektorów ruchu.
	 * Wstawia do kompensowanego obrazu makrobloki z obrazu referencyjnego 
	 * na podstawie wektorów ruchu.
	 * @param imgR obraz referencyjny
	 * @param motionVectors wektory ruchu
	 * @param mbSize rozmiar makrobloku
	 * @return obraz po kompensacji ruchu
	 */
	public Mat compensate(Mat imgR, MotionVector[][] motionVectors, int mbSize) {
		int width = imgR.width();
		int height = imgR.height();
		
		Mat new_image = new Mat(height, width, imgR.type());
		
		double c = (double) 1/(mbSize*mbSize);
		// liczba makroblokow w wierszu
		int N1 = width/mbSize;
		
		// punkty w poziomie reprezentujace makrobloki
		int[] n1 = new int[N1];
		for(int i = 0; i<N1; i++) {
			n1[i] = i*mbSize;
		}
		
		// liczba makroblokow w kolumnie
		int N2 = height/mbSize;
		
		// punkty w pionie reprezentujace makrobloki
		int[] n2 = new int[N2];
		for(int i = 0; i<N2; i++) {
			n2[i] = i*mbSize;
		}
		
		
		for(int i = 0; i<N1; i++) {
			for(int j = 0; j<N2; j++) {
				MotionVector vector = motionVectors[i][j];
				int actBlkVer = n1[i];
				int actBlkHor = n2[j];
				int refBlkVer = n1[i] + vector.getX();
				int refBlkHor = n2[j] + vector.getY();
				for(int a = 0; a<mbSize; a++) {
					for(int b = 0; b<mbSize; b++) {
						new_image.put(actBlkHor + b, actBlkVer + a, imgR.get(refBlkHor + b, refBlkVer + a));
					}
				}
			}
		}
		
		return new_image;
	}
}
