package com.project.app.exceptions;

/**
 *
 * @author p.rafailov
 */
public class EntityConverterNotFoundException extends RuntimeException {

    private final static String MESSAGE = "Entity converter not found";

    public EntityConverterNotFoundException(String key) {
        super(String.format("%s for provided key %s", MESSAGE, key));
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
