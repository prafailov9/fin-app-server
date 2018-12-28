package com.project.app.exceptions;

/**
 *
 * @author p.rafailov
 */
public class CannotPersistEntityException extends RuntimeException {

    private final static String MESSAGE = "Entity cannot be persisted!";

    public CannotPersistEntityException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
