package com.project.app.businesslogic.exceptions;

/**
 *
 * @author prafailov
 */
public class DateAdderDoesntExistException extends RuntimeException {

    private final static String MESSAGE = "Could not find specific date adder!";

    public DateAdderDoesntExistException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
