package pl.edu.pw.koda.mb.algorithm;

import org.opencv.core.Mat;

public class FullSearch {

	/**
	 * Algorytm Full Search metody pasowania bloków.
	 * Block-matching algorithm.
	 * @param imgA obraz aktualny, dla ktorego chcemy znalezc
	 *             wektory ruchu
	 * @param imgR obraz odniesienia
	 * @param mbSize rozmiar makrobloku
	 * @param p rozmiar obszaru poszukiwan
	 */
	public MotionVector[][] execute(Mat imgA, Mat imgR, int mbSize, int p, CostFunction costFunction) {
		int width = imgA.width();
		int height = imgA.height();

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
		
		//punkt (n1,n2) reprezentuje makroblok
		//np. (n1[0],n2[0]) reprezentuje pierwszy makroblok
		
		// wektory ruchu
		MotionVector[][] result = new MotionVector[N1][N2];
		
		for(int i = 0; i<N1; i++) {
			for(int j = 0; j<N2; j++) {
				// szukamy wektora ruchu dla makrobloku (i,j)
				MotionVector[] costs = new MotionVector[(2*p+1)*(2*p+1)];
				int vectorIndex = -1;
				for(int n = -p; n<=p; n++) {
					for(int m = -p; m<=p; m++) {
						vectorIndex++;
						int actBlkVer = n1[i];
						int actBlkHor = n2[j];
						int refBlkVer = n1[i] + n;
						int refBlkHor = n2[j] + m;
						if(isOutOfBounds(refBlkVer, refBlkHor, width, height, mbSize)){
							costs[vectorIndex] = new MotionVector(n, m, Double.MAX_VALUE);
							continue;
						}
						double cost = 0;
						for(int a = 0; a<mbSize; a++) {
							for(int b = 0; b<mbSize; b++) {
								cost += costFunction.compute(imgA.get(actBlkHor+b, actBlkVer+a)[0], imgR.get(refBlkHor+b, refBlkVer+a)[0]);
							}
						}
						costs[vectorIndex] = new MotionVector(n, m, cost*c);
						
					}
				}
				result[i][j] = getMinVector(costs);
				System.out.println(String.format("Completed (%s,%s)", i, j)); 
			}
		}
		
		return result;
	}
	
	/**
	 * Sprawdza czy blok odniesienia miesci sie w granicach obrazu
	 * @param x punkt poziomy bloku odniesienia
	 * @param y punkt pionowy bloku odniesienia
	 * @param width szerokosc obrazu
	 * @param hight wysokosc obrazu
	 * @param mbSize rozmiar bloku
	 * @return true jesli blok odniesienia nie miesci sie w granicach obrazu
	 *         false w przeciwnym przypadku
	 */
	private boolean isOutOfBounds(int x, int y, int width, int height, int mbSize) {
		if(x < 0 || (x+mbSize) > width || y < 0 || y+mbSize > height) {
			return true;
		}
		
		return false;
	}
	
	private MotionVector getMinVector(MotionVector[] vectors) {
		MotionVector minVector = null;
		double minCost = Double.MAX_VALUE;
		for(MotionVector vector : vectors) {
			if(vector.getCost() < minCost) {
				minCost = vector.getCost();
				minVector = vector;
			}
		}
		
		return minVector;
	}
	
}
