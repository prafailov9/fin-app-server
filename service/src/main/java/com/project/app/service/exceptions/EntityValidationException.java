package com.project.app.service.exceptions;

public abstract class EntityValidationException extends RuntimeException {
    
    public EntityValidationException() {
        super();
    }
    
    @Override
    public abstract String getMessage();
    
}
