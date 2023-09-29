package com.project.app.service.exceptions.calcvalidation;

/**
 *
 * @author prafailov
 */
public class InvalidPositionStateBeforeCalculationException extends RuntimeException {

    private final static String MESSAGE = "Invalid position state! Either of these properties do not have valid values: "
            + "id, startDateOfDeal, volume, principal.";

    public InvalidPositionStateBeforeCalculationException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
