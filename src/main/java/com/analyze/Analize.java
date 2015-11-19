package com.analyze;

/**
 * Class of analizing function.
 * Created by Sergey on 18.11.2015.
 */
public class Analize {
    /**
     *
     * @param X_array array of x-coordinate
     * @param Y_array array of y-coordinate
     * @param n number of point
     * @return index of maximum value
     */
    public int GlobalMax(double [] X_array,double [] Y_array, int n){
        int max_index=0;//index of maximum value

        for(int i=0;i<n;i++) //cicle where we looking for maximum
            if(Y_array[max_index]<Y_array[i]) max_index=i;

        return max_index;
    }

    /**
     *
     * @param X_array array of x-coordinate
     * @param Y_array array of y-coordinate
     * @param n number of point
     * @return index of Minimum value
     */
    public int GlobalMin(double [] X_array,double [] Y_array, int n){
        int min_index=0;//index of minimum value

        for(int i=0;i<n;i++) //cicle where we looking for maximum
            if(Y_array[min_index]>Y_array[i]) min_index=i;

        return min_index;
    }
}
