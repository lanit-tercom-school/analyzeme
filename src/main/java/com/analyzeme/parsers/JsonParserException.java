package com.analyzeme.parsers;

public class JsonParserException extends InvalidFileException {
    private final ExceptionType exType;

    public ExceptionType getExType() {
        return exType;
    }

    public enum ExceptionType {
        DIFFERENT_LENGTH,
        UNKNOWN_EXCEPTION,
        PARSE_FILE
    }

    public JsonParserException(String text) {
        super(text);
        exType = ExceptionType.UNKNOWN_EXCEPTION;
    }

    public JsonParserException(ExceptionType exType) {
        this.exType = exType;
    }

    public String getMessage() {
        switch (exType) {
            case DIFFERENT_LENGTH:
                return "Different length of arrays";
            case PARSE_FILE:
                return "Parse file error";
            default:
                return super.toString();
        }
    }
}
