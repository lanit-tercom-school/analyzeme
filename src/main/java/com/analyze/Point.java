package com.analyze;

/**
 * Class what describe point
 * Created by Sergey on 07.12.2015.
 */
public class Point {
    private double x;
    private double y;

    /**
     * default constructor
     */
    Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructor  for two double variable
     *
     * @param x value of x coordinate
     * @param y value of y coordinate
     */
    Point(double x, double y) {
        this.x = x;
        this.y = y;

    }

    /**
     * set value of x coordinate
     *
     * @param x value of x coordinate
     */
    public void SetX(double x) {
        this.x = x;
    }

    /**
     * set value of x coordinate
     *
     * @param y value of x coordinate
     */
    public void SetY(double y) {
        this.y = y;
    }

    /**
     * get value of x coordinate
     */
    public double GetX() {
        return x;
    }

    /**
     * get value of x coordinate
     */
    public double GetY() {
        return y;
    }
}