package com.analyzeme.data.resolvers;

import com.analyzeme.data.dataset.DataSet;

public interface IDataSetResolver {

    DataSet getDataSet(String key) throws Exception;
}
