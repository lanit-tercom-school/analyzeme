package com.analyzeme.analyzers;

/**
 * Created by Ольга on 12.04.2016.
 */

//TODO: deprecate?
public class Point<U, V> {

	/**
	 * (on 12.04.2016)
	 * U - can be Double,Integer or Date
	 * V - can be Double,Integer
	 */
	private U x;
	private V y;

	public Point(U x, V y) {
		this.x = x;
		this.y = y;
	}

	public U getX() {
		return x;
	}

	public void setX(U x) {
		this.x = x;
	}

	public V getY() {
		return y;
	}

	public void setY(V y) {
		this.y = y;
	}
}
