package com.project.app.service.exceptions;

/**
 *
 * @author prafailov
 */
public class InvalidFrequencyValueException extends RuntimeException {

    private final static String MESSAGE = "Frequency value has invalid state!";

    public InvalidFrequencyValueException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
