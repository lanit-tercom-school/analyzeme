package com.analyzeme.parsers;

/**
 * Created by ilya on 7/6/16.
 */
public class InvalidFileException extends Exception {
    public InvalidFileException(String message) {
        super(message);
    }

    public InvalidFileException() {
        super();
    }

}
