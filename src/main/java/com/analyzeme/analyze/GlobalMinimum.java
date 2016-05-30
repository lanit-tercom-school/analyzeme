package com.analyzeme.analyze;

/**
 * /**
 * Class of AnalyzeFunction  "GlobalMinimum"
 * Created by Sergey on 23.11.2015.
 */
public class GlobalMinimum implements AnalyzeFunction {
	/**
	 * @param xArray array of x-coordinate.
	 * @param yArray array of y-coordinate.
	 * @return index of Minimum value.
	 */
	public int calc(final double[] xArray, final double[] yArray) {
		int min_index = 0;//index of minimum value

		for (int i = 0; i < yArray.length; i++) {
			//cicle where we looking for minimum.
			if (yArray[min_index] > yArray[i]) {
				min_index = i;
			}
		}

		return min_index;
	}

	/**
	 * @param pointArray array of Point.
	 * @return index of minimum value.
	 */
	public int calc(final Point[] pointArray) {
		int min_index = 0;//index of minimum value

		for (int i = 0; i < pointArray.length; i++) {
			//cicle where we looking for minimum.
			if (pointArray[min_index].getY() > pointArray[i].getY()) {
				min_index = i;
			}
		}

		return min_index;
	}
}
