package com.project.app.businesslogic.exceptions;

public abstract class EntityValidationException extends RuntimeException {
    
    public EntityValidationException() {
        super();
    }
    
    @Override
    public abstract String getMessage();
    
}
