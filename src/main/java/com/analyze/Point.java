package com.analyze;

/**
 * Class what describe points.
 */
public class Point {

    public double x; // X-axis
    public double y; // Y-axis
    /**
     * Default constructor. Coordinates x and y will be 0.
     */
    public Point(){
        this.x=0;
        this.y=0;
    }

    /**
     * Constructor by two coordinates.
     */
    public  Point(double X,double Y){
        this.x=X;
        this.y=Y;
    }


}
