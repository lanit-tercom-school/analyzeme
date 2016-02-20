package com.analyzeme.analyze;

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
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * set value of x coordinate
	 *
	 * @param x value of x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * set value of x coordinate
	 *
	 * @param y value of x coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * get value of x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * get value of x coordinate
	 */
	public double getY() {
		return y;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (!(other instanceof Point)) {
			return false;
		}
		Point otherPoint = (Point) other;
		return Double.compare(x, otherPoint.getX()) == 0
				&& Double.compare(y, otherPoint.getY()) == 0;
	}
}
