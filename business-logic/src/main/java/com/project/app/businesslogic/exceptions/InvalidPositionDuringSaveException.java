package com.project.app.businesslogic.exceptions;

/**
 *
 * @author prafailov
 */
public class InvalidPositionDuringSaveException extends RuntimeException {

    private final static String MESSAGE = "The position ID, volume  or principal should be null upon saving.";

    public InvalidPositionDuringSaveException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
