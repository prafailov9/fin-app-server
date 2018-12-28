package com.project.app.businesslogic.exceptions;

public class InvalidEntityIdException extends EntityValidationException {

    private final static String MESSAGE = "Invalid entity id! Either null or wrong value.";

    public InvalidEntityIdException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
