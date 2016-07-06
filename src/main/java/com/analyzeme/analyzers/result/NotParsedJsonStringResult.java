package com.analyzeme.analyzers.result;

/**
 * Use this type of result when you have got a result of an unknown type (from user R script, for example)
 */

public class NotParsedJsonStringResult implements IResult {

    private final String result;

    public NotParsedJsonStringResult(final String result) {
        this.result = result;
    }

    public String getValue() {
        return result;
    }

    public String toJson() {
        if (result == null) {
            return null;
        }
        return result;
    }
}
