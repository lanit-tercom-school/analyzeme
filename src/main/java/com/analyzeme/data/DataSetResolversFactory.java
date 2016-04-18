package com.analyzeme.data;

/**
 * Created by lagroffe on 30.03.2016 13:48
 */
public class DataSetResolversFactory {

	public static IDataSetResolver getDataSetResolver(final String type) throws IllegalArgumentException {
		if (type == null || type.equals(""))
			throw new IllegalArgumentException("Impossible type of data source");
		if (type.equalsIgnoreCase("repo")) {
			return new RepositoryDataResolver();
		} else {
			throw new IllegalArgumentException("This data source doesn't exist");
		}
	}
}
