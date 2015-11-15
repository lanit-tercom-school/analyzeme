package com.analyze;

/**
 * Class what calculate global minimum
 */
public class GlobalMinimum {
    public int GlobalMin(Point [] array ,int n){
        int min_index=0;
        for(int i=0;i<n;i++)  if(array[i].y<array[min_index].y) min_index=i;

        return min_index;
    }
}
