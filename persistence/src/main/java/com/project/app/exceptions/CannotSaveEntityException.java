package com.project.app.exceptions;

/**
 *
 * @author p.rafailov
 */
public class CannotSaveEntityException extends RuntimeException {

    private final static String MESSAGE = "Entity cannot be saved!";

    public CannotSaveEntityException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
