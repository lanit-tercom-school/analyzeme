package com.analyzeme.analyzers;

/**
 * Created by Ольга on 10.04.2016.
 */
public interface IAnalyzer<T> {
    /**
     * @param data - should be List<Point> or
     *             List<T>(for one-dimensional analysis)
     *             Set<Lists<Points>> (for future, to analyze more than one set of data)
     * @return String with JSON inside(JSON should represent Point or List<Point>)
     */
    String analyze(T data);
}
