package com.analyzeme.analyze;

/**
 * Class of AnalyzeFunction  "GlobalMaximum" .
 * Created by Sergey on 23.11.2015.
 */
public class GlobalMaximum implements AnalyzeFunction {

	/**
	 * @param X_array array of x-coordinate.
	 * @param Y_array array of y-coordinate.
	 * @return index of maximum value.
	 */
	@Override
	public int Calc(double[] X_array, double[] Y_array) {
		int max_index = 0;//index of maximum value

		for (int i = 0; i < Y_array.length; i++) //cicle where we looking for maximum.
			if (Y_array[max_index] < Y_array[i]) max_index = i;

		return max_index;
	}

	/**
	 * @param Point_array array of Point.
	 * @return index of maximum value.
	 */
	@Override
	public int Calc(Point[] Point_array) {
		int max_index = 0;//index of maximum value

		for (int i = 0; i < Point_array.length; i++) //cicle where we looking for maximum.
			if (Point_array[max_index].GetY() < Point_array[i].GetY()) max_index = i;

		return max_index;
	}
}
