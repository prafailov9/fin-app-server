package com.project.app.exceptions;

/**
 *
 * @author p.rafailov
 */
public class NullIdException extends RuntimeException {

    private static final String MESSAGE = "Identifier is null!";

    public NullIdException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
