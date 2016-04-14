package com.analyzeme.R.facade;

/**
 * Created by lagroffe on 13.04.2016 0:25
 */
public class GetFromRFactory {

	public static GetFromR getLinkToR(TypeOfReturnValue typeOfReturnValue) {
		switch (typeOfReturnValue) {
			case DOUBLE:
				return new NumberFromR();
			case STRING:
				return new DefaultFromR();
			case POINT:
				return new PointFromR();
			case POINTS:
				return new PointsFromR();
		}
	   throw new IllegalArgumentException("This type of return value is not supported");
	}
}
