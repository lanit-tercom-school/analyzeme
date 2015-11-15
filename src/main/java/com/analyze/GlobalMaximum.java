package com.analyze;
/**
 * Class what calculate global maximum
 */
public class GlobalMaximum {
    public int GlobalMax(Point [] array ,int n){
        int max_index=0;
        for(int i=0;i<n;i++)  if(array[i].y>array[max_index].y) max_index=i;

        return max_index;
    }
}
