package com.analyzeme.analyze;

/**
 * Interface what we use to create function for analyze.
 * Created by Sergey on 23.11.2015.
 */
public interface AnalyzeFunction {
	/**
	 * Calculate sth  using arrays of x and y coordinates
	 *
	 * @param xArray array of x-coordinate.
	 * @param yArray array of y-coordinate.
	 * @return index of calculated value.
	 */
	int calc(final double[] xArray, final double[] yArray);

	/**
	 * Calculate sth  using arrays of Point
	 *
	 * @param pointArray array of Point.
	 * @return index of calculated value.
	 */
	int calc(final Point[] pointArray);
}
