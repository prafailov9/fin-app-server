package com.project.app.service.exceptions;

public class InvalidEntityDateException extends EntityValidationException {
    
    private final static String MESSAGE = "Invalid date value!";
    
    public InvalidEntityDateException() {
        super();
    }
    
    @Override
    public String getMessage() {
        return MESSAGE;
    }
    
}
