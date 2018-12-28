package com.project.app.exceptions;

/**
 *
 * @author Plamen
 */
public class NoSuchEntityException extends RuntimeException {

    private final static String MESSAGE = "Entity does not exist!";

    public NoSuchEntityException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
