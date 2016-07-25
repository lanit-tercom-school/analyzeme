package com.analyzeme.data.resolvers;

import com.analyzeme.data.DataSet;

public interface IDataSetResolver {

    DataSet getDataSet(String key) throws Exception;
}
