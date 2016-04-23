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
		return Double.compare(x, otherPoint.GetX()) == 0
				&& Double.compare(y, otherPoint.GetY()) == 0;
	}
}
