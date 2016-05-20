package com.analyzeme.data.resolvers;

/**
 * Created by lagroffe on 30.03.2016 13:48
 */
public class DataSetResolversFactory {

    public static IDataSetResolver getDataSetResolver(final TypeOfSource type) throws IllegalArgumentException {
        if (type == null)
            throw new IllegalArgumentException("Impossible type of data source");
        switch (type) {
            case REPO: {
                return new JsonPointRepositoryDataResolver();
            }
            default: {
                throw new IllegalArgumentException("DataSetResolversFactory: internal error with enum");
            }
        }
    }
}
