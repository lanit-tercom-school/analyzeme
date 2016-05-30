package com.analyzeme.analyze;

/**
 * Class of AnalyzeFunction  "GlobalMaximum" .
 * Created by Sergey on 23.11.2015.
 */
public class GlobalMaximum implements AnalyzeFunction {

	/**
	 * @param xArray array of x-coordinate.
	 * @param yArray array of y-coordinate.
	 * @return index of maximum value.
	 */
	public int calc(double[] xArray, double[] yArray) {
		int max_index = 0;//index of maximum value

		for (int i = 0; i < yArray.length; i++) //cicle where we looking for maximum.
			if (yArray[max_index] < yArray[i]) max_index = i;

		return max_index;
	}

	/**
	 * @param pointArray array of Point.
	 * @return index of maximum value.
	 */
	public int calc(Point[] pointArray) {
		int max_index = 0;//index of maximum value

		for (int i = 0; i < pointArray.length; i++) //cicle where we looking for maximum.
			if (pointArray[max_index].getY() < pointArray[i].getY()) max_index = i;

		return max_index;
	}
}
