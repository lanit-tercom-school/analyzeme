package com.analyze;

/**
 * /**
 * Class of AnalyzeFunction  "GlobalMinimum"
 * Created by Sergey on 23.11.2015.
 */
public class GlobalMinimum implements AnalyzeFunction {
    /**
     * @param X_array array of x-coordinate.
     * @param Y_array array of y-coordinate.
     * @return index of Minimum value.
     */
    @Override
    public int Calc(double[] X_array, double[] Y_array) {
        int min_index = 0;//index of minimum value

        for (int i = 0; i < Y_array.length; i++) //cicle where we looking for minimum.
            if (Y_array[min_index] > Y_array[i]) min_index = i;

        return min_index;
    }

}
