package com.analyzeme.analyzers;

import java.util.List;

/**
 * Created by Ольга on 10.04.2016.
 */
public interface IAnalyzer<T> {

	/**
	 * @param data - every item in list represents one source of points (file, for example)
	 * @return Double, Point or List<Point>
	 */
	T analyze(List<Point[]> data) throws Exception;
}
