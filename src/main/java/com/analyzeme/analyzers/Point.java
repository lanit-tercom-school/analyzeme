package com.analyzeme.analyzers;

/**
 * Created by Ольга on 12.04.2016.
 */
//TODO: add functions for this type of Point in r.Call
public class Point<T1, T2> {

	/**
	 * (on 12.04.2016)
	 * T1 - can be Double,Integer or Date
	 * T2 - can be Double,Integer
	 */
	private T1 x;
	private T2 y;

	public Point(T1 x, T2 y) {
		this.x = x;
		this.y = y;
	}

	public T1 getX() {
		return x;
	}

	public void setX(T1 x) {
		this.x = x;
	}

	public T2 getY() {
		return y;
	}

	public void setY(T2 y) {
		this.y = y;
	}
}
