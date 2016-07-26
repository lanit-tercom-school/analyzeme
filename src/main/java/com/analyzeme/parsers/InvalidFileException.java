package com.analyzeme.parsers;

public class InvalidFileException extends Exception {
    public InvalidFileException(String message) {
        super(message);
    }

    public InvalidFileException() {
        super();
    }

}
