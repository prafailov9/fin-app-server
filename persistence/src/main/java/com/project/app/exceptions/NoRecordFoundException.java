package com.project.app.exceptions;

/**
 *
 * @author p.rafailov
 */
public class NoRecordFoundException extends RuntimeException {

    private final static String MESSAGE = "No records found in the database!";

    public NoRecordFoundException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
