package com.project.app.businesslogic.exceptions.calcvalidation;

/**
 *
 * @author prafailov
 */
public class InvalidTransactionStateException extends RuntimeException {

    private final static String MESSAGE = "Invalid transaction state before calculation! "
            + "Either of these properties have invalid values: amount, sign;";

    public InvalidTransactionStateException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
