package com.analyzeme.data;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public interface IDataSetResolver {
	DataSet getDataSet(String key) throws Exception;
}
