package com.analyzeme.data.resolvers;

import com.analyzeme.data.DataSet;

import java.util.List;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public interface IDataSetResolver {

    DataSet createDataSet(String key) throws Exception;
}
