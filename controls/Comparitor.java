package controls;

import java.io.FileInputStream;

public class Comparitor {

	public int compare(String previous, String current) {
		int distance = 0;
		int charprev;
		int charcurr;
		int currmean = 0;
		int currcount = 0;
		int prevmean = 0;
		int prevcount = 0;
		Utility utility = new Utility();
		double[][] plane = new double[8][8];
		double[][] row = null;
		double[][] col = new double[8][8];
		double[][] colour = new double[8][8];
		int i = 0;
		int j = 0;
		int columns = 0;

		try {
			FileInputStream prev = new FileInputStream(previous);
			FileInputStream curr = new FileInputStream(current);
			StringBuffer previousImage = new StringBuffer();
			StringBuffer currentImage = new StringBuffer();
			while ((charprev = prev.read()) != -1) {
				prevmean += charprev;
				prevcount++;
				previousImage.append(charprev);
				// System.out.print(charprev + " ");
				if (i >= 7 && j >= 7) {
					continue;
				}
				if (i >= 7) {
					continue;
				}
				if (j >= 7) {
					j = 0;
					i++;
				}
				col[i][j] = charprev;
				if (i >= 7 && j >= 7) {
					continue;
				}
				if (i >= 7) {
					continue;
				}
				if (j >= 7) {
					j = 0;
					i++;
				}
				plane[i][j] = charprev - 255;
				if (i >= 7 && j >= 7) {
					continue;
				}
				if (i >= 7) {
					continue;
				}
				if (j >= 7) {
					j = 0;
					i++;
				}
				colour[i][j] = charprev - 128;
			}
			utility.extractCol(colour, i);

			prevmean = prevmean / prevcount;
			System.out.println();
			while ((charcurr = curr.read()) != -1) {
				currmean += charcurr;
				currcount++;
				currentImage.append(charcurr);
			}
			currmean = currmean / currcount;
			System.out.println("Mean of Pervious Image: " + prevmean
					+ " Previous count: " + prevcount);
			System.out.println("Mean of Current Image: " + currmean
					+ " Current count: " + currcount);
			int min = currcount - 300;
			int max = currcount + 300;
			System.out.println("min:" + min);
			System.out.println("max:" + max);
			if (prevcount >= min && prevcount <= max) {
				distance = 0;
			} else {
				distance = 1;
			}
			System.out.println("The count"
					+ previousImage.toString().compareToIgnoreCase(
							currentImage.toString()));
			System.out
					.println("The distance Between the images is " + distance);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return distance;
	}
}
