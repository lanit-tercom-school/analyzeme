package com.analyzeme.data.resolvers;

public class DataSetResolversFactory {

    public static IDataSetResolver getDataSetResolver(
            final TypeOfSource type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException(
                    "Impossible type of data source");
        }
        switch (type) {
            case REPO: {
                return new FileInRepositoryResolver();
            }
            default: {
                throw new IllegalArgumentException(
                        "DataSetResolversFactory: internal error with enum");
            }
        }
    }
}
