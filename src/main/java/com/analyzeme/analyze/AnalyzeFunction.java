package com.analyzeme.analyze;

/**
 * Interface what we use to create function for analyze.
 * Created by Sergey on 23.11.2015.
 */
public interface AnalyzeFunction {
	/**
	 * Calculate sth  using arrays of x and y coordinates
	 *
	 * @param X_array array of x-coordinate.
	 * @param Y_array array of y-coordinate.
	 * @return index of calculated value.
	 */
	int Calc(double[] X_array, double[] Y_array);

	/**
	 * Calculate sth  using arrays of Point
	 *
	 * @param Point_array array of Point.
	 * @return index of calculated value.
	 */
	int Calc(Point[] Point_array);
}
