package com.project.app.exceptions;

/**
 *
 * @author p.rafailov
 */
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(final String msg) {
        super(msg);
    }

}
