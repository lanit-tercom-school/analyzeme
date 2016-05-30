package com.analyzeme.analyze;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class what describe point
 * Created by Sergey on 07.12.2015.
 */
@JsonAutoDetect
public class Point {
	@JsonProperty("x")
	private double x;
	@JsonProperty("y")
	private double y;

	/**
	 * default constructor
	 */
	public Point() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructor  for two double variable
	 *
	 * @param a value of a coordinate
	 * @param b value of b coordinate
	 */
	public Point(final double a, final double b) {
		this.x = a;
		this.y = b;

	}

	/**
	 * set value of x coordinate
	 *
	 * @param x value of x coordinate
	 */
	public void setX(final double x) {
		this.x = x;
	}

	/**
	 * set value of x coordinate
	 *
	 * @param y value of x coordinate
	 */
	public void setY(final double y) {
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
