package com.project.app.businesslogic.exceptions.calcvalidation;

/**
 *
 * @author prafailov
 */
public class PositionVolumeStateException extends RuntimeException {

    private final static String MESSAGE = "Position volume must be larger than 0!";

    public PositionVolumeStateException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
