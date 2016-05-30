package com.analyzeme.data.resolvers;

import com.analyzeme.data.DataSet;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public interface IDataSetResolver {

    DataSet getDataSet(String key) throws Exception;
}
