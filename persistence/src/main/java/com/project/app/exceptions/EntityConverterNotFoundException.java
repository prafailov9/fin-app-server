package com.project.app.exceptions;

/**
 *
 * @author p.rafailov
 */
public class EntityConverterNotFoundException extends RuntimeException {

    private final static String MESSAGE = "Entity converter not found!";

    public EntityConverterNotFoundException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
