package com.analyzeme.r.facade;

import com.analyzeme.r.facade.get.*;

public class GetFromRFactory {

    public static IFromR getLinkToR(TypeOfReturnValue typeOfReturnValue) {
        switch (typeOfReturnValue) {
            case DOUBLE:
                return new ScalarFromR<Double>();
            case SCALAR:
                return new ScalarFromR();
            case JSON_STRING:
                return new DefaultFromR();
            case VECTOR:
                return new VectorFromR();
            case VECTORS:
                return new FileFromR();
            default:
                throw new IllegalArgumentException(
                        "This type of return value is not supported");
        }
    }
}
