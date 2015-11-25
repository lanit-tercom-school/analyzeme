package com.analyze;

/**
 * /**
 * Class of AnalyzeFunction  "GlobalMaximum" .
 * Created by Sergey on 23.11.2015.
 */
public class GlobalMaximum implements AnalyzeFunction {

    /**
     *
     * @param X_array array of x-coordinate.
     * @param Y_array array of y-coordinate.
     * @param n number of point.
     * @return index of maximum value.
     */
    @Override
    public int Calc(double [] X_array,double [] Y_array, int n){
        int max_index=0;//index of maximum value

        for(int i=0;i<n;i++) //cicle where we looking for maximum.
            if(Y_array[max_index]<Y_array[i]) max_index=i;

        return max_index;
    }



}
