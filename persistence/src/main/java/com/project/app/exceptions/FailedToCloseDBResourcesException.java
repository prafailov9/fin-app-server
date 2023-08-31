package com.project.app.exceptions;

public class FailedToCloseDBResourcesException extends RuntimeException {

    public FailedToCloseDBResourcesException(Throwable exception) {
        super(exception.getMessage(), exception.getCause());
    }

}
