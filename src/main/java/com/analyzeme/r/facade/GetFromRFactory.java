package com.analyzeme.r.facade;

/**
 * Created by lagroffe on 13.04.2016 0:25
 */
public class GetFromRFactory {

    public static GetFromR getLinkToR(TypeOfReturnValue typeOfReturnValue) {
        switch (typeOfReturnValue) {
            case DOUBLE:
                return new ScalarFromR<Double>();
            case SCALAR:
                return new ScalarFromR();
            case JSON_STRING:
                return new DefaultFromR();
            case VECTOR:
                return new VectorFromR();
            case FILE:
                return new FileFromR();
            default:
                throw new IllegalArgumentException(
                        "This type of return value is not supported");
        }
    }
}
